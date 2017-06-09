package com.natpryce.xmlk

import java.io.File
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLOutputFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stax.StAXResult
import javax.xml.transform.stream.StreamResult


object ExampleXmlFiles {
    private val dataDir = File("test-data/xml")
    private val inputFactory: XMLInputFactory = XMLInputFactory.newFactory()
    private val outputFactory = XMLOutputFactory.newFactory()
    private var documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
    private val transformer = TransformerFactory.newInstance().newTransformer()
    
    fun list() =
        dataDir.list().filter { it.endsWith(".xml") }
    
    fun loadText(name: String) =
        documentBuilder.parse(file(name))
            .let { doc ->
                doc.documentElement.normalize()
                
                val text = StringWriter()
                transformer.transform(
                    DOMSource(doc),
                    StAXResult(outputFactory.createXMLStreamWriter(text)))
                text.toString()
            }
    
    fun open(name: String) =
        loadText(name).reader()
    
    fun openUnnormalised(name: String) =
        file(name).reader()
    
    fun load(name: String) =
        openUnnormalised(name).use { it.readXml(inputFactory) }
    
    fun file(name: String) =
        File(dataDir, name)
}
