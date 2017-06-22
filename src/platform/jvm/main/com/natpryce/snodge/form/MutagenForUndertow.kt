package com.natpryce.snodge.form

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.mapped
import io.undertow.server.handlers.form.FormData


fun Mutagen<Form>.forUndertow() =
    mapped(FormData::toForm, Form::toFormData)

fun FormData.toForm() =
    this.flatMap { key -> this[key].map { key to it.value } }

fun Form.toFormData() =
    FormData(Int.MAX_VALUE).also {
        forEach { (key, value) -> it.add(key, value) }
    }
