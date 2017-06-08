package com.natpryce.jsonk

import java.io.File

object ExampleJsonFiles {
    fun list() =
        File(javaClass.getResource(".").path)
            .listFiles { f -> f.name.endsWith(".json") }
            .map { it.name }
    
    fun open(name: String) =
        javaClass.getResourceAsStream(name).reader()
    
    fun load(name: String) =
        open(name).use { it.readJsonElement() }
}