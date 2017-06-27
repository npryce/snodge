package com.natpryce.snodge

import kotlin.js.Math


impl open class Random {
    impl open fun nextDouble() =
        Math.random()
    
    impl open fun nextInt(bound: Int): Int =
        if (bound > 0)
            (nextDouble() * bound).toInt()
        else
            throw IllegalArgumentException("bound must be > 0")
}
