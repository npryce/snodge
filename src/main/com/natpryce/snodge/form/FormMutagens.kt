package com.natpryce.snodge.form

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.combine
import com.natpryce.snodge.mapped
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


fun defaultFormMutagens() = combine(
    removeSingleFieldValue(),
    removeField())
