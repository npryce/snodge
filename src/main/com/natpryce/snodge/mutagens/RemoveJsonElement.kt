package com.natpryce.snodge.mutagens

import com.google.gson.JsonElement
import com.natpryce.snodge.DocumentMutation
import com.natpryce.snodge.JsonPath
import com.natpryce.snodge.Mutagen

class RemoveJsonElement : Mutagen {
    override fun potentialMutations(document: JsonElement, pathToElement: JsonPath, elementToMutate: JsonElement): Sequence<DocumentMutation> {
        if (pathToElement.isRoot) {
            return emptySequence()
        }
        else {
            return sequenceOf(pathToElement.remove())
        }
    }
}
