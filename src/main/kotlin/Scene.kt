package com.sdercolin.shogicore

import com.sdercolin.shogicore.exception.IllegalMoveException
import com.sdercolin.shogicore.exception.IllegalOnBoardPositionException
import com.sdercolin.shogicore.exception.PieceNotExistingException

/**
 * Class describing the situation of the game at a certain timing
 * @param pieces contains all the pieces in the scene
 */
data class Scene constructor(val pieces: List<Piece>) {

    /**
     * Returns the next scene if the given {@code Move} is conducted
     * @throws IllegalMoveException when the given move cannot be conducted in the current scene
     */
    fun take(move: Move): Scene {
        ensureMoveLegal(move)
        val piece = move.piece
        val newPiece = piece.move(move.target, move.promote)
        val pieceList = pieces.toMutableList()
        pieceList.indexOf(piece).let { pieceList[it] = newPiece }

        val takenPiece = getPieceOn(move.target)
        if (takenPiece != null) {
            val newTakenPiece = takenPiece.beTaken()
            pieceList.indexOf(takenPiece).let { pieceList[it] = newTakenPiece }
        }

        return Scene(pieces = pieceList.toList())
    }

    /**
     * Result of the game in this scene
     */
    val result: GameResult
        get() = TODO()

    private fun ensureMoveLegal(move: Move) {
        val possibleMove = getPossibleMoves(move.piece).find {
            it.piece == move.piece && it.target == move.target
        }
        if (possibleMove != null) {
            if (possibleMove.canPromote || !move.promote) return
        }
        throw IllegalMoveException(move)
    }

    internal fun getPossibleMoves(x: Int, y: Int): List<PossibleMove>? {
        return getPossibleMoves(Position(x, y))
    }

    /**
     * Returns a list containing all the possible moves by the piece on the given {@code Position} on the board
     * When there is not piece on the given point, {@code null} is returned
     * @param onBoardPosition should be a point on the board
     * @throws IllegalOnBoardPositionException when the given {@code Position} is not on the board
     */
    fun getPossibleMoves(onBoardPosition: Position): List<PossibleMove>? {
        if (!onBoardPosition.isOnBoard) throw IllegalOnBoardPositionException(onBoardPosition)
        return getPieceOn(onBoardPosition)?.let { getPossibleMoves(it) }
    }

    /**
     * Returns a list containing all the possible moves by the given {@code Piece}
     * @param piece should be one of the item in {@code this.pieces}
     * @throws PieceNotExistingException when the given piece is not exiting in this scene
     */
    fun getPossibleMoves(piece: Piece): List<PossibleMove> {
        if (!pieces.contains(piece)) throw PieceNotExistingException(piece, this)
        return if (piece.position.isOnBoard) {
            val movablePositions = piece.movablePositions
                .filter { getPieceOn(it)?.currentColor != piece.currentColor }
                .filter { piece.getRouteTo(it).all { position -> getPieceOn(position) == null } }
            movablePositions.map { target ->
                PossibleMove(piece, target, canPromote(piece, target))
            }
        } else {
            val droppablePositions = Position.wholeBoard
                .filter { getPieceOn(it) == null }
            droppablePositions.map { target ->
                PossibleMove(piece, target, canPromote = false)
            }
        }
    }

    private fun canPromote(piece: Piece, target: Position): Boolean {
        val promotableZone = Position.getPromotableZone(piece.currentColor)
        return (piece.position in promotableZone || target in promotableZone) && piece.promote() != null
    }

    internal fun getPieceOn(position: Position): Piece? {
        if (!position.isOnBoard) throw IllegalOnBoardPositionException(position)
        return pieces.find { it.position == position }
    }

    internal fun getPiecesOn(position: Position): List<Piece> {
        return pieces.filter { it.position == position }
    }

    companion object {
        internal val empty: Scene
            get() = Scene(listOf())

        internal val initial: Scene
            get() = Scene(
                listOf(
                    King(Color.BLACK, Color.BLACK, Position(4, 8), 0),
                    Gold(Color.BLACK, Color.BLACK, Position(3, 8), 1),
                    Gold(Color.BLACK, Color.BLACK, Position(5, 8), 2),
                    Silver(Color.BLACK, Color.BLACK, Position(2, 8), 3),
                    Silver(Color.BLACK, Color.BLACK, Position(6, 8), 4),
                    Knight(Color.BLACK, Color.BLACK, Position(1, 8), 5),
                    Knight(Color.BLACK, Color.BLACK, Position(7, 8), 6),
                    Lance(Color.BLACK, Color.BLACK, Position(0, 8), 7),
                    Lance(Color.BLACK, Color.BLACK, Position(8, 8), 8),
                    Rook(Color.BLACK, Color.BLACK, Position(1, 7), 9),
                    Bishop(Color.BLACK, Color.BLACK, Position(7, 7), 10),
                    Pawn(Color.BLACK, Color.BLACK, Position(0, 6), 11),
                    Pawn(Color.BLACK, Color.BLACK, Position(1, 6), 12),
                    Pawn(Color.BLACK, Color.BLACK, Position(2, 6), 13),
                    Pawn(Color.BLACK, Color.BLACK, Position(3, 6), 14),
                    Pawn(Color.BLACK, Color.BLACK, Position(4, 6), 15),
                    Pawn(Color.BLACK, Color.BLACK, Position(5, 6), 16),
                    Pawn(Color.BLACK, Color.BLACK, Position(6, 6), 17),
                    Pawn(Color.BLACK, Color.BLACK, Position(7, 6), 18),
                    Pawn(Color.BLACK, Color.BLACK, Position(8, 6), 19),
                    King(Color.WHITE, Color.WHITE, Position(4, 0), 20),
                    Gold(Color.WHITE, Color.WHITE, Position(3, 0), 21),
                    Gold(Color.WHITE, Color.WHITE, Position(5, 0), 22),
                    Silver(Color.WHITE, Color.WHITE, Position(2, 0), 23),
                    Silver(Color.WHITE, Color.WHITE, Position(6, 0), 24),
                    Knight(Color.WHITE, Color.WHITE, Position(1, 0), 25),
                    Knight(Color.WHITE, Color.WHITE, Position(7, 0), 26),
                    Lance(Color.WHITE, Color.WHITE, Position(0, 0), 27),
                    Lance(Color.WHITE, Color.WHITE, Position(8, 0), 28),
                    Rook(Color.WHITE, Color.WHITE, Position(7, 1), 29),
                    Bishop(Color.WHITE, Color.WHITE, Position(1, 1), 30),
                    Pawn(Color.WHITE, Color.WHITE, Position(0, 2), 31),
                    Pawn(Color.WHITE, Color.WHITE, Position(1, 2), 32),
                    Pawn(Color.WHITE, Color.WHITE, Position(2, 2), 33),
                    Pawn(Color.WHITE, Color.WHITE, Position(3, 2), 34),
                    Pawn(Color.WHITE, Color.WHITE, Position(4, 2), 35),
                    Pawn(Color.WHITE, Color.WHITE, Position(5, 2), 36),
                    Pawn(Color.WHITE, Color.WHITE, Position(6, 2), 37),
                    Pawn(Color.WHITE, Color.WHITE, Position(7, 2), 38),
                    Pawn(Color.WHITE, Color.WHITE, Position(8, 2), 39)
                )
            )
    }
}
