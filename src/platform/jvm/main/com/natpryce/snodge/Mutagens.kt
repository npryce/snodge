package com.natpryce.snodge


/**
 * Returns a Mutagen that ignores the original value and generates a fixed sequence of values.
 */
fun <T> always(vararg replacements: T): Mutagen<T> =
    always(replacements.asIterable())

/**
 * Returns a Mutagen that ignores the original value and generates a fixed sequence of values.
 */
fun <T> always(replacements: Iterable<T>): Mutagen<T> =
    fun(_: Random, _: T) =
        replacements.asSequence().map { lazyOf(it) }

/**
 * Applies a Mutagen multiple times to the original value.
 *
 * If the Mutagen is deterministic, then this is a pointless operation.  If the Mutagen is randomised, then this
 * operation will produce multiple (probably) different mutants.
 */
fun <T> repeat(n: Int, mutagen: Mutagen<T>): Mutagen<T> =
    fun (random: Random, original: T) =
        (1..n).asSequence().flatMap { mutagen(random, original) }

/**
 * Returns a mutagen that generates one mutant by applying a function to the original
 */
fun <T> map(f: (T)->T): Mutagen<T> =
    fun (_: Random, original: T) =
        sequenceOf(lazy { f(original) })
