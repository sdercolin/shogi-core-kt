package com.sdercolin.shogicore.exception

import com.sdercolin.shogicore.PossibleMove

class InvalidPromotionException(val move: PossibleMove) : Exception(
    "Trying to conduct an invalid promotion, move = $move"
)
