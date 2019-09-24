package com.sdercolin.shogicore.exception

import com.sdercolin.shogicore.Move

class InvalidMoveException(val move: Move) : Exception(
    "Trying to conduct an invalid move, move = $move"
)
