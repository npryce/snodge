package com.natpryce.snodge.json

import com.natpryce.snodge.Random

fun main(args: Array<String>) {
    val random = Random()
    val originalJson = "{\"x\": \"hello\", \"y\": [1,2,3]}"
    
    random.mutants(defaultJsonMutagens().forStrings(), 10, originalJson)
        .forEach { println(it) }
}
