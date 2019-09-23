package com.sdercolin.shogicore

internal infix fun Int.towards(target: Int): List<Int> {
    return when {
        this > target -> (target..this).toList().reversed()
        this < target -> (this..target).toList()
        else -> listOf()
    }
}
