package com.natpryce.xmlk

import java.io.Reader
import javax.xml.namespace.QName
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamConstants.CDATA
import javax.xml.stream.XMLStreamConstants.CHARACTERS
import javax.xml.stream.XMLStreamConstants.COMMENT
import javax.xml.stream.XMLStreamConstants.END_DOCUMENT
import javax.xml.stream.XMLStreamConstants.END_ELEMENT
import javax.xml.stream.XMLStreamConstants.PROCESSING_INSTRUCTION
import javax.xml.stream.XMLStreamConstants.START_DOCUMENT
import javax.xml.stream.XMLStreamConstants.START_ELEMENT
import javax.xml.stream.XMLStreamReader

fun String.toXmlDocument() = reader().use { it.readXml() }

fun Reader.readXml(inputFactory: XMLInputFactory = XMLInputFactory.newFactory()): XmlDocument =
    inputFactory.createXMLStreamReader(this).readXml()


fun XMLStreamReader.readXml(): XmlDocument {
    require(START_DOCUMENT, null, null)
    
    val xml = version?.let { XmlDeclaration(version, characterEncodingScheme) }
    val children = readChildrenUntil(END_DOCUMENT)
    
    return XmlDocument(xml, children)
}

private fun XMLStreamReader.readChildrenUntil(end: Int): List<XmlNode> {
    val children = mutableListOf<XmlNode>()
    
    while (next() != end) {
        when (eventType) {
            START_ELEMENT -> children += readElement()
            CHARACTERS -> children += XmlText(text, asCData = false)
            CDATA -> children += XmlText(text, asCData = true)
            PROCESSING_INSTRUCTION -> children += XmlProcessingInstruction(piTarget, piData)
            COMMENT -> children += XmlComment(text)
        }
    }
    
    return children.toList()
}

private fun XMLStreamReader.readElement(): XmlElement {
    val name = name
    val attributes = (0 until attributeCount)
        .map {i ->
            val attributeName: QName = getAttributeName(i)
            val attributeValue: String = getAttributeValue(i)
            
            attributeName to attributeValue
        }
        .toMap()
    
    val children = readChildrenUntil(END_ELEMENT)
    
    return XmlElement(name, attributes, children)
}
