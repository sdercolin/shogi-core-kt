package com.sdercolin.shogicore.exception

import com.sdercolin.shogicore.Move
import com.sdercolin.shogicore.Piece
import com.sdercolin.shogicore.Position
import com.sdercolin.shogicore.PossibleMove

class IllegalOnBoardPositionException(val position: Position) : Exception(
    "Trying to get a single piece from hand position, position = $position"
)