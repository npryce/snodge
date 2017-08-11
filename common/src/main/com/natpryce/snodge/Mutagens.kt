package com.natpryce.snodge

import com.natpryce.snodge.internal.mapLazy


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

/**
 * Creates a mutagen of a recursive data structure, by applying the given mutagen to every applicable node within the structure.
 */
inline fun <ROOT, NODE, reified T : NODE> recurse(
    crossinline walk: (ROOT) -> Sequence<Pair<NODE, (NODE) -> ROOT>>,
    crossinline nodeMutagen: Mutagen<T>): Mutagen<ROOT> =
    { random: Random, original: ROOT ->
        walk(original)
            .flatMap { (node, replaceInDocument) ->
                when (node) {
                    is T -> nodeMutagen(random, node).mapLazy(replaceInDocument)
                    else -> emptySequence()
                }
            }
    }
