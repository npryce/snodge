package com.natpryce.snodge

import com.natpryce.snodge.internal.mapLazy


fun <T> splice(
    sliceMutagen: Mutagen<T>,
    length: (T) -> Int,
    slice: (T, IntRange) -> T,
    replaceSlice: (T, IntRange, T) -> T
): Mutagen<T> =
    fun(random: Random, original: T): Sequence<Lazy<T>> {
        val replacedLength = 1 + random.nextInt(length(original) - 1)
        val replacedStart = random.nextInt(length(original) - replacedLength)
        val replacedEnd = replacedStart + replacedLength
        val replacedRange = replacedStart..replacedEnd
        
        return sliceMutagen(random, slice(original, replacedRange)).mapLazy {
            replaceSlice(original, replacedRange, it)
        }
    }
