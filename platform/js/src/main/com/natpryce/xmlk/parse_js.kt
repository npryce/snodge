package com.natpryce.xmlk

import org.w3c.dom.CDATASection
import org.w3c.dom.Comment
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.w3c.dom.ProcessingInstruction
import org.w3c.dom.Text
import org.w3c.dom.get
import org.w3c.dom.parsing.DOMParser


fun String.toXmlDocument(): XmlDocument =
    DOMParser().parseFromString(this, "application/xml").toXmlDocument()

fun Document.toXmlDocument(): XmlDocument =
    XmlDocument(null, childNodes.map {node -> node.toXmlNode()})

private fun Node.toXmlNode(): XmlNode =
    when(this) {
        is Element -> this.toXmlElement()
        is Text -> XmlText(wholeText, asCData = false)
        is CDATASection -> XmlText(wholeText, asCData = true)
        is ProcessingInstruction -> XmlProcessingInstruction(target, data.takeIf { it.isNotEmpty() })
        is Comment -> XmlComment(textContent ?: "")
        else -> throw IllegalArgumentException("cannot convert ${this::class.simpleName} to XmlNode")
    }

private fun Element.toXmlElement(): XmlElement {
    return XmlElement(
        name = QName(localName, namespaceURI, prefix),
        attributes = emptyMap(), // TODO parse attributes
        children = childNodes.map { it.toXmlNode() })
}

private fun NodeList.map(f: (Node)->XmlNode): List<XmlNode> =
    (0 until length).map { f(get(it)!!) }
