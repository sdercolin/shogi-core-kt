package com.sdercolin.shogicore

data class Scene constructor(val pieces: List<Piece>) {

    fun take(move: Move): Scene {
        TODO()
    }

    val result: GameResult
        get() = TODO()

    fun getPossibleMoves(piece: Piece): List<Move> {
        TODO()
    }

    companion object {
        val empty: Scene
            get() = Scene(listOf())
    }
}