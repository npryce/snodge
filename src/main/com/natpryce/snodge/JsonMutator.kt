package com.natpryce.snodge

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.natpryce.snodge.internal.walk
import java.nio.charset.Charset
import java.util.*

class JsonMutator(
    private val mutationCount: Int,
    private val random: Random = Random(),
    private val mutagens: Mutagen = allMutagens()

) : Mutator<JsonElement> {
    
    override fun invoke(original: JsonElement) =
        random.sample(mutationCount, original.walk().flatMap { path ->
            mutagens.potentialMutations(original, path, path(original))
        }).map { it(original) }
}

fun Mutator<JsonElement>.forStrings(): Mutator<String> {
    val gson = Gson()
    return mapped({ gson.fromJson(it, JsonElement::class.java) }, { it.toString() })
}

fun Mutator<JsonElement>.forEncodedStrings(encoding: Charset) =
    this.forStrings().encodedAs(encoding)

fun Mutator<JsonElement>.forEncodedStrings(encodingName: String) =
    this.forEncodedStrings(Charset.forName(encodingName))


fun Random.mutateJson(doc: JsonElement, count: Int, mutagen: Mutagen = allMutagens()): List<JsonElement> {
    return JsonMutator(count, this, mutagen).invoke(doc)
}

fun Random.mutateJson(doc: String, count: Int, mutagen: Mutagen = allMutagens()): List<String> {
    return JsonMutator(count, this, mutagen).forStrings().invoke(doc)
}

fun Random.mutateEncodedJson(doc: ByteArray, encoding: Charset, count: Int, mutagen: Mutagen = allMutagens()): List<ByteArray> {
    return JsonMutator(count, this, mutagen).forEncodedStrings(encoding).invoke(doc)
}
