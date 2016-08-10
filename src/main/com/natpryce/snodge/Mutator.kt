package com.natpryce.snodge

import com.google.gson.JsonElement
import com.natpryce.snodge.internal.EncodedStringMutator
import java.nio.charset.Charset

interface Mutator<T> {
    fun mutate(original: T, mutationCount: Int): List<T>
    
    companion object {
        fun <T> id() = IdentityMutator<T>()
    }
}

fun Mutator<String>.encodedAs(encoding: Charset) =
    EncodedStringMutator(encoding, this)

fun Mutator<JsonElement>.forEncodedStrings(encodingName: String) =
    this.forEncodedStrings(Charset.forName(encodingName))
