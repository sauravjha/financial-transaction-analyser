package financial.transaction.analyser.uploader

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.util.CSVFieldNumDifferentException
import financial.transaction.analyser.cli.InvalidDateFormat
import financial.transaction.analyser.utility.toDateFormat
import java.io.File
import java.lang.IllegalArgumentException
import java.math.BigDecimal

class CsvFileReader(
    private val fileName: File,
) {
    fun readCSVFile(): List<Transaction> {
        return try {
            csvReader {
                skipMissMatchedRow = true
            }.readAll(fileName).drop(1).map {
                    Transaction(
                        transactionId=it[0].trim(),
                        fromAccountId=it[1].trim(),
                        toAccountId=it[2].trim(),
                        createdAt= it[3].trim().toDateFormat()!!,
                        amount= BigDecimal(it[4].trim()),
                        transactionType=TransactionType.valueOf(it[5].trim()),
                        relatedTransaction= it[6].trim().checkIfEmptyAndTransactionType(it[5].trim())
                    )
                }.toList()
        } catch (e: CSVFieldNumDifferentException) {
            throw InvalidDateFormat("Invalid data format.")
        } catch (e: IllegalArgumentException) {
            throw InvalidDateFormat("Invalid TransactionType")
        } catch (e: NullPointerException) {
            throw InvalidDateFormat("Invalid Date")
        }
    }

}

private fun String.checkIfEmptyAndTransactionType(transactionType: String): String? {
    return when {
        (this == "" && TransactionType.valueOf(transactionType) == TransactionType.PAYMENT) -> null
        (this == "" && TransactionType.valueOf(transactionType) == TransactionType.REVERSAL) -> throw InvalidDateFormat(
            "Invalid data transactionType REVERSAL should have relatedTransaction data")
        TransactionType.valueOf(transactionType) == TransactionType.PAYMENT -> throw InvalidDateFormat(
            "Invalid data transactionType PAYMENT should not have relatedTransaction data")
        else -> this
    }
}
