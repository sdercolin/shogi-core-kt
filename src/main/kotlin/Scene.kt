package com.sdercolin.shogicore

class Scene private constructor() {

    fun take(step: Step): Scene {
        TODO()
    }

    val result: GameResult
        get() {
            TODO()
        }

    companion object {
        val empty: Scene
            get() = Scene()
    }
}