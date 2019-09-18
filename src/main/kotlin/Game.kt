package com.sdercolin.shogicore

class Game() {
    private val firstPlayer = Player(Color.BLACK)
    private val secondPlayer = Player(Color.WHITE)
    private val scenes = mutableListOf(Scene.empty)

    val history: List<Scene> = scenes.toList()
    val currentScene: Scene get() = scenes.last()

    val result: GameResult get() = currentScene.result

    var currentPlayer: Player = firstPlayer
        private set

    val movablePieces: List<Piece>
        get() = currentScene.pieces
            .filter { it.color == currentPlayer.color }
            .filter { currentScene.getPossibleMoves(it).isNotEmpty() }

    fun getPossibleMoves(piece: Piece): List<PossibleMove> {
        return currentScene.getPossibleMoves(piece)
    }

    fun take(move: Move): Scene {
        currentPlayer =
            if (currentPlayer == firstPlayer) secondPlayer
            else firstPlayer

        return currentScene.take(move).also {
            scenes.add(it)
        }
    }
}