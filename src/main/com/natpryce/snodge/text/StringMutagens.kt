package com.natpryce.snodge.text

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.always
import com.natpryce.snodge.splice


fun splice(s: String): Mutagen<String> = splice(always(s))

fun splice(sliceMutagen: Mutagen<String>): Mutagen<String> =
    splice(sliceMutagen, String::length, String::slice, String::replaceRange)

fun possiblyMeaningfulStrings() = listOf(
    "",
    "1",
    "-1",
    "0",
    "-0",
    Long.MAX_VALUE.toString(),
    Long.MIN_VALUE.toString(),
    "true",
    "false",
    "1.0",
    "-1.0",
    "0.0",
    "-0.0",
    Double.NaN.toString(),
    Double.POSITIVE_INFINITY.toString(),
    Double.NEGATIVE_INFINITY.toString(),
    Double.MAX_VALUE.toString(),
    Double.MIN_VALUE.toString()
)

fun replaceWithPossiblyMeaningfulText(): Mutagen<String> =
    always(possiblyMeaningfulStrings())
