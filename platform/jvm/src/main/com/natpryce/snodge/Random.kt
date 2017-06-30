package com.natpryce.snodge

import java.util.Collections

typealias Random = java.util.Random

fun <T> Random.shuffled(items: Iterable<T>): List<T> {
    val list = items.toMutableList()
    Collections.shuffle(list, this)
    return list.toList()
}
