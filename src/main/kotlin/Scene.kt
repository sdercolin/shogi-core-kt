package com.sdercolin.shogicore

data class Scene constructor(val pieces: List<Piece>) {

    fun take(move: Move): Scene {
        TODO()
    }

    val result: GameResult
        get() = TODO()

    fun getPossibleMoves(piece: Piece): List<PossibleMove> {
        val movablePositions = piece.movablePositions
            .filter { getPieceOn(it)?.currentColor != piece.currentColor }
            .filter { piece.getRouteTo(it).all { position -> getPieceOn(position) == null } }
        return movablePositions.map { target ->
            PossibleMove(piece, target, canPromote(piece, target))
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