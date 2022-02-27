package financial.transaction.analyser.uploader

import com.natpryce.hamkrest.Matcher
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.has
import com.natpryce.hamkrest.throws
import financial.transaction.analyser.cli.InvalidDateFormat
import financial.transaction.analyser.utility.toDateFormat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.File
import java.math.BigDecimal

object CSVFileReaderSpek : Spek({
    describe("CSVFileReader") {
        context("when the valid sample csv is read") {
            val valid = File("src/test/resources/sample.csv")
            val output = CsvFileReader(valid).readCSVFile()

            val expectedOutput = listOf<Transaction>(
                Transaction(
                    transactionId = "TX10001",
                    fromAccountId = "ACC334455",
                    toAccountId = "ACC778899",
                    createdAt = "20/10/2018 12:47:55".toDateFormat()!!,
                    amount = BigDecimal("25.00"),
                    transactionType = TransactionType.PAYMENT,
                    relatedTransaction = null,
                ),
                Transaction(
                    transactionId = "TX10002",
                    fromAccountId = "ACC334455",
                    toAccountId = "ACC998877",
                    createdAt = "20/10/2018 17:33:43".toDateFormat()!!,
                    amount = BigDecimal("10.50"),
                    transactionType = TransactionType.PAYMENT,
                    relatedTransaction = null,
                ),
                Transaction(
                    transactionId = "TX10003",
                    fromAccountId = "ACC998877",
                    toAccountId = "ACC778899",
                    createdAt = "20/10/2018 18:00:00".toDateFormat()!!,
                    amount = BigDecimal("5.00"),
                    transactionType = TransactionType.PAYMENT,
                    relatedTransaction = null,
                ),
                Transaction(
                    transactionId = "TX10004",
                    fromAccountId = "ACC334455",
                    toAccountId = "ACC998877",
                    createdAt = "20/10/2018 19:45:00".toDateFormat()!!,
                    amount = BigDecimal("10.50"),
                    transactionType = TransactionType.REVERSAL,
                    relatedTransaction = "TX10002",
                ),
                Transaction(
                    transactionId = "TX10005",
                    fromAccountId = "ACC334455",
                    toAccountId = "ACC778899",
                    createdAt = "21/10/2018 09:30:00".toDateFormat()!!,
                    amount = BigDecimal("7.25"),
                    transactionType = TransactionType.PAYMENT,
                    relatedTransaction = null,
                ),
            )

            it("returns the expected transaction") {
                assertThat(output, equalTo(expectedOutput))
            }
        }

        context("when the valid sample csv with invalid transaction is set") {
            val valid = File("src/test/resources/sample-invalid-transaction.csv")

            it("throws InvalidDateFormat") {
                assertThat(
                    {
                        CsvFileReader(valid).readCSVFile()
                    },
                    throws<InvalidDateFormat>(withMessage("Invalid TransactionType"))
                )
            }
        }

        context("when the valid sample csv with invalid data set") {
            val valid = File("src/test/resources/sample-invalid-date-format.csv")
            it("throws InvalidDateFormat") {
                assertThat(
                    {
                        CsvFileReader(valid).readCSVFile()
                    },
                    throws<InvalidDateFormat>(withMessage("Invalid Date"))
                )
            }
        }

        context("when the valid sample csv is read but one of the the transactionType is PAYMENT but and relatedTransaction is set") {
            val valid = File("src/test/resources/sample-inconsistent-transactionType-PAYMENT-and-relatedTransaction-data.csv")

            it("throws InvalidDateFormat") {
                assertThat(
                    {
                        CsvFileReader(valid).readCSVFile()
                    },
                    throws<InvalidDateFormat>(withMessage("Invalid data transactionType PAYMENT should not have relatedTransaction data"))
                )
            }
        }

        context("when the valid sample csv is read but one of the the transactionType is PAYMENT but and relatedTransaction is set") {
            val valid = File("src/test/resources/sample-inconsistent-transactionType-REVERSAL-and-relatedTransaction-data.csv")

            it("throws InvalidDateFormat") {
                assertThat(
                    {
                        CsvFileReader(valid).readCSVFile()
                    },
                    throws<InvalidDateFormat>(withMessage("Invalid data transactionType REVERSAL should have relatedTransaction data"))
                )
            }
        }
    }
})

fun withMessage(message: String): Matcher<InvalidDateFormat> {
    return has(InvalidDateFormat::message, equalTo(message))
}
