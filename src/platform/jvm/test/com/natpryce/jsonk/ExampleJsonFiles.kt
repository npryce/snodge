package com.natpryce.jsonk

import java.io.File

object ExampleJsonFiles {
    private val dataDir = File("test-data/json")
    
    fun list() =
        dataDir.list().filter {it.endsWith(".json") }
    
    fun open(name: String) =
        File(dataDir, name).reader()
    
    fun load(name: String) =
        open(name).use { it.readJsonElement() }
}
