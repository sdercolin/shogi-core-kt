package com.sdercolin.shogicore.exception

import com.sdercolin.shogicore.Position

class InvalidPositionException(val position: Position) : Exception(
    "Trying to get an invalid position, position = $position"
)
