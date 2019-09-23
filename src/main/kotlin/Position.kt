package com.sdercolin.shogicore

/**
 * Representing a point the board or either of player's hand
 */
data class Position(val x: Int, val y: Int) {
    val isOnBoard get() = x in 0..X_MAX && y in 0..Y_MAX

    internal operator fun plus(point: Pair<Int, Int>): Position {
        return copy(x = x + point.first, y = y + point.second)
    }

    companion object {
        const val X_MAX = 8
        const val Y_MAX = 8
        private const val WIDTH_PROMOTABLE_ZONE = 3

        /**
         * Returns certain {@code Position} representing the hand of the given color
         */
        fun getHandPosition(color: Color): Position {
            return when (color) {
                Color.BLACK -> Position(-1, 9)
                Color.WHITE -> Position(9, -1)
            }
        }

        /**
         * A list containing all the {@code Position}s on the board
         */
        val wholeBoard: List<Position>
            get() =
                (0..X_MAX).flatMap { x ->
                    (0..Y_MAX).map { y ->
                        Position(x, y)
                    }
                }

        /**
         * A list containing all the existing {@code Position}s
         */
        val all: List<Position>
            get() = wholeBoard + getHandPosition(Color.BLACK) + getHandPosition(Color.WHITE)

        internal fun getPromotableZone(color: Color): List<Position> {
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
