package com.natpryce.snodge.form

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.combine
import com.natpryce.snodge.internal.mapLazy
import com.natpryce.snodge.reflect.replaceWithTroublesomeClassName
import com.natpryce.snodge.text.possiblyMeaningfulStrings
import com.natpryce.snodge.text.replaceWithPossiblyMeaningfulText
import kotlin.random.Random

fun removeSingleFieldValue(): Mutagen<Form> =
    fun(_: Random, original: Form) =
        original.asSequence().map { field -> lazy { original - field } }

fun removeField(): Mutagen<Form> =
    fun(_: Random, original: Form) =
        original.keys.asSequence().map { key -> lazy { original.filter { it.first != key } } }

fun mutateValue(valueMutagen: Mutagen<String>): Mutagen<Form> =
    fun(random: Random, original: Form) =
        original.asSequence().flatMap { (key, value) ->
            valueMutagen(random, value).mapLazy { newValue ->
                original.map { if (it == (key to value)) key to newValue else it }
            }
        }


fun addUniqueField(newValues: Iterable<String>, basename: String = "x") =
    fun(_: Random, original: Form) =
        sequenceOf(lazy {
            original + (unusedKey(basename, original).let { name -> newValues.map { value -> name to value } })
        })

private fun unusedKey(basename: String, original: Form) =
    original.keys.let { keys ->
        (sequenceOf(basename) + generateSequence(1, { it + 1 }).map { i -> "${basename}_$i" })
            .filterNot { it in keys }
            .first()
    }

fun defaultFormMutagens() = combine(
    removeSingleFieldValue(),
    removeField(),
    mutateValue(replaceWithPossiblyMeaningfulText()),
    combine(possiblyMeaningfulStrings().map { addUniqueField(listOf(it), basename = "x") }),
    mutateValue(replaceWithTroublesomeClassName()))
