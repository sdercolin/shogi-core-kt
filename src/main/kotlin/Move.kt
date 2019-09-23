package com.sdercolin.shogicore

import com.sdercolin.shogicore.exception.IllegalPromotionException

/**
 * A class defining a move that can be passed to an ongoing game
 * @param piece the piece to conduct this move
 * @param target the target position to move to
 * @param promote if the piece should promotes after moving
 */
data class Move(val piece: Piece, val target: Position, val promote: Boolean)

/**
 * A class defining a possible move that can create a certain {@code Move}
 * @param piece the piece to conduct this move
 * @param target the target position to move to
 * @param canPromote if the piece is able to promote after moving
 */
data class PossibleMove(val piece: Piece, val target: Position, val canPromote: Boolean) {

    /**
     * Takes the decision of the player if to conduct a promotion and returns a confirmed {@code Move}
     * @throws IllegalPromotionException when the desired promotion cannot be conducted
     */
    fun confirm(promote: Boolean): Move {
        if (!canPromote && promote) throw IllegalPromotionException(this)
        return Move(piece, target, promote)
    }
}
