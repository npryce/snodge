package com.natpryce.testing

import java.io.File


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
