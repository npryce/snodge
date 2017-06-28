package com.natpryce.jsonk

import com.natpryce.testing.projectFile
import org.junit.Assert.fail
import java.io.File

object ExampleJsonFiles {
    private val dataDir = projectFile("test-data/json")
    
    fun list() =
        dataDir.list()?.filter {it.endsWith(".json") } ?: fail("test-data/json directory not found")
    
    fun open(name: String) =
        File(dataDir, name).reader()
    
    fun load(name: String) =
        open(name).use { it.readJsonElement() }
}
