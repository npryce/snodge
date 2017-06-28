package com.natpryce.xmlk

import com.natpryce.testing.projectFile
import java.io.File
import javax.xml.stream.XMLInputFactory


object ExampleXmlFiles {
    private val dataDir = projectFile("test-data/xml")
    private val inputFactory: XMLInputFactory = XMLInputFactory.newFactory()
    
    fun list() =
        dataDir.list().filter { it.endsWith(".xml") }
    
    fun loadText(name: String) =
        file(name).readText()
    
    fun load(name: String) =
        openUnnormalised(name).use { it.readXml(inputFactory) }
    
    fun openUnnormalised(name: String) =
        file(name).reader()
    
    fun file(name: String) =
        File(dataDir, name)
}
