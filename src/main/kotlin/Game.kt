package com.sdercolin.shogicore

import com.sdercolin.shogicore.exception.IllegalMoveException
import com.sdercolin.shogicore.exception.PieceNotExistingException

/**
 * Main class for game procedures
 * i.e.
 * <pre>
 * <code>
 *     // Initialize a game
 *     val game = Game()
 *
 *     // Get and display a scene
 *     val scene = game.currentScene
 *
 *     // Conduct a move
 *     val piece = selectFrom(game.movablePieces)
 *     val possibleMove = selectFrom(game.getPossibleMoves(piece))
 *     val move = possibleMove.confirm(false)
 *     val newScene = game.take(move)
 *
 *     // Check result
 *     val result = game.result
 * </code>
 * </pre>
 */
class Game() {
    private val firstPlayer = Player(Color.BLACK)
    private val secondPlayer = Player(Color.WHITE)
    private val records: MutableList<Pair<Scene, Move?>> = mutableListOf(Scene.empty to null)

    /**
     * A list containing pairs of scene and the move that leads to the scene, in chronological order
     */
    val history: List<Pair<Scene, Move?>> get() = records.toList()

    /**
     * The latest scene
     */
    val currentScene: Scene get() = records.last().first

    /**
     * Result of the game at present
     */
    val result: GameResult get() = currentScene.result

    /**
     * Player who are suppose to conduct the next move
     */
    var currentPlayer: Player = firstPlayer
        private set

    /**
     * A list containing all the pieces that are able to be moved by the current player as the next move
     */
    val movablePieces: List<Piece>
        get() = currentScene.pieces
            .filter { it.color == currentPlayer.color }
            .filter { currentScene.getPossibleMoves(it).isNotEmpty() }

    /**
     * Returns all the options for the next move by the given piece
     * @param piece should be one of the item in the list returned by {@code movablePieces} for the same scene
     * @throws PieceNotExistingException when the given piece is not exiting in the current scene
     */
    fun getPossibleMoves(piece: Piece): List<PossibleMove> {
        return currentScene.getPossibleMoves(piece)
    }

    /**
     * Conduct a move and return the next scene after the move it processed
     * @param move should be created by one of the item in the list returned
     * by {@code getPossibleMoves()} for the same scene
     * @throws IllegalMoveException when the given move cannot be conducted in the current scene
     */
    fun take(move: Move): Scene {
        currentPlayer =
            if (currentPlayer == firstPlayer) secondPlayer
            else firstPlayer

        return currentScene.take(move).also {
            records.add(it to move)
        }
    }
}
