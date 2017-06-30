package com.natpryce.snodge


typealias Random = MersenneTwisterFast

fun <T> Random.shuffled(list: Iterable<T>): List<T> {
    val arr = list.toList().toTypedArray()
    for (i in arr.size downTo 2) {
        arr.swap(i - 1, nextInt(i))
    }
    return arr.toList()
}

private fun <T> Array<T>.swap(i: Int, j: Int) {
    val tmp = this[i]
    this[i] = this[j]
    this[j] = tmp
}
