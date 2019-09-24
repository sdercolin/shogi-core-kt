package com.sdercolin.shogicore

/**
 * Color for pieces and player
 */
sealed class Color {
    object Black : Color() {
        override fun next() = White
    }

    object White : Color() {
        override fun next() = Black
    }

    abstract fun next(): Color
}
