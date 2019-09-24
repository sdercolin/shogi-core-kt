import com.sdercolin.shogicore.Color
import com.sdercolin.shogicore.King
import com.sdercolin.shogicore.Pawn
import com.sdercolin.shogicore.Position
import com.sdercolin.shogicore.Rook
import com.sdercolin.shogicore.Scene
import org.junit.jupiter.api.Test

internal class SceneTest {
    @Test
    fun isCheckingTest() {
        val scene1 = Scene(
            listOf(
                King(Color.Black, Color.Black, Position(0, 8), 0),
                Rook(Color.Black, Color.Black, Position(8, 8), 1),
                King(Color.White, Color.White, Position(8, 0), 2)
            ),
            Color.Black
        )
        assert(scene1.isChecking)

        val scene2 = Scene(
            listOf(
                King(Color.Black, Color.Black, Position(0, 8), 0),
                Rook(Color.Black, Color.Black, Position(7, 8), 1),
                King(Color.White, Color.White, Position(8, 0), 2)
            ),
            Color.Black
        )
        assert(!scene2.isChecking)
    }

    @Test
    fun isCheckedTest() {
        val scene1 = Scene(
            listOf(
                King(Color.Black, Color.Black, Position(0, 8), 0),
                Rook(Color.Black, Color.Black, Position(8, 8), 1),
                King(Color.White, Color.White, Position(8, 0), 2)
            ),
            Color.White
        )
        assert(scene1.isChecked)

        val scene2 = Scene(
            listOf(
                King(Color.Black, Color.Black, Position(0, 8), 0),
                Rook(Color.Black, Color.Black, Position(7, 8), 1),
                King(Color.White, Color.White, Position(8, 0), 2)
            ),
            Color.White
        )
        assert(!scene2.isChecked)
    }

    @Test
    fun isCheckmatedTest() {
        val scene1 = Scene(
            listOf(
                King(Color.Black, Color.Black, Position(0, 8), 0),
                Rook(Color.Black, Color.Black, Position(8, 8), 1),
                Rook(Color.Black, Color.Black, Position(7, 8), 2),
                King(Color.White, Color.White, Position(8, 0), 3)
            ),
            Color.White
        )
        assert(scene1.isCheckmated)

        val scene2 = Scene(
            listOf(
                King(Color.Black, Color.Black, Position(0, 8), 0),
                Rook(Color.Black, Color.Black, Position(8, 8), 1),
                Rook(Color.Black, Color.Black, Position(7, 8), 2),
                King(Color.White, Color.White, Position(8, 0), 3),
                Pawn(Color.White, Color.White, Position.getHandPosition(Color.White), 3)
            ),
            Color.White
        )
        assert(!scene2.isCheckmated)
    }
}