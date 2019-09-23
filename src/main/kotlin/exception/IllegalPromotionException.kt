package com.sdercolin.shogicore.exception

import com.sdercolin.shogicore.PossibleMove

class IllegalPromotionException(val move: PossibleMove) : Exception(
    "Trying to conduct an illegal promotion, move = $move"
)