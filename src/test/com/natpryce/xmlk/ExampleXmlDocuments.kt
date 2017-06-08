package com.natpryce.xmlk

import java.io.File
import javax.xml.stream.XMLInputFactory

object ExampleXmlFiles {
    private val inputFactory: XMLInputFactory = XMLInputFactory.newFactory()
    
    fun list() =
        File(ExampleXmlFiles::class.java.getResource(".").path)
            .listFiles { f -> f.name.endsWith(".xml") }
            .map { it.name }
    
    fun open(name: String) =
        javaClass.getResourceAsStream(name).reader()
    
    fun load(name: String) =
        open(name).use { it.readXml(inputFactory) }
}