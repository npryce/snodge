package com.natpryce.xmlk

import java.io.StringWriter
import java.io.Writer
import javax.xml.stream.XMLOutputFactory
import javax.xml.stream.XMLOutputFactory.IS_REPAIRING_NAMESPACES
import javax.xml.stream.XMLStreamWriter


fun XmlDocument.toXmlString(): String =
    StringWriter().also { it.writeXml(this) }.toString()

fun Writer.writeXml(
    xmlDocument: XmlDocument,
    outputFactory: XMLOutputFactory = XMLOutputFactory.newFactory().apply { setProperty(IS_REPAIRING_NAMESPACES, true) }
) {
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
        is XmlText -> if (n.asCData) writeCData(n.text) else writeCharacters(n.text)
        is XmlProcessingInstruction -> writeProcessingInstruction(n.target, n.data)
        is XmlComment -> writeComment(n.text)
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
