package financial.transaction.analyser

import financial.transaction.analyser.uploader.Transaction
import financial.transaction.analyser.uploader.TransactionType
import java.math.BigDecimal

class EvaluateTransaction(val transaction: List<Transaction>) {
    fun getRelativeBalanceAndNumberOfTransaction(inputData: InputData): AnalysedData {
        val relativeTransaction =  transaction.filter {
            (( it.relatedTransaction == null || it.transactionType == TransactionType.PAYMENT) &&
                    (it.fromAccountId == inputData.accountId || it.toAccountId == inputData.accountId))
                    && ((inputData.fromDateFormat <= it.createdAt) && (it.createdAt <= inputData.toDateFormat))
        }.map {
            if (it.fromAccountId == inputData.accountId) {
                it.amount.multiply(BigDecimal(-1))
            } else {
                it.amount
            }
        }
        return AnalysedData(
            relativeBalance = relativeTransaction.sumOf { it },
            numberOfTransaction = relativeTransaction.size
        )
    }
}
