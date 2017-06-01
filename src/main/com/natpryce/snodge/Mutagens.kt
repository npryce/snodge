package com.natpryce.snodge

import java.util.Random


fun <T> always(replacement: T): Mutagen<T> =
    fun(_: Random, _: T) =
        sequenceOf(lazy { replacement })

