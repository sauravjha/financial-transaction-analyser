package financial.transaction.analyser.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.options.validate
import financial.transaction.analyser.EvaluateTransaction
import financial.transaction.analyser.InputData
import financial.transaction.analyser.utility.toDateFormat
import financial.transaction.analyser.uploader.CsvFileReader
import java.io.File

private val DATE_REGEX = """
    (\d{2})/(\d{2})/(\d{4}) (\d{2}):(\d{2}):(\d{2})
""".trimIndent().toRegex()

class CommandLineInterface : CliktCommand() {

    private val accountId: String by option(help = "Enter the account id.").prompt("Given the above input CSV file," +
            " entering the following input arguments:\naccountId")
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
        val transactions = CsvFileReader(File("tranactionFile.csv")).readCSVFile()
        EvaluateTransaction(transactions).getRelativeBalanceAndNumberOfTransaction(inputData)
    }
}


class InvalidDateFormat(override val message: String) : RuntimeException(message)



