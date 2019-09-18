package com.sdercolin.shogicore

import com.sdercolin.shogicore.exception.IllegalPromotionException

data class Move(val piece: Piece, val target: Position, val promote: Boolean) {
}

data class PossibleMove(val piece: Piece, val target: Position, val canPromote: Boolean) {

    fun confirmPromotion(promote: Boolean): Move {
        if (!canPromote && promote) throw IllegalPromotionException(this)
        return Move(piece, target, promote)
    }
}