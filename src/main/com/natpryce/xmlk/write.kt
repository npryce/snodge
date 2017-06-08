package com.natpryce.xmlk

import java.io.StringWriter
import java.io.Writer
import javax.xml.stream.XMLOutputFactory
import javax.xml.stream.XMLStreamWriter


fun XmlDocument.toXmlString() = StringWriter().also { it.writeXml(this) }.toString()

fun Writer.writeXml(xmlDocument: XmlDocument, outputFactory: XMLOutputFactory = XMLOutputFactory.newFactory()) {
    outputFactory.createXMLStreamWriter(this).writeXml(xmlDocument)
}

private fun XMLStreamWriter.writeXml(doc: XmlDocument) {
    doc.xml?.let { writeStartDocument(it.encoding, it.version) }
    writeChildren(doc)
    writeEndDocument()
}

fun XMLStreamWriter.writeXmlNode(n: XmlNode) {
    when (n) {
        is XmlElement -> writeElement(n)
        is XmlText -> writeCharacters(n.text)
        is XmlCData -> writeCData(n.text)
    }.let{}
}

private fun XMLStreamWriter.writeElement(n: XmlElement) {
    writeStartElement(n.name.prefix, n.name.localPart, n.name.namespaceURI)
    n.attributes.forEach { name, value ->
        writeAttribute(name.prefix, name.namespaceURI, name.localPart, value)
    }
    writeChildren(n)
    writeEndElement()
}

private fun XMLStreamWriter.writeChildren(parent: HasChildren) {
    parent.children.forEach { writeXmlNode(it) }
}
