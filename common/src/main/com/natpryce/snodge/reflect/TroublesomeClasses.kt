package com.natpryce.snodge.reflect

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.always

header fun troublesomeClasses(): List<String>

fun replaceWithTroublesomeClassName(): Mutagen<String> =
    always(troublesomeClasses())
