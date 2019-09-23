package com.sdercolin.shogicore.exception

import com.sdercolin.shogicore.Position

class IllegalOnBoardPositionException(val position: Position) : Exception(
    "Trying to get a single piece from hand position, position = $position"
)
