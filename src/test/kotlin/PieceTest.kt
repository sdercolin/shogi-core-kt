import com.sdercolin.shogicore.Bishop
import com.sdercolin.shogicore.Color
import com.sdercolin.shogicore.Dragon
import com.sdercolin.shogicore.Gold
import com.sdercolin.shogicore.Horse
import com.sdercolin.shogicore.King
import com.sdercolin.shogicore.Knight
import com.sdercolin.shogicore.Lance
import com.sdercolin.shogicore.Pawn
import com.sdercolin.shogicore.Position
import com.sdercolin.shogicore.PromotedKnight
import com.sdercolin.shogicore.PromotedLance
import com.sdercolin.shogicore.PromotedPawn
import com.sdercolin.shogicore.PromotedSilver
import com.sdercolin.shogicore.Rook
import com.sdercolin.shogicore.Silver
import com.sdercolin.shogicore.exception.UnreachablePositionException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class PieceTest {
    @Test
    fun testKingMove1() {
        val piece = King(Color.Black, Color.Black, Position(4, 0), 0)
        val expected = listOf(
            5 to 0,
            3 to 0,
            5 to 1,
            3 to 1,
            4 to 1
        ).map { Position(it.first, it.second) }.toSet()
        val actual = piece.movablePositions.toSet()
        assertEquals(expected, actual)
    }

    @Test
    fun testKingMove2() {
        val piece = King(Color.Black, Color.Black, Position(0, 0), 0)
        val expected = listOf(
            1 to 0,
            0 to 1,
            1 to 1
        ).map { Position(it.first, it.second) }.toSet()
        val actual = piece.movablePositions.toSet()
        assertEquals(expected, actual)
    }

    @Test
    fun testKingMove3() {
        val piece = King(Color.Black, Color.Black, Position(3, 3), 0)
        val expected = listOf(
            2 to 3,
            4 to 3,
            3 to 2,
            3 to 4,
            2 to 2,
            4 to 4,
            2 to 4,
            4 to 2
        ).map { Position(it.first, it.second) }.toSet()
        val actual = piece.movablePositions.toSet()
        assertEquals(expected, actual)
    }

    @Test
    fun testRookMove1() {
        val piece = Rook(Color.Black, Color.Black, Position(0, 0), 0)
        val expectedMovablePositions = listOf(
            0 to 1,
            0 to 2,
            0 to 3,
            0 to 4,
            0 to 5,
            0 to 6,
            0 to 7,
            0 to 8,
            1 to 0,
            2 to 0,
            3 to 0,
            4 to 0,
            5 to 0,
            6 to 0,
            7 to 0,
            8 to 0
        ).map { Position(it.first, it.second) }.toSet()
        val actualMovablePositions = piece.movablePositions.toSet()
        assertEquals(expectedMovablePositions, actualMovablePositions)

        val target = Position(5, 0)
        val expectedRoute = listOf(
            1 to 0,
            2 to 0,
            3 to 0,
            4 to 0
        ).map { Position(it.first, it.second) }
        val actualRoute = piece.getRouteTo(target)
        assertEquals(expectedRoute, actualRoute)
    }

    @Test
    fun testRookMove2() {
        val piece = Rook(Color.Black, Color.Black, Position(4, 4), 0)
        val expectedMovablePositions = listOf(
            4 to 0,
            4 to 1,
            4 to 2,
            4 to 3,
            4 to 5,
            4 to 6,
            4 to 7,
            4 to 8,
            0 to 4,
            1 to 4,
            2 to 4,
            3 to 4,
            5 to 4,
            6 to 4,
            7 to 4,
            8 to 4
        ).map { Position(it.first, it.second) }.toSet()
        val actualMovablePositions = piece.movablePositions.toSet()
        assertEquals(expectedMovablePositions, actualMovablePositions)

        val wrongTarget = Position(5, 5)
        assertThrows(UnreachablePositionException::class.java) {
            piece.getRouteTo(wrongTarget)
        }

        val target = Position(4, 8)
        val expectedRoute = listOf(
            4 to 5,
            4 to 6,
            4 to 7
        ).map { Position(it.first, it.second) }
        val actualRoute = piece.getRouteTo(target)
        assertEquals(expectedRoute, actualRoute)
    }

    @Test
    fun testDragonMove1() {
        val piece = Dragon(Color.Black, Color.Black, Position(0, 0), 0)
        val expectedMovablePositions = listOf(
            0 to 1,
            0 to 2,
            0 to 3,
            0 to 4,
            0 to 5,
            0 to 6,
            0 to 7,
            0 to 8,
            1 to 0,
            2 to 0,
            3 to 0,
            4 to 0,
            5 to 0,
            6 to 0,
            7 to 0,
            8 to 0,
            1 to 1
        ).map { Position(it.first, it.second) }.toSet()
        val actualMovablePositions = piece.movablePositions.toSet()
        assertEquals(expectedMovablePositions, actualMovablePositions)

        val target1 = Position(5, 0)
        val expectedRoute1 = listOf(
            1 to 0,
            2 to 0,
            3 to 0,
            4 to 0
        ).map { Position(it.first, it.second) }
        val actualRoute1 = piece.getRouteTo(target1)
        assertEquals(expectedRoute1, actualRoute1)

        val target2 = Position(1, 1)
        val expectedRoute2 = listOf<Position>()
        val actualRoute2 = piece.getRouteTo(target2)
        assertEquals(expectedRoute2, actualRoute2)
    }

    @Test
    fun testDragonMove2() {
        val piece = Dragon(Color.Black, Color.Black, Position(4, 4), 0)
        val expectedMovablePositions = listOf(
            4 to 0,
            4 to 1,
            4 to 2,
            4 to 3,
            4 to 5,
            4 to 6,
            4 to 7,
            4 to 8,
            0 to 4,
            1 to 4,
            2 to 4,
            3 to 4,
            5 to 4,
            6 to 4,
            7 to 4,
            8 to 4,
            5 to 5,
            3 to 3,
            3 to 5,
            5 to 3
        ).map { Position(it.first, it.second) }.toSet()
        val actualMovablePositions = piece.movablePositions.toSet()
        assertEquals(expectedMovablePositions, actualMovablePositions)

        val wrongTarget = Position(5, 6)
        assertThrows(UnreachablePositionException::class.java) {
            piece.getRouteTo(wrongTarget)
        }

        val target = Position(4, 8)
        val expectedRoute = listOf(
            4 to 5,
            4 to 6,
            4 to 7
        ).map { Position(it.first, it.second) }
        val actualRoute = piece.getRouteTo(target)
        assertEquals(expectedRoute, actualRoute)
    }

    @Test
    fun testBishopMove1() {
        val piece = Bishop(Color.Black, Color.Black, Position(0, 0), 0)
        val expectedMovablePositions = listOf(
            1 to 1,
            2 to 2,
            3 to 3,
            4 to 4,
            5 to 5,
            6 to 6,
            7 to 7,
            8 to 8
        ).map { Position(it.first, it.second) }.toSet()
        val actualMovablePositions = piece.movablePositions.toSet()
        assertEquals(expectedMovablePositions, actualMovablePositions)

        val target = Position(5, 5)
        val expectedRoute = listOf(
            1 to 1,
            2 to 2,
            3 to 3,
            4 to 4
        ).map { Position(it.first, it.second) }
        val actualRoute = piece.getRouteTo(target)
        assertEquals(expectedRoute, actualRoute)
    }

    @Test
    fun testBishopMove2() {
        val piece = Bishop(Color.Black, Color.Black, Position(4, 4), 0)
        val expectedMovablePositions = listOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 3,
            5 to 5,
            6 to 6,
            7 to 7,
            8 to 8,
            0 to 8,
            1 to 7,
            2 to 6,
            3 to 5,
            5 to 3,
            6 to 2,
            7 to 1,
            8 to 0
        ).map { Position(it.first, it.second) }.toSet()
        val actualMovablePositions = piece.movablePositions.toSet()
        assertEquals(expectedMovablePositions, actualMovablePositions)

        val target = Position(7, 1)
        val expectedRoute = listOf(
            5 to 3,
            6 to 2
        ).map { Position(it.first, it.second) }
        val actualRoute = piece.getRouteTo(target)
        assertEquals(expectedRoute, actualRoute)
    }

    @Test
    fun testHorseMove() {
        val piece = Horse(Color.Black, Color.Black, Position(4, 4), 0)
        val expectedMovablePositions = listOf(
            0 to 0,
            1 to 1,
            2 to 2,
            3 to 3,
            5 to 5,
            6 to 6,
            7 to 7,
            8 to 8,
            0 to 8,
            1 to 7,
            2 to 6,
            3 to 5,
            5 to 3,
            6 to 2,
            7 to 1,
            8 to 0,
            3 to 4,
            5 to 4,
            4 to 3,
            4 to 5
        ).map { Position(it.first, it.second) }.toSet()
        val actualMovablePositions = piece.movablePositions.toSet()
        assertEquals(expectedMovablePositions, actualMovablePositions)

        val target1 = Position(7, 7)
        val expectedRoute1 = listOf(
            5 to 5,
            6 to 6
        ).map { Position(it.first, it.second) }
        val actualRoute1 = piece.getRouteTo(target1)
        assertEquals(expectedRoute1, actualRoute1)

        val target2 = Position(4, 5)
        val expectedRoute2 = listOf<Position>()
        val actualRoute2 = piece.getRouteTo(target2)
        assertEquals(expectedRoute2, actualRoute2)
    }

    @Test
    fun testGoldLikeMove1() {
        val pieces = listOf(
            Gold(Color.Black, Color.Black, Position(4, 4), 0),
            PromotedKnight(Color.Black, Color.Black, Position(4, 4), 0),
            PromotedLance(Color.Black, Color.Black, Position(4, 4), 0),
            PromotedSilver(Color.Black, Color.Black, Position(4, 4), 0),
            PromotedPawn(Color.Black, Color.Black, Position(4, 4), 0)
        )
        val expectedMovablePositions = listOf(
            4 to 3,
            3 to 3,
            5 to 3,
            3 to 4,
            5 to 4,
            4 to 5
        ).map { Position(it.first, it.second) }.toSet()
        val actualMovablePositions = pieces.map { it.movablePositions.toSet() }
        actualMovablePositions.forEach {
            assertEquals(expectedMovablePositions, it)
        }
    }

    @Test
    fun testGoldLikeMove2() {
        val pieces = listOf(
            Gold(Color.White, Color.White, Position(4, 4), 0),
            PromotedKnight(Color.White, Color.White, Position(4, 4), 0),
            PromotedLance(Color.White, Color.White, Position(4, 4), 0),
            PromotedSilver(Color.White, Color.White, Position(4, 4), 0),
            PromotedPawn(Color.White, Color.White, Position(4, 4), 0)
        )
        val expectedMovablePositions = listOf(
            4 to 5,
            3 to 5,
            5 to 5,
            3 to 4,
            5 to 4,
            4 to 3
        ).map { Position(it.first, it.second) }.toSet()
        val actualMovablePositions = pieces.map { it.movablePositions.toSet() }
        actualMovablePositions.forEach {
            assertEquals(expectedMovablePositions, it)
        }
    }

    @Test
    fun testSilverMove1() {
        val piece = Silver(Color.Black, Color.Black, Position(4, 4), 0)
        val expectedMovablePositions = listOf(
            4 to 3,
            3 to 3,
            5 to 3,
            3 to 5,
            5 to 5
        ).map { Position(it.first, it.second) }.toSet()
        val actualMovablePositions = piece.movablePositions.toSet()
        assertEquals(expectedMovablePositions, actualMovablePositions)
    }

    @Test
    fun testSilverMove2() {
        val piece = Silver(Color.Black, Color.White, Position(4, 4), 0)
        val expectedMovablePositions = listOf(
            4 to 5,
            3 to 5,
            5 to 5,
            3 to 3,
            5 to 3
        ).map { Position(it.first, it.second) }.toSet()
        val actualMovablePositions = piece.movablePositions.toSet()
        assertEquals(expectedMovablePositions, actualMovablePositions)
    }

    @Test
    fun testKnightMove1() {
        val piece = Knight(Color.Black, Color.Black, Position(4, 4), 0)
        val expectedMovablePositions = listOf(
            3 to 2,
            5 to 2
        ).map { Position(it.first, it.second) }.toSet()
        val actualMovablePositions = piece.movablePositions.toSet()
        assertEquals(expectedMovablePositions, actualMovablePositions)
    }

    @Test
    fun testKnightMove2() {
        val piece = Knight(Color.Black, Color.Black, Position(4, 1), 0)
        val expectedMovablePositions = setOf<Position>()
        val actualMovablePositions = piece.movablePositions.toSet()
        assertEquals(expectedMovablePositions, actualMovablePositions)
    }

    @Test
    fun testLanceMove() {
        val piece = Lance(Color.Black, Color.Black, Position(4, 4), 0)
        val expectedMovablePositions = listOf(
            4 to 3,
            4 to 2,
            4 to 1,
            4 to 0
        ).map { Position(it.first, it.second) }.toSet()
        val actualMovablePositions = piece.movablePositions.toSet()
        assertEquals(expectedMovablePositions, actualMovablePositions)
    }

    @Test
    fun testPawnMove() {
        val piece = Pawn(Color.Black, Color.Black, Position(4, 4), 0)
        val expectedMovablePositions = listOf(
            4 to 3
        ).map { Position(it.first, it.second) }.toSet()
        val actualMovablePositions = piece.movablePositions.toSet()
        assertEquals(expectedMovablePositions, actualMovablePositions)
    }
}
