package com.natpryce.xmlk

import java.io.Reader
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamConstants.*
import javax.xml.stream.XMLStreamException
import javax.xml.stream.XMLStreamReader
import javax.xml.stream.events.XMLEvent

fun String.toXmlDocument() = reader().use { it.readXml() }

fun Reader.readXml(inputFactory: XMLInputFactory = XMLInputFactory.newFactory()): XmlDocument =
    inputFactory.createXMLStreamReader(this).readXml()


fun XMLStreamReader.readXml(): XmlDocument {
    require(START_DOCUMENT, null, null)
    
    val xml = version?.let { XmlDeclaration(version, characterEncodingScheme) }
    val children = readChildrenUntil(END_DOCUMENT)
    
    return XmlDocument(xml, children)
}

private fun XMLStreamReader.readElement(): XmlElement {
    val attributes = (1..attributeCount)
        .map {i -> getAttributeName(i) to getAttributeValue(i) }
        .toMap()
    
    val name = name
    val children = readChildrenUntil(END_ELEMENT)
    
    return XmlElement(name, attributes, children)
}

private fun XMLStreamReader.readChildrenUntil(end: Int): List<XmlNode> {
    val children = mutableListOf<XmlNode>()
    
    while (next() != end) {
        when (eventType) {
            START_ELEMENT -> children += readElement()
            CHARACTERS -> children += XmlText(text)
            CDATA -> children += XmlCData(text)
        }
    }
    
    return children.toList()
}
