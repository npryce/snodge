package com.natpryce.xmlk

import java.io.File
import javax.xml.stream.XMLInputFactory

object ExampleXmlFiles {
    private val dataDir = File("test-data/xml")
    private val inputFactory: XMLInputFactory = XMLInputFactory.newFactory()
    
    fun list() =
        dataDir.list().filter { it.endsWith(".xml") }
    
    fun open(name: String) =
        File(dataDir, name).reader()
    
    fun load(name: String) =
        open(name).use { it.readXml(inputFactory) }
}
