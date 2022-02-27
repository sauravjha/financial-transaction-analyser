package financial.transaction.analyser

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import financial.transaction.analyser.uploader.Transaction
import financial.transaction.analyser.uploader.TransactionType
import financial.transaction.analyser.utility.toDateFormat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.math.BigDecimal

object AnalyseTransactionSpek: Spek({
    describe("EvaluateTransaction") {
        context("when we call EvaluateTransaction is called with the input that does not have accountid") {

            val expectedOutput = listOf<Transaction>(
                Transaction(
                    transactionId= "TX10001",
                    fromAccountId= "ACC3344559",
                    toAccountId= "ACC778899",
                    createdAt= "20/10/2018 12:47:55".toDateFormat()!!,
                    amount= BigDecimal("25.00"),
                    transactionType= TransactionType.PAYMENT,
                    relatedTransaction= null,
                ),
                Transaction(
                    transactionId= "TX10002",
                    fromAccountId= "ACC334455",
                    toAccountId= "ACC998877",
                    createdAt= "20/10/2018 17:33:43".toDateFormat()!!,
                    amount= BigDecimal("10.50"),
                    transactionType= TransactionType.PAYMENT,
                    relatedTransaction= null,
                ),
                Transaction(
                    transactionId= "TX10003",
                    fromAccountId= "ACC998877",
                    toAccountId= "ACC778899",
                    createdAt= "20/10/2018 18:00:00".toDateFormat()!!,
                    amount= BigDecimal("5.00"),
                    transactionType= TransactionType.PAYMENT,
                    relatedTransaction= null,
                ),
                Transaction(
                    transactionId= "TX10004",
                    fromAccountId= "ACC334455",
                    toAccountId= "ACC998877",
                    createdAt= "20/10/2018 19:45:00".toDateFormat()!!,
                    amount= BigDecimal("10.50"),
                    transactionType= TransactionType.REVERSAL,
                    relatedTransaction= "TX10002",
                ),
                Transaction(
                    transactionId= "TX10005",
                    fromAccountId= "ACC334455",
                    toAccountId= "ACC778899",
                    createdAt= "21/10/2018 09:30:00".toDateFormat()!!,
                    amount= BigDecimal("7.25"),
                    transactionType= TransactionType.PAYMENT,
                    relatedTransaction= null,
                ),
            )
            val inputData = InputData(
                "ACC33445",
                "20/10/2018 12:00:00".toDateFormat()!!,
                "20/10/2018 19:00:00".toDateFormat()!!
            )

            val relativeBalance = EvaluateTransaction(expectedOutput).getRelativeBalanceAndNumberOfTransaction(inputData)
            it("returns correct relative balance") {
                assertThat(relativeBalance.relativeBalance, equalTo(BigDecimal("0")))
            }
            it("returns correct number of transaction") {
                assertThat(relativeBalance.numberOfTransaction, equalTo(0))
            }

        }

        context("when we call EvaluateTransaction is called with the input(accountId) that has -ive relative balance for the give time period") {

            val expectedOutput = listOf<Transaction>(
                Transaction(
                    transactionId= "TX10001",
                    fromAccountId= "ACC334455",
                    toAccountId= "ACC778899",
                    createdAt= "20/10/2018 12:47:55".toDateFormat()!!,
                    amount= BigDecimal("25.00"),
                    transactionType= TransactionType.PAYMENT,
                    relatedTransaction= null,
                ),
                Transaction(
                    transactionId= "TX10002",
                    fromAccountId= "ACC334455",
                    toAccountId= "ACC998877",
                    createdAt= "20/10/2018 17:33:43".toDateFormat()!!,
                    amount= BigDecimal("10.50"),
                    transactionType= TransactionType.PAYMENT,
                    relatedTransaction= null,
                ),
                Transaction(
                    transactionId= "TX10003",
                    fromAccountId= "ACC998877",
                    toAccountId= "ACC778899",
                    createdAt= "20/10/2018 18:00:00".toDateFormat()!!,
                    amount= BigDecimal("5.00"),
                    transactionType= TransactionType.PAYMENT,
                    relatedTransaction= null,
                ),
                Transaction(
                    transactionId= "TX10004",
                    fromAccountId= "ACC334455",
                    toAccountId= "ACC998877",
                    createdAt= "20/10/2018 19:45:00".toDateFormat()!!,
                    amount= BigDecimal("10.50"),
                    transactionType= TransactionType.REVERSAL,
                    relatedTransaction= "TX10002",
                ),
                Transaction(
                    transactionId= "TX10005",
                    fromAccountId= "ACC334455",
                    toAccountId= "ACC778899",
                    createdAt= "21/10/2018 09:30:00".toDateFormat()!!,
                    amount= BigDecimal("7.25"),
                    transactionType= TransactionType.PAYMENT,
                    relatedTransaction= null,
                ),
            )
            val inputData = InputData(
                "ACC334455",
                "20/10/2018 12:00:00".toDateFormat()!!,
                "20/10/2018 19:00:00".toDateFormat()!!
            )

            val relativeBalance = EvaluateTransaction(expectedOutput).getRelativeBalanceAndNumberOfTransaction(inputData)
            it("returns correct relative balance") {
                assertThat(relativeBalance.relativeBalance, equalTo(BigDecimal("-35.50")))
            }
            it("returns correct number of transaction") {
                assertThat(relativeBalance.numberOfTransaction, equalTo(2))
            }

        }

        context("when we call EvaluateTransaction is called with the input(accountId) that time period does not exits") {

            val expectedOutput = listOf<Transaction>(
                Transaction(
                    transactionId= "TX10001",
                    fromAccountId= "ACC334455",
                    toAccountId= "ACC778899",
                    createdAt= "20/10/2018 12:47:55".toDateFormat()!!,
                    amount= BigDecimal("25.00"),
                    transactionType= TransactionType.PAYMENT,
                    relatedTransaction= null,
                ),
                Transaction(
                    transactionId= "TX10002",
                    fromAccountId= "ACC334455",
                    toAccountId= "ACC998877",
                    createdAt= "20/10/2018 17:33:43".toDateFormat()!!,
                    amount= BigDecimal("10.50"),
                    transactionType= TransactionType.PAYMENT,
                    relatedTransaction= null,
                ),
                Transaction(
                    transactionId= "TX10003",
                    fromAccountId= "ACC998877",
                    toAccountId= "ACC778899",
                    createdAt= "20/10/2018 18:00:00".toDateFormat()!!,
                    amount= BigDecimal("5.00"),
                    transactionType= TransactionType.PAYMENT,
                    relatedTransaction= null,
                ),
                Transaction(
                    transactionId= "TX10004",
                    fromAccountId= "ACC334455",
                    toAccountId= "ACC998877",
                    createdAt= "20/10/2018 19:45:00".toDateFormat()!!,
                    amount= BigDecimal("10.50"),
                    transactionType= TransactionType.REVERSAL,
                    relatedTransaction= "TX10002",
                ),
                Transaction(
                    transactionId= "TX10005",
                    fromAccountId= "ACC334455",
                    toAccountId= "ACC778899",
                    createdAt= "21/10/2018 09:30:00".toDateFormat()!!,
                    amount= BigDecimal("7.25"),
                    transactionType= TransactionType.PAYMENT,
                    relatedTransaction= null,
                ),
            )
            val inputData = InputData(
                "ACC334455",
                "20/10/2020 12:00:00".toDateFormat()!!,
                "20/10/2020 19:00:00".toDateFormat()!!
            )

            val relativeBalance = EvaluateTransaction(expectedOutput).getRelativeBalanceAndNumberOfTransaction(inputData)
            it("returns correct relative balance i.e 0") {
                assertThat(relativeBalance.relativeBalance, equalTo(BigDecimal("0")))
            }
            it("returns correct number of transaction i.e 0") {
                assertThat(relativeBalance.numberOfTransaction, equalTo(0))
            }

        }

        context("when we call EvaluateTransaction is called with the input(accountId) that has 2 transaction and both are reversal") {

            val expectedOutput = listOf<Transaction>(
                Transaction(
                    transactionId= "TX10001",
                    fromAccountId= "ACC334455",
                    toAccountId= "ACC778899",
                    createdAt= "20/10/2018 12:47:55".toDateFormat()!!,
                    amount= BigDecimal("25.00"),
                    transactionType= TransactionType.REVERSAL,
                    relatedTransaction= null,
                ),
                Transaction(
                    transactionId= "TX10002",
                    fromAccountId= "ACC334455",
                    toAccountId= "ACC998877",
                    createdAt= "20/10/2018 17:33:43".toDateFormat()!!,
                    amount= BigDecimal("10.50"),
                    transactionType= TransactionType.REVERSAL,
                    relatedTransaction= null
                ),
                Transaction(
                    transactionId= "TX10003",
                    fromAccountId= "ACC998877",
                    toAccountId= "ACC778899",
                    createdAt= "20/10/2018 18:00:00".toDateFormat()!!,
                    amount= BigDecimal("5.00"),
                    transactionType= TransactionType.PAYMENT,
                    relatedTransaction= null,
                ),
                Transaction(
                    transactionId= "TX10004",
                    fromAccountId= "ACC334455",
                    toAccountId= "ACC998877",
                    createdAt= "20/10/2018 19:45:00".toDateFormat()!!,
                    amount= BigDecimal("10.50"),
                    transactionType= TransactionType.REVERSAL,
                    relatedTransaction= "TX10002",
                ),
                Transaction(
                    transactionId= "TX10005",
                    fromAccountId= "ACC334455",
                    toAccountId= "ACC778899",
                    createdAt= "21/10/2018 09:30:00".toDateFormat()!!,
                    amount= BigDecimal("7.25"),
                    transactionType= TransactionType.PAYMENT,
                    relatedTransaction= null,
                ),
            )
            val inputData = InputData(
                "ACC334455",
                "20/10/2018 12:00:00".toDateFormat()!!,
                "20/10/2018 19:00:00".toDateFormat()!!
            )

            val relativeBalance = EvaluateTransaction(expectedOutput).getRelativeBalanceAndNumberOfTransaction(inputData)
            it("returns correct relative balance i.e 0") {
                assertThat(relativeBalance.relativeBalance, equalTo(BigDecimal("-35.50")))
            }
            it("returns correct number of transaction i.e 0") {
                assertThat(relativeBalance.numberOfTransaction, equalTo(2))
            }

        }
    }
})