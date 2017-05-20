package com.natpryce.snodge.json

import com.google.gson.JsonPrimitive
import kotlin.reflect.jvm.jvmName

class CodeExecutionVulnerability(message: String): Error(message)

class ClassCannotBeLoaded {
    companion object {
        init {
            throw CodeExecutionVulnerability("tried to load an arbitrary class")
        }
    }
}

class ClassCannotBeInstantiated {
    init {
        throw CodeExecutionVulnerability("tried to instantiate an arbitrary class")
    }
}

private class PrivateClass {
}


fun securityMutagens() =
    listOf(
        ClassCannotBeLoaded::class,
        ClassCannotBeInstantiated::class,
        PrivateClass::class
    ).map { c ->
        replaceJsonElement(JsonPrimitive(c.jvmName))
    }.let {
        combine(it)
    }
