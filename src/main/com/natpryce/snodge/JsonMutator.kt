package com.natpryce.snodge

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.natpryce.snodge.internal.EncodedStringMutator

import java.nio.charset.Charset
import java.util.ArrayList
import java.util.Random
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Stream

import com.natpryce.snodge.internal.walk
import com.natpryce.snodge.allMutagens

class JsonMutator(private val rng: Random, private val mutagens: Mutagen) : Mutator<JsonElement> {
    @JvmOverloads constructor(mutagen: Mutagen = allMutagens()) : this(Random(), mutagen) {
    }
    
    override fun mutate(original: JsonElement, mutationCount: Int) =
        mutations(original, mutationCount).map { mutation ->
            mutation(original)
        }
    
    private fun mutations(document: JsonElement, mutationCount: Int): List<DocumentMutation> {
        val selectedMutations = mutableListOf<DocumentMutation>()
        val counter = AtomicInteger(0)
        
        walk(document)
            .flatMap { path ->
                mutagens.potentialMutations(document, path, path.invoke(document))
            }
            .forEach { potentialMutation ->
                val count = counter.incrementAndGet()
                if (count <= mutationCount) {
                    selectedMutations.add(potentialMutation)
                }
                else {
                    val index = rng.nextInt(count)
                    
                    if (index < selectedMutations.size) {
                        selectedMutations[index] = potentialMutation
                    }
                }
            }
        
        return selectedMutations
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

fun Mutator<JsonElement>.forEncodedStrings(encoding: Charset): Mutator<ByteArray> {
    return EncodedStringMutator(encoding, this.forStrings())
}

fun Mutator<JsonElement>.forEncodedStrings(encodingName: String): Mutator<ByteArray> {
    return this.forEncodedStrings(Charset.forName(encodingName))
}
