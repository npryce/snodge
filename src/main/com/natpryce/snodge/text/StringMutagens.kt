package com.natpryce.snodge.text

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.always
import com.natpryce.snodge.mapLazy
import java.util.Random


fun splice(s: String): Mutagen<String> = splice(always(s))

fun splice(substringMutagen: Mutagen<String>): Mutagen<String> =
    fun(random: Random, original: String): Sequence<Lazy<String>> {
        val replacedLength = 1 + random.nextInt(original.length-1)
        val replacedStart = random.nextInt(original.length - replacedLength)
        val replacedEnd = replacedStart + replacedLength
        
        return substringMutagen(random, original.substring(replacedStart, replacedEnd)).mapLazy {
            original.replaceRange(replacedStart, replacedEnd, it)
        }
    }
