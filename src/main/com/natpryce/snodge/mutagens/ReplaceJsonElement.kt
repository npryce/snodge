package com.natpryce.snodge.mutagens

import com.google.gson.JsonElement
import com.natpryce.snodge.DocumentMutation
import com.natpryce.snodge.JsonPath
import com.natpryce.snodge.Mutagen

import kotlin.sequences.sequenceOf

class ReplaceJsonElement(private val replacement: JsonElement) : Mutagen {
    override fun potentialMutations(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement): Sequence<DocumentMutation> {
        return sequenceOf(pathToElement.map { e -> replacement })
    }
}
