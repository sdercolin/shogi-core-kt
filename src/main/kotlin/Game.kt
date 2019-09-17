package com.sdercolin.shogicore

class Game(private val firstPlayer: Player, private val secondPlayer: Player) {

    private val scenes = mutableListOf(Scene.empty)

    val history: List<Scene> = scenes.toList()
    val currentScene: Scene get() = scenes.last()

    val result: GameResult get() = currentScene.result

    fun take(step: Step): Scene = currentScene.take(step)
}