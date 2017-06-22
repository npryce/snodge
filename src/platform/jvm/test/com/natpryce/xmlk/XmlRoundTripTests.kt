package com.natpryce.xmlk

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import org.xml.sax.InputSource
import java.io.StringReader
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.stream.XMLOutputFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stax.StAXResult


@RunWith(Parameterized::class)
class XmlRoundTripTests(val exampleName: String) {
    @Test
    fun `round trips XML`() {
        val originalXml = ExampleXmlFiles.loadText(exampleName)
        val original = originalXml.toXmlDocument()
        val roundTrippedXml = original.toXmlString()
        
        assertEquals(normaliseXmlText(originalXml), normaliseXmlText(roundTrippedXml))
    }
    
    companion object {
        @Parameters(name = "{0}") @JvmStatic
        fun examples() = ExampleXmlFiles.list()
    
        private val outputFactory = XMLOutputFactory.newFactory().apply { setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true) }
        private var documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        private val transformer = TransformerFactory.newInstance().newTransformer()
    
        fun normaliseXmlText(xmlText: String) =
            documentBuilder.parse(InputSource(StringReader(xmlText)))
                .let { doc ->
                    doc.normalize()
                
                    val writer = StringWriter()
                    transformer.transform(
                        DOMSource(doc),
                        StAXResult(outputFactory.createXMLStreamWriter(writer)))
                    writer.toString()
                }
    }
}
