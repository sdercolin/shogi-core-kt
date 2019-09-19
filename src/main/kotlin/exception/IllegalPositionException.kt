package com.sdercolin.shogicore.exception

import com.sdercolin.shogicore.Move
import com.sdercolin.shogicore.Piece
import com.sdercolin.shogicore.Position
import com.sdercolin.shogicore.PossibleMove

class IllegalPositionException(val position: Position) : Exception(
    "Trying to get an illegal position, position = $position"
)