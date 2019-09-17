package com.sdercolin.shogicore

data class Scene constructor(val pieces: List<Piece>) {

    fun take(step: Step): Scene {
        TODO()
    }

    val result: GameResult
        get() = TODO()

    fun getPossibleSteps(piece: Piece): List<Step> {
        TODO()
    }

    companion object {
        val empty: Scene
            get() = Scene(listOf())
    }
}