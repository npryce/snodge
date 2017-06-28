package com.natpryce.snodge.reflect

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.always

fun replaceWithTroublesomeClassName(): Mutagen<String> =
    always(troublesomeClasses())
