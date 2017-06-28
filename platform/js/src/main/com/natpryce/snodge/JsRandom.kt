package com.natpryce.snodge

import kotlin.js.Math


class Random {
    fun nextDouble() =
        Math.random()
    
    fun nextInt(bound: Int): Int =
        if (bound > 0)
            (nextDouble() * bound).toInt()
        else
            throw IllegalArgumentException("bound must be > 0")
}
