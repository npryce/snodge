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
    writeXmlNode(doc.root)
}

fun XMLStreamWriter.writeXmlNode(n: XmlNode) {
    when (n) {
        is XmlElement -> {
            writeStartElement(n.name.prefix, n.name.localPart, n.name.namespaceURI)
            n.attributes.forEach { name, value ->
                writeAttribute(name.prefix, name.namespaceURI, name.localPart, value)
            }
            n.children.forEach { writeXmlNode(it) }
            writeEndElement()
        }
        is XmlText -> writeCharacters(n.text)
        is XmlCData -> writeCData(n.text)
    }!!
}
