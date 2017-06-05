package com.natpryce.snodge

import java.util.Random


/**
 * The main entry point of the Snodge library: randomly sample a fixed number of mutants from a sequence of
 * potential mutants generated by applying a mutagen to some original value.
 */
fun <T> Random.mutants(mutagen: Mutagen<T>, sampleSize: Int, original: T): List<T> =
    mutagen(this, original)
        .let { sample(sampleSize, it) }
        .map { it.value }

fun <T> Random.mutant(mutagen: Mutagen<T>, original: T): T =
    mutants(mutagen, 1, original).first()
