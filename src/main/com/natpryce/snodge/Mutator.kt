package com.natpryce.snodge

import com.google.gson.JsonElement
import com.natpryce.snodge.internal.EncodedStringMutator
import java.nio.charset.Charset


typealias Mutator<T> = (original: T, count: Int)->List<T>

fun <T> id() = IdentityMutator<T>()

fun Mutator<String>.encodedAs(encoding: Charset) =
    fun (original: ByteArray, mutationCount: Int) =
        this(original.toString(encoding), mutationCount).map { it.toByteArray(encoding) }

fun Mutator<JsonElement>.forEncodedStrings(encodingName: String) =
    this.forEncodedStrings(Charset.forName(encodingName))
