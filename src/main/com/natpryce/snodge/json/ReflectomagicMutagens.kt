package com.natpryce.snodge.json

import com.google.gson.JsonPrimitive
import com.natpryce.snodge.reflect.troublesomeClasses

fun securityMutagens() =
    troublesomeClasses()
        .map { replaceJsonElement(JsonPrimitive(it)).ifElement { it is JsonPrimitive && it.isString} }
        .let { combine(it) }
