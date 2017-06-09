package com.natpryce.snodge.internal

fun ByteArray.replaceRange(range: IntRange, inserted: ByteArray): ByteArray =
    sliceArray(0..range.first-1) + inserted + this.sliceArray(range.last+1..size-1)

fun <T, U> Sequence<Lazy<T>>.mapLazy(f: (T) -> U) = map { lazy { f(it.value) } }
