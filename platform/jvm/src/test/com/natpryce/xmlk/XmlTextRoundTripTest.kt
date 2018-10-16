package com.natpryce.xmlk

import kotlin.test.Test
import org.xml.sax.InputSource
import java.io.StringReader
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.stream.XMLOutputFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stax.StAXResult
import kotlin.test.assertEquals


class XmlTextRoundTripTest {
    @Test
    fun `round_trips_XML`() {
        ExampleXmlFiles.forEachText { exampleName, originalXml ->
            val original = originalXml.toXmlDocument()
            val roundTrippedXml = original.toXmlString()
            
            assertEquals(normaliseXmlText(xmlText = originalXml), normaliseXmlText(roundTrippedXml), message=exampleName)
        }
    }
    
    companion object {
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
