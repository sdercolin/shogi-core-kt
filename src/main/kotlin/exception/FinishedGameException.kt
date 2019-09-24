package com.sdercolin.shogicore.exception

import com.sdercolin.shogicore.Scene

class FinishedGameException(val finalScene: Scene) : Exception(
    "Trying to continue a finished game, finalScene = $finalScene"
)
