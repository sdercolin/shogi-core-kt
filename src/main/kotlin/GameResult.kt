package com.sdercolin.shogicore

/**
 * The result of a game
 */
sealed class GameResult(open val winnerColor: Color?) {
    object InGame : GameResult(null)
    data class Resignation(override val winnerColor: Color) : GameResult(winnerColor)
    data class Checkmate(override val winnerColor: Color) : GameResult(winnerColor)
    data class TimeForfeit(override val winnerColor: Color) : GameResult(winnerColor)
    object Impasse : GameResult(null)
    object Repetition : GameResult(null)
    data class ImpasseFailure(override val winnerColor: Color) : GameResult(winnerColor)
    data class EnteringKing(override val winnerColor: Color?) : GameResult(winnerColor)
    data class TwoPawns(override val winnerColor: Color) : GameResult(winnerColor)
    data class DropPawnMate(override val winnerColor: Color) : GameResult(winnerColor)
    data class RepetitionMate(override val winnerColor: Color) : GameResult(winnerColor)
    data class LeftInCheck(override val winnerColor: Color) : GameResult(winnerColor)
    data class PieceWithoutNextMove(override val winnerColor: Color) : GameResult(winnerColor)
}
