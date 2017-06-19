package com.natpryce.snodge.form

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.combine
import com.natpryce.snodge.internal.mapLazy
import com.natpryce.snodge.mapped
import com.natpryce.snodge.reflect.replaceWithTroublesomeClassName
import com.natpryce.snodge.text.replaceWithPossiblyMeaningfulText
import java.nio.charset.Charset
import java.util.Random

fun Mutagen<Form>.forStrings(charset: Charset = Charsets.UTF_8): Mutagen<String> =
    mapped({ parseForm(it, charset) }, { it.toXWwwFormUrlencoded(charset) })

fun removeSingleFieldValue(): Mutagen<Form> =
    fun(_: Random, original: Form) =
        original.asSequence().map { field -> lazy { original - field } }

fun removeField(): Mutagen<Form> =
    fun(_: Random, original: Form) =
        original.keys.asSequence().map { key -> lazy { original.filter { it.first != key } } }

fun mutateValue(valueMutagen: Mutagen<String>): Mutagen<Form> =
    fun (random: Random, original: Form) =
        original.asSequence().flatMap { (key, value) ->
            valueMutagen(random, value).mapLazy{ newValue ->
                original.map { if (it == (key to value)) key to newValue else it }
            }
        }

fun defaultFormMutagens() = combine(
    removeSingleFieldValue(),
    removeField(),
    mutateValue(replaceWithPossiblyMeaningfulText()),
    mutateValue(replaceWithTroublesomeClassName()))
