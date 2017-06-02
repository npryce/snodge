package com.natpryce.snodge

import java.util.Random


fun <T> always(vararg replacements: T): Mutagen<T> =
    fun(_: Random, _: T) =
        replacements.asSequence().map { lazyOf(it) }


fun <T> repeat(n: Int, mutagen: Mutagen<T>): Mutagen<T> =
    fun (random: Random, original: T) =
        (1..n).asSequence().flatMap { mutagen(random, original) }
