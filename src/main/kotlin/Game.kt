package com.sdercolin.shogicore

class Game() {
    private val firstPlayer = Player(Color.BLACK)
    private val secondPlayer = Player(Color.WHITE)
    private val records: MutableList<Pair<Scene, Move?>> = mutableListOf(Scene.empty to null)

    val history: List<Pair<Scene, Move?>> get() = records.toList()
    val currentScene: Scene get() = records.last().first

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
            records.add(it to move)
        }
    }
}