package financial.transaction.analyser

import java.math.BigDecimal

data class AnalysedData(
    val relativeBalance: BigDecimal,
    val numberOfTransaction: Int
)
