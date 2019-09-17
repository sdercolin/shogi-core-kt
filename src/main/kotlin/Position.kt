package com.sdercolin.shogicore

data class Position(val x: Int, val y: Int) {
    val isOnBoard get() = x in 0..X_MAX && y in 0..Y_MAX

    operator fun plus(point: Pair<Int, Int>): Position {
        return copy(x = x + point.first, y = y + point.second)
    }

    companion object {
        private const val X_MAX = 8
        private const val Y_MAX = 8

        val blackHand = Position(-1, 9)
        val whiteHand = Position(9, -1)

        val wholeBoard: List<Position>
            get() =
                (0..X_MAX).flatMap { x ->
                    (0..Y_MAX).map { y ->
                        Position(x, y)
                    }
                }
    }
}