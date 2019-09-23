package com.sdercolin.shogicore.exception

import com.sdercolin.shogicore.Piece
import com.sdercolin.shogicore.Position

class UnreachablePositionException(val piece: Piece, val targetPosition: Position) : Exception(
    "Trying to conduct or calculate an illegal move, piece = $piece, targetPosition = $targetPosition"
)
