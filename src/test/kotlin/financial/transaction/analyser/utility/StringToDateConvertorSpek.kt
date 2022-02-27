package financial.transaction.analyser.utility

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.text.DateFormat
import java.text.SimpleDateFormat

object StringToDateConvertorSpek : Spek({
    describe("StringToDateConvertor") {
        context("when string 20/10/2018 12:47:55 calls toDateFormat") {
            val resultDate = "20/10/2018 12:47:55".toDateFormat()
            val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val resultedDate = dateFormat.format(resultDate)
            it("returns correct year") {
                assertThat(resultedDate, equalTo("20/10/2018 12:47:55"))
            }
        }
        context("when string 20/10-2018 12:47:55 calls toDateFormat") {
            val resultDate = "20/10-2018 12:47:55".toDateFormat()
            it("returns correct date") {
                assertThat(resultDate, equalTo(null))
            }
        }
    }
})
