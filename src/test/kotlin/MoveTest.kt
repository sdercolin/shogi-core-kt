import com.sdercolin.shogicore.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class MoveTest {
    @Test
    fun simpleMoveTest() {
        val scene0 = Scene.initial
        val possibleMoves1 = scene0.getPossibleMoves(6, 6)
        assertEquals(1, possibleMoves1?.size)

        val move1 = possibleMoves1!![0].confirm(false)
        val scene1 = scene0.take(move1)
        assertNotNull(scene1.getPieceOn(Position(6, 5)))

        val possibleMoves2 = scene1.getPossibleMoves(7, 7)
        assertEquals(5, possibleMoves2?.size)
        val move2 = possibleMoves2!!.find { it.target == Position(2, 2) }?.confirm(true)
        assertNotNull(move2)
        val scene2 = scene1.take(move2!!)

        val blackHandPieces = scene2.getPiecesOn(Position.getHandPosition(Color.BLACK))
        assertEquals(1, blackHandPieces.size)
        val takenPiece = blackHandPieces[0]
        assert(takenPiece.color == Color.WHITE && takenPiece is Pawn)

        val newPiece = scene2.getPieceOn(Position(2, 2))
        assertNotNull(newPiece)
        assert(newPiece is Horse)
    }
}