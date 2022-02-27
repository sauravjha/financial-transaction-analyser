package financial.transaction.analyser

import java.util.Date

data class InputData(
    val accountId: String,
    val fromDateFormat: Date,
    val toDateFormat: Date
    )