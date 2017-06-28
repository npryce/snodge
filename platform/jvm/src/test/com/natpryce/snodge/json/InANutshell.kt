package com.natpryce.snodge.json

import com.natpryce.snodge.Random
import com.natpryce.snodge.mutants

fun main(args: Array<String>) {
    val random = Random()
    val originalJson = "{\"x\": \"hello\", \"y\": [1,2,3]}"
    
    random.mutants(defaultJsonMutagens().forStrings(), 10, originalJson)
        .forEach { println(it) }
}
