package com.natpryce.jsonk

import com.natpryce.testing.loadTestData

object ExampleJsonFiles {
    private val dataDir = "test-data/json"
    
    fun list() =
        listOf(
            "array-example.json",
            "object-example.json"
        )
    
    fun <T> load(name: String, block: (JsonElement) -> T) =
        loadTestData(dataDir + "/" + name) {it.toJsonElement().let(block)}
}
