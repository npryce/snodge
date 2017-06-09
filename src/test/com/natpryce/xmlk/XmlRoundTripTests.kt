package com.natpryce.xmlk

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import java.io.StringReader
import java.io.StringWriter
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLOutputFactory
import javax.xml.stream.XMLStreamWriter
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stax.StAXResult
import javax.xml.transform.stax.StAXSource


@RunWith(Parameterized::class)
class XmlRoundTripTests(val exampleName: String) {
    @Test
    fun `round trips XML`() {
        val originalXml = ExampleXmlFiles.loadText(exampleName)
        val roundTrippedXml = originalXml.toXmlDocument().toXmlString()
        
        assertEquals(originalXml, roundTrippedXml)
    }
    
    companion object {
        @Parameters(name = "{0}") @JvmStatic
        fun examples() = ExampleXmlFiles.list()
    }
}
