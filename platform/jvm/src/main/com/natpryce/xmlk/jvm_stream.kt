package com.natpryce.xmlk

import java.io.Reader
import java.io.StringWriter
import java.io.Writer
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLOutputFactory
import javax.xml.stream.XMLStreamConstants.CDATA
import javax.xml.stream.XMLStreamConstants.CHARACTERS
import javax.xml.stream.XMLStreamConstants.COMMENT
import javax.xml.stream.XMLStreamConstants.END_DOCUMENT
import javax.xml.stream.XMLStreamConstants.END_ELEMENT
import javax.xml.stream.XMLStreamConstants.PROCESSING_INSTRUCTION
import javax.xml.stream.XMLStreamConstants.START_DOCUMENT
import javax.xml.stream.XMLStreamConstants.START_ELEMENT
import javax.xml.stream.XMLStreamReader
import javax.xml.stream.XMLStreamWriter

fun defaultInputFactory(): XMLInputFactory =
    XMLInputFactory.newFactory().apply {
        setProperty(XMLInputFactory.IS_COALESCING, true)
    }

fun defaultOutputFactory(): XMLOutputFactory =
    XMLOutputFactory.newFactory().apply {
        setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true)
    }


fun String.toXmlDocument(): XmlDocument =
    reader().use { it.readXml() }

fun XmlDocument.toXmlString(): String =
    StringWriter().also { it.writeXml(this) }.toString()


fun Reader.readXml(inputFactory: XMLInputFactory = defaultInputFactory()): XmlDocument =
    inputFactory.createXMLStreamReader(this).readXml()


fun XMLStreamReader.readXml(): XmlDocument {
    require(START_DOCUMENT, null, null)
    return XmlDocument(readChildrenUntil(END_DOCUMENT))
}

private fun XMLStreamReader.readChildrenUntil(end: Int): List<XmlNode> {
    val children = mutableListOf<XmlNode>()
    
    while (next() != end) {
        when (eventType) {
            START_ELEMENT -> children += readElement()
            CHARACTERS -> children += XmlText(text, asCData = false)
            CDATA -> children += XmlText(text, asCData = true)
            PROCESSING_INSTRUCTION -> children += XmlProcessingInstruction(piTarget, piData.takeIf { it.isNotEmpty() })
            COMMENT -> children += XmlComment(text)
        }
    }
    
    return children.toList()
}

private fun XMLStreamReader.readElement(): XmlElement {
    val attributes = (0 until attributeCount)
        .map { attributeQName(it) to getAttributeValue(it) }
        .toMap()
    
    val children = readChildrenUntil(END_ELEMENT)
    
    return XmlElement(elementQName(), attributes, children)
}

private fun XMLStreamReader.elementQName() =
    QName(
        localPart = localName,
        namespaceURI = namespaceURI?.takeUnless { it.isEmpty() },
        prefix = prefix?.takeUnless { it.isEmpty() })

private fun XMLStreamReader.attributeQName(i: Int) =
    QName(
        localPart = getAttributeLocalName(i),
        namespaceURI = getAttributeNamespace(i)?.takeUnless { it.isEmpty() },
        prefix = getAttributePrefix(i)?.takeUnless { it.isEmpty() })


fun Writer.writeXml(xmlDocument: XmlDocument, outputFactory: XMLOutputFactory = defaultOutputFactory()) =
    outputFactory.createXMLStreamWriter(this).writeXml(xmlDocument)

private fun XMLStreamWriter.writeXml(doc: XmlDocument) {
    writeStartDocument("UTF-8", "1.0")
    writeChildren(doc)
    writeEndDocument()
}

fun XMLStreamWriter.writeXmlNode(n: XmlNode) {
    when (n) {
        is XmlElement ->
            writeElement(n)
        is XmlText ->
            if (n.asCData) writeCData(n.text) else writeCharacters(n.text)
        is XmlProcessingInstruction ->
            writeProcessingInstruction(n.target, n.data ?: "")
        is XmlComment ->
            writeComment(n.text)
    }.let { /* check exhaustiveness */ }
}

private fun XMLStreamWriter.writeElement(n: XmlElement) {
    writeStartElement(n.name.prefix ?: "", n.name.localPart, n.name.namespaceURI ?: "")
    n.attributes.forEach { name, value ->
        writeAttribute(name.prefix ?: "", name.namespaceURI ?: "", name.localPart, value)
    }
    writeChildren(n)
    writeEndElement()
}

private fun XMLStreamWriter.writeChildren(parent: HasChildren) {
    parent.children.forEach { writeXmlNode(it) }
}
