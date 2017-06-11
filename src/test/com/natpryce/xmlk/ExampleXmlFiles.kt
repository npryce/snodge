package com.natpryce.xmlk

import java.io.File
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLOutputFactory
import javax.xml.stream.XMLOutputFactory.IS_REPAIRING_NAMESPACES
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stax.StAXResult


object ExampleXmlFiles {
    private val dataDir = File("test-data/xml")
    private val inputFactory: XMLInputFactory = XMLInputFactory.newFactory()
    private val outputFactory = XMLOutputFactory.newFactory().apply { setProperty(IS_REPAIRING_NAMESPACES, true) }
    private var documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
    private val transformer = TransformerFactory.newInstance().newTransformer()
    
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
