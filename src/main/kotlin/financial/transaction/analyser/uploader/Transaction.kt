package financial.transaction.analyser.uploader

import java.math.BigDecimal
import java.util.Date

data class Transaction(
    val transactionId: String,
    val fromAccountId: String,
    val toAccountId: String,
    val createdAt: Date,
    val amount: BigDecimal,
    val transactionType: TransactionType,
    val relatedTransaction: String? = null
)

enum class TransactionType {
    PAYMENT, REVERSAL;
}
