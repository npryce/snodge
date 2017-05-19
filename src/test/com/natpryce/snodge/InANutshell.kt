package com.natpryce.snodge

import java.util.Random

object InANutshell {
    @JvmStatic fun main(args: Array<String>) {
        val random = Random()
        val originalJson = "{\"x\": \"hello\", \"y\": [1,2,3]}"
        
        random.mutants(originalJson, 10, allMutagens().forStrings())
            .forEach(::println)
    }
}
