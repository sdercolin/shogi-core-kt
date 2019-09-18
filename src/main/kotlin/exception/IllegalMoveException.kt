package com.sdercolin.shogicore.exception

import com.sdercolin.shogicore.Move
import com.sdercolin.shogicore.Piece
import com.sdercolin.shogicore.Position
import com.sdercolin.shogicore.PossibleMove

class IllegalMoveException(val move: Move) : Exception(
    "Trying to conduct an illegal move, move = $move"
)