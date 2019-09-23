package com.sdercolin.shogicore.exception

import com.sdercolin.shogicore.Position

class IllegalPositionException(val position: Position) : Exception(
    "Trying to get an illegal position, position = $position"
)
