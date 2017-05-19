package com.natpryce.snodge

import java.nio.charset.Charset
import java.util.Random


/**
 * A Mutagen maps a value to a finite sequence of mutations of that value.
 *
 * Mutations are calculated lazily so that mutants only need be constructed
 * for a random sample of all possible mutations.
 */
typealias Mutagen<T> = (T) -> Sequence<Lazy<T>>

fun <T, U> Mutagen<U>.mapped(mapIn: (T) -> U, mapOut: (U) -> T): Mutagen<T> =
    fun(original: T) =
        this@mapped(mapIn(original)).map { lazy { mapOut(it.value) } }

fun Mutagen<String>.encodedAs(encoding: Charset): Mutagen<ByteArray> =
    mapped({ it.toString(encoding) }, { it.toByteArray(encoding) })


fun <T> Random.mutants(original: T, sampleSize: Int, mutagen: Mutagen<T>): List<T> =
    mutagen(original)
        .let { sample(sampleSize, it) }
        .map { it.value }
