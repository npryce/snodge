package com.natpryce.snodge.reflect

import kotlin.reflect.jvm.jvmName

class CodeExecutionVulnerability(message: String) : Error(message)

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

impl fun troublesomeClasses() =
    listOf(ClassCannotBeLoaded::class, ClassCannotBeInstantiated::class, PrivateClass::class)
        .map { it.jvmName }


