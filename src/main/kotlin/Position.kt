package com.sdercolin.shogicore

data class Position(val x: Int, val y: Int) {
    val isOnBoard get() = x in 0..X_MAX && y in 0..Y_MAX

    operator fun plus(point: Pair<Int, Int>): Position {
        return copy(x = x + point.first, y = y + point.second)
    }

    companion object {
        private const val X_MAX = 8
        private const val Y_MAX = 8
        private const val WIDTH_PROMOTABLE_ZONE = 3

        fun getHandPosition(color: Color): Position {
            return when (color) {
                Color.BLACK -> Position(-1, 9)
                Color.WHITE -> Position(9, -1)
            }
        }

        val wholeBoard: List<Position>
            get() =
                (0..X_MAX).flatMap { x ->
                    (0..Y_MAX).map { y ->
                        Position(x, y)
                    }
                }

        val all: List<Position>
            get() = wholeBoard + getHandPosition(Color.BLACK) + getHandPosition(Color.WHITE)

        fun getPromotableZone(color: Color): List<Position> {
            return when (color) {
                Color.BLACK ->
                    (0..X_MAX).flatMap { x ->
                        (0 until WIDTH_PROMOTABLE_ZONE).map { y ->
                            Position(x, y)
                        }
                    }
                Color.WHITE ->
                    (0..X_MAX).flatMap { x ->
                        (X_MAX - WIDTH_PROMOTABLE_ZONE + 1..X_MAX).map { y ->
                            Position(x, y)
                        }
                    }
            }
        }
    }
}