package com.natpryce.snodge

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.natpryce.snodge.internal.walk
import java.nio.charset.Charset
import java.util.*

class JsonMutator(
    private val mutagens: Mutagen = allMutagens(),
    private val rng: Random = Random()

) : Mutator<JsonElement> {
    
    override fun mutate(original: JsonElement, mutationCount: Int) =
        mutations(original, mutationCount).map { mutation -> mutation(original) }
    
    private fun mutations(document: JsonElement, mutationCount: Int): List<DocumentMutation> {
        return document.walk()
            .flatMap { path -> mutagens.potentialMutations(document, path, path(document)) }
            .sample(mutationCount, random = rng)
    }
}


fun Mutator<JsonElement>.forStrings(): Mutator<String> {
    val gson = Gson()
    
    return object : Mutator<String> {
        override fun mutate(original: String, mutationCount: Int) =
            mutate(gson.fromJson(original, JsonElement::class.java), mutationCount)
                .map { it.toString() }
    }
}

fun Mutator<JsonElement>.forEncodedStrings(encoding: Charset) =
    this.forStrings().encodedAs(encoding)

