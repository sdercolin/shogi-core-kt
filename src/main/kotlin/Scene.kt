package com.sdercolin.shogicore

import com.sdercolin.shogicore.exception.IllegalMoveException
import com.sdercolin.shogicore.exception.IllegalOnBoardPositionException
import com.sdercolin.shogicore.exception.PieceNotExistingException

/**
 * Class describing the situation of the game at a certain timing
 * @param pieces contains all the pieces in the scene
 * @param playingColor the color of the player who can conduct the next move
 */
data class Scene constructor(val pieces: List<Piece>, val playingColor: Color) {

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

        return Scene(
            pieces = pieceList.toList(),
            playingColor = playingColor.next()
        )
    }

    /**
     * Result of the game in this scene
     */
    val result: GameResult by lazy {
        when {
            isCheckmated -> GameResult.Checkmate(playingColor.next())
            else -> GameResult.InGame
        }
    }

    /**
     * A list containing all the pieces that are able to be moved by the current player as the next move
     */
    val movablePieces: List<Piece>
        get() = pieces
            .filter { it.color == playingColor }
            .filter { getPossibleMoves(it).isNotEmpty() }

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

    internal val allMoves: List<Move>
        get() = movablePieces
            .flatMap { getPossibleMoves(it) }
            .flatMap {
                if (it.canPromote) listOf(it.confirm(true), it.confirm(false))
                else listOf(it.confirm(false))
            }

    internal val isCheckmated: Boolean
        get() {
            if (!isChecked) return false
            val nextScenes = allMoves.map { this.take(it) }
            return nextScenes.all { it.isChecking }
        }

    internal val isChecked: Boolean
        get() {
            val nextScene = this.copy(pieces = pieces.toList(), playingColor = playingColor.next())
            return nextScene.isChecking
        }

    internal val isChecking: Boolean
        get() {
            val king = pieces.find { it is King && it.color != playingColor }!!
            return allMoves.any { it.target == king.position }
        }

    companion object {
        internal val empty: Scene
            get() = Scene(listOf(), Color.Black)

        internal val initial: Scene
            get() = Scene(
                listOf(
                    King(Color.Black, Color.Black, Position(4, 8), 0),
                    Gold(Color.Black, Color.Black, Position(3, 8), 1),
                    Gold(Color.Black, Color.Black, Position(5, 8), 2),
                    Silver(Color.Black, Color.Black, Position(2, 8), 3),
                    Silver(Color.Black, Color.Black, Position(6, 8), 4),
                    Knight(Color.Black, Color.Black, Position(1, 8), 5),
                    Knight(Color.Black, Color.Black, Position(7, 8), 6),
                    Lance(Color.Black, Color.Black, Position(0, 8), 7),
                    Lance(Color.Black, Color.Black, Position(8, 8), 8),
                    Rook(Color.Black, Color.Black, Position(1, 7), 9),
                    Bishop(Color.Black, Color.Black, Position(7, 7), 10),
                    Pawn(Color.Black, Color.Black, Position(0, 6), 11),
                    Pawn(Color.Black, Color.Black, Position(1, 6), 12),
                    Pawn(Color.Black, Color.Black, Position(2, 6), 13),
                    Pawn(Color.Black, Color.Black, Position(3, 6), 14),
                    Pawn(Color.Black, Color.Black, Position(4, 6), 15),
                    Pawn(Color.Black, Color.Black, Position(5, 6), 16),
                    Pawn(Color.Black, Color.Black, Position(6, 6), 17),
                    Pawn(Color.Black, Color.Black, Position(7, 6), 18),
                    Pawn(Color.Black, Color.Black, Position(8, 6), 19),
                    King(Color.White, Color.White, Position(4, 0), 20),
                    Gold(Color.White, Color.White, Position(3, 0), 21),
                    Gold(Color.White, Color.White, Position(5, 0), 22),
                    Silver(Color.White, Color.White, Position(2, 0), 23),
                    Silver(Color.White, Color.White, Position(6, 0), 24),
                    Knight(Color.White, Color.White, Position(1, 0), 25),
                    Knight(Color.White, Color.White, Position(7, 0), 26),
                    Lance(Color.White, Color.White, Position(0, 0), 27),
                    Lance(Color.White, Color.White, Position(8, 0), 28),
                    Rook(Color.White, Color.White, Position(7, 1), 29),
                    Bishop(Color.White, Color.White, Position(1, 1), 30),
                    Pawn(Color.White, Color.White, Position(0, 2), 31),
                    Pawn(Color.White, Color.White, Position(1, 2), 32),
                    Pawn(Color.White, Color.White, Position(2, 2), 33),
                    Pawn(Color.White, Color.White, Position(3, 2), 34),
                    Pawn(Color.White, Color.White, Position(4, 2), 35),
                    Pawn(Color.White, Color.White, Position(5, 2), 36),
                    Pawn(Color.White, Color.White, Position(6, 2), 37),
                    Pawn(Color.White, Color.White, Position(7, 2), 38),
                    Pawn(Color.White, Color.White, Position(8, 2), 39)
                ),
                Color.Black
            )
    }
}
