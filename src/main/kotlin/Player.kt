package com.sdercolin.shogicore

class Player(val color: Color) {
    val isFirst get() = color == Color.BLACK
}