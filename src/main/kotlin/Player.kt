package com.sdercolin.shogicore

/**
 * The class defining a participant of a game
 */
class Player(val color: Color) {
    val isFirst get() = color == Color.Black
}
