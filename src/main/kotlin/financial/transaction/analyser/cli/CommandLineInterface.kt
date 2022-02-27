package financial.transaction.analyser.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.validate
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.file
import financial.transaction.analyser.AnalyseTransaction
import financial.transaction.analyser.InputData
import financial.transaction.analyser.uploader.CsvFileReader
import financial.transaction.analyser.utility.toDateFormat
import java.math.BigDecimal

private val DATE_REGEX =
    """
    (\d{2})/(\d{2})/(\d{4}) (\d{2}):(\d{2}):(\d{2})
    """.trimIndent().toRegex()

private val CSV_REGEX = "([a-zA-Z0-9/\\s_\\\\.\\-\\(\\):])+(.csv)\$".toRegex()

class CommandLineInterface : CliktCommand() {

    private val file by argument(
        help =
            "(MANDATORY) Enter *.csv file with complete location."
    )
        .file(mustExist = true).validate { require(CSV_REGEX.matches(it.toString())) { "File must be CSV file" } }

    private val accountId: String by option(help = "Enter the account id.").prompt(
        "Given the above input CSV file," +
            " entering the following input arguments:\naccountId"
    )
    private val from: String by option(help = "Enter the start date.").prompt("from").validate {
        require(it.matches(DATE_REGEX)) {
            "Date format is incorrect we are expecting it to be dd/mm/yyyy hh:mm:ss"
        }
    }
    private val to: String by option(help = "Enter the end date.").prompt("to").validate {
        require(
            it.matches(DATE_REGEX)
        ) {
            "Date format is incorrect we are expecting it to be dd/mm/yyyy hh:mm:ss"
        }
        require(
            from.toDateFormat()!! <= it.toDateFormat()!!
        ) {
            "To date must be greater then from date"
        }
    }

    override fun run() {
        val inputData = InputData(
            accountId = accountId,
            fromDateFormat = from.toDateFormat() ?: throw InvalidDateFormat("Invalid date format"),
            toDateFormat = to.toDateFormat() ?: throw InvalidDateFormat("Invalid date format"),
        )
        val transactions = CsvFileReader(file).readCSVFile()
        val (relativeBalance, numberOfTransaction) = AnalyseTransaction(transactions)
            .getRelativeBalanceAndNumberOfTransaction(
                inputData
            )
        echo(
            "Relative balance for the period is: " +
                "${appendDollarSymbol(relativeBalance)} Number of transactions included is: $numberOfTransaction"
        )
    }
}

private fun appendDollarSymbol(relativeBalance: BigDecimal): String {
    return if (relativeBalance < BigDecimal(0)) "-$${relativeBalance.abs()}" else "+$${relativeBalance.abs()}"
}

class InvalidDateFormat(override val message: String) : RuntimeException(message)
