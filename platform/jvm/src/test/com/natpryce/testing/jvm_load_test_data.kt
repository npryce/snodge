package com.natpryce.testing

import java.io.File
import kotlin.io.readText

fun <T> loadTestData(name: String, consumer: (String)->T) =
    projectFile(name).readText().let(consumer)

fun projectFile(name: String): File =
    fileAbove(File(".").absoluteFile, name)

tailrec fun fileAbove(parent: File, name: String): File {
    val f = File(parent, name)
    if (f.exists()) {
        return f
    } else {
        return fileAbove(parent.parentFile, name)
    }
}

