import com.sdercolin.shogicore.Hello
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class HelloTest {

    @Test
    fun sayTest() {
        val hello = Hello()
        assertEquals(hello.say(), "World")
    }
}