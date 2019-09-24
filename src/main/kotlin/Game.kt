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
    private val firstPlayer = Player(Color.Black)
    private val secondPlayer = Player(Color.White)
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
    var result: GameResult = GameResult.InGame
        private set


    /**
     * Player who are suppose to conduct the next move
     */
    val currentPlayer: Player
        get() = listOf(firstPlayer, secondPlayer).find { it.color == currentScene.playingColor }!!

    /**
     * A list containing all the pieces that are able to be moved by the current player as the next move
     */
    val movablePieces: List<Piece>
        get() = currentScene.movablePieces

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
    fun take(move: Move): Scene =
        currentScene.take(move).also {
            records.add(it to move)
            result = it.result
        }

    /**
     * Finish the game with one of the players giving up the game
     */
    fun resign(): GameResult.Resignation {
        return GameResult.Resignation(currentPlayer.color).also {
            result = it
        }
    }
}
