package com.sdercolin.shogicore.exception

import com.sdercolin.shogicore.Move

class IllegalMoveException(val move: Move) : Exception(
    "Trying to conduct an illegal move, move = $move"
)
