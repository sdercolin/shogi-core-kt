package com.sdercolin.shogicore

import com.sdercolin.shogicore.exception.IllegalMoveException

data class Scene constructor(val pieces: List<Piece>) {

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

    fun getPossibleMoves(piece: Piece): List<PossibleMove> {
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

    private fun getPieceOn(position: Position): Piece? {
        return pieces.find { it.position == position }
    }

    companion object {
        val empty: Scene
            get() = Scene(listOf())
    }
}