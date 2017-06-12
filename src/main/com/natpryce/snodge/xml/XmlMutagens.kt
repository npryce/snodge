package com.natpryce.snodge.xml

import com.natpryce.snodge.combine
import com.natpryce.xmlk.XmlElement
import com.natpryce.xmlk.XmlNode
import com.natpryce.xmlk.XmlText
import com.natpryce.xmlk.minusAttribute
import com.natpryce.xmlk.minusChild
import com.natpryce.xmlk.replaceChild
import com.natpryce.xmlk.withAttribute
import javax.xml.namespace.QName


fun removeAttribute() = XmlMutagen<XmlElement> { _, element ->
    element.attributes.keys.asSequence().map { lazy { element.minusAttribute(it) } }
}

fun removeElement() = XmlMutagen<XmlElement> { _, element ->
    element.children.indices.map { lazy { element.minusChild(it) } }.asSequence()
}

fun removeNamespace() = XmlMutagen<XmlElement> { _, element ->
    sequenceOf(lazy { element.copy(name = element.name.withoutNamespace()) }) +
        element.attributes.asSequence()
            .filter {(name, _) -> name.namespaceURI != null}
            .map { (name, value) -> lazy { element.minusAttribute(name).withAttribute(name.withoutNamespace() to value) }
        }
}

fun replaceNode(replacement: XmlNode) = XmlMutagen<XmlElement> { _, element ->
    element.children.indices.map { lazy { element.replaceChild(it, replacement) } }.asSequence()
}

private fun QName.withoutNamespace() = QName(localPart)

fun defaultXmlMutagens() = combine(
    removeAttribute(),
    removeElement(),
    removeNamespace(),
    replaceNode(XmlElement(QName("*** replacement ***"))),
    replaceNode(XmlText("*** replacement ***")))
