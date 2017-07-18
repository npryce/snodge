package com.natpryce.xmlk

import com.natpryce.testing.loadTestData
import java.io.File
import javax.xml.stream.XMLInputFactory


object ExampleXmlFiles {
    private val dataDir = "test-data/xml"
    
    fun list() =
        listOf(
            "attributes.xml",
            "comments.xml",
            "entities.xml",
            "namespaces.xml",
            "no-xml-declaration.xml",
            "processing-instructions.xml",
            "simple-example.xml"
        )
    
    fun loadText(name: String, block: (String) -> Unit) =
        loadTestData("$dataDir/$name", block)
    
    fun load(name: String, block: (XmlDocument) -> Unit) =
        loadText(name) { it.toXmlDocument().let(block) }
}
