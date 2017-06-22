package com.natpryce.snodge.xml

import com.natpryce.snodge.mutants
import com.natpryce.xmlk.ExampleXmlFiles
import com.natpryce.xmlk.toXmlString
import java.util.Random

fun main(args: Array<String>) {
    val random = Random()
    val originalXml = ExampleXmlFiles.load("simple-example.xml")
    
    random.mutants(defaultXmlMutagens(), 10, originalXml)
        .forEach {
            println(it.toXmlString())
            println()
        }
}
