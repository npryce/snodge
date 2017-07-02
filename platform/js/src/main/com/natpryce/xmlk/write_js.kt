package com.natpryce.xmlk

import org.w3c.dom.Node
import org.w3c.dom.XMLDocument
import org.w3c.dom.parsing.XMLSerializer
import kotlin.browser.document

fun XmlDocument.toXmlString(): String = XMLSerializer().serializeToString(toDOM())

fun XmlDocument.toDOM(): XMLDocument {
    return document.implementation.createDocument("", "", null)
        .also { doc: XMLDocument ->
            children.forEach { child ->
                doc.appendChild(child.toDOM())
            }
        }
}

fun XmlNode.toDOM(): Node =
    when (this) {
        is XmlText -> if (asCData) document.createCDATASection(text) else document.createTextNode(text)
        is XmlElement -> document.createElementNS(name.namespaceURI, name.toDOM()).also { element ->
            attributes.forEach { (attrName, attrValue) ->
                element.setAttributeNS(attrName.namespaceURI, attrName.toDOM(), attrValue)
            }
            children.forEach { child ->
                element.appendChild(child.toDOM())
            }
        }
        is XmlComment -> document.createComment(text)
        is XmlProcessingInstruction -> {
            document.createProcessingInstruction(target, data ?: "")
        }
    }

private fun QName.toDOM() = (prefix?.plus(":") ?: "") + localPart
