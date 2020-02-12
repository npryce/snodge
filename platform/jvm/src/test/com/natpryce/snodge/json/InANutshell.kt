package com.natpryce.snodge.json

import com.natpryce.snodge.mutants
import kotlin.random.Random

fun main(args: Array<String>) {
    val originalJson = "{\"x\": \"hello\", \"y\": [1,2,3]}"
    
    Random.mutants(defaultJsonMutagens().forStrings(), 10, originalJson)
        .forEach { println(it) }
}
