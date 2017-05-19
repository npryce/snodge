package com.natpryce.snodge.json

import com.natpryce.snodge.mutants
import java.util.Random

object InANutshell {
    @JvmStatic fun main(args: Array<String>) {
        val random = Random()
        val originalJson = "{\"x\": \"hello\", \"y\": [1,2,3]}"
        
        random.mutants(allJsonMutagens().forStrings(), 10, originalJson)
            .forEach(::println)
    }
}
