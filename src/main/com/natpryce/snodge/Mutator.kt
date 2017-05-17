package com.natpryce.snodge

import java.nio.charset.Charset


typealias Mutator<T> = (original: T) -> List<T>

fun <T> id() = fun(original: T) = listOf(original)

fun <T, U> Mutator<U>.mapped(toFn: (T) -> U, fromFn: (U) -> T): Mutator<T> =
    fun(original: T) = this(toFn(original)).map { fromFn(it) }

fun Mutator<String>.encodedAs(encoding: Charset): Mutator<ByteArray> =
    mapped({ it.toString(encoding) }, { it.toByteArray(encoding) })
