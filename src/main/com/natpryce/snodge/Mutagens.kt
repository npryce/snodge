package com.natpryce.snodge

import java.util.Random


fun <T> always(vararg replacements: T): Mutagen<T> =
    fun(_: Random, _: T) =
        replacements.asSequence().map { lazyOf(it) }
