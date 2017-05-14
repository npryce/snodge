package com.natpryce.snodge

object InANutshell {
    @JvmStatic fun main(args: Array<String>) {
        val mutator = JsonMutator()
        val originalJson = "{\"x\": \"hello\", \"y\": [1,2,3]}"
        
        mutator.forStrings().invoke(originalJson, 10).forEach { println(it) }
    }
}
