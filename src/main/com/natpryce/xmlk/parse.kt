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
    
    nextTag()
    val rootElement = readElement()
    
    next()
    require(END_DOCUMENT, null, null)
    
    return XmlDocument(rootElement)
}

private fun XMLStreamReader.readElement(): XmlElement {
    val attributes = (1..attributeCount)
        .map {i -> getAttributeName(i) to getAttributeValue(i) }
        .toMap()
    
    val name = name
    val children = mutableListOf<XmlNode>()
    while (next() != END_ELEMENT) {
        when (eventType) {
            START_ELEMENT -> children += readElement()
            CHARACTERS -> children += XmlText(text)
            CDATA -> children += XmlCData(text)
        }
    }
    
    return XmlElement(name, attributes, children)
}

private inline fun <reified T: XMLEvent> XMLEventReader.expect() {
    val event = nextEvent()
    if (event !is T) {
        throw XMLStreamException("expected ${T::class.simpleName}")
    }
}
