package financial.transaction.analyser.utility

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date


fun String.toDateFormat(): Date? {
    return try {
        SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(this)
    } catch (e: ParseException) {
        null
    }
}
