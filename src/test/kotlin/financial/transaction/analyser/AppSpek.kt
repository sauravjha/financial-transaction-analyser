package financial.transaction.analyser

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object AppSpek: Spek({
    describe("App") {
        context("when the greeting field is been called") {
            it("returns the greeting Hello World.") {
                //assertThat(App().greeting, equalTo("Hello world."))
            }
        }
    }
})