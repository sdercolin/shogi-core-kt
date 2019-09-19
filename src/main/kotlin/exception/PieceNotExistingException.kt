package com.sdercolin.shogicore.exception

import com.sdercolin.shogicore.*

class PieceNotExistingException(val piece: Piece, val scene: Scene) : Exception(
    "Trying to get a piece that is not existing in the scene, piece = $piece, scene = $scene"
)