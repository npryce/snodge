package com.natpryce.snodge.text

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.always
import com.natpryce.snodge.splice


fun splice(s: String): Mutagen<String> = splice(always(s))

fun splice(sliceMutagen: Mutagen<String>): Mutagen<String> =
    splice(sliceMutagen, String::length, String::slice, String::replaceRange)
