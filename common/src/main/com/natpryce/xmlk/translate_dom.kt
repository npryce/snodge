package com.natpryce.xmlk

import org.w3c.dom.Attr
import org.w3c.dom.CDATASection
import org.w3c.dom.Comment
import org.w3c.dom.DOMImplementation
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.w3c.dom.ProcessingInstruction
import org.w3c.dom.Text


fun Document.toXmlDocument(): XmlDocument =
    XmlDocument(childNodes.map { node -> node.toXmlNode()})

private fun Node.toXmlNode(): XmlNode =
    when(this) {
        is Element -> this.toXmlElement()
        is Text -> XmlText(data, asCData = false)
        is CDATASection -> XmlText(data, asCData = true)
        is ProcessingInstruction -> this.toXmlProcessingInstruction()
        is Comment -> XmlComment(textContent ?: "")
        else -> throw IllegalArgumentException("cannot convert ${this::class.simpleName} to XmlNode")
    }

@Suppress("UNNECESSARY_SAFE_CALL") // Required for JVM platform but not JS platform
private fun ProcessingInstruction.toXmlProcessingInstruction() =
    XmlProcessingInstruction(target, data?.takeIf { it.isNotEmpty() })

private fun Element.toXmlElement(): XmlElement {
    return XmlElement(
        name = QName(localName, namespaceURI, prefix),
        attributes = (0 until attributes.length)
            .map { i -> attributes.item(i) as Attr }
            .associate{ QName(it.localName, it.namespaceURI, it.prefix) to it.value },
        children = childNodes.map { it.toXmlNode() })
}

private fun NodeList.map(f: (Node)->XmlNode): List<XmlNode> =
    (0 until length).map { f(this.item(it)!!) }

fun XmlDocument.toDOM(implementation: DOMImplementation = defaultDOMImplementation()): Document {
    return implementation.createEmptyDocument()
        .also { doc: Document ->
            children.forEach { child ->
                doc.appendChild(child.toDOM(doc))
            }
        }
}

private fun XmlNode.toDOM(doc: Document): Node =
    when (this) {
        is XmlText -> if (asCData) doc.createCDATASection(text) else doc.createTextNode(text)
        is XmlElement -> doc.createElementNS(name.namespaceURI, name.toDOM()).also { element ->
            attributes.forEach { (attrName, attrValue) ->
                element.setAttributeNS(attrName.namespaceURI, attrName.toDOM(), attrValue)
            }
            children.forEach { child ->
                element.appendChild(child.toDOM(doc))
            }
        }
        is XmlComment -> doc.createComment(text)
        is XmlProcessingInstruction -> {
            doc.createProcessingInstruction(target, data ?: "")
        }
    }

private fun QName.toDOM() = (prefix?.plus(":") ?: "") + localPart
