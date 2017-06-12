package com.natpryce.snodge.xml

import com.natpryce.snodge.combine
import com.natpryce.xmlk.XmlElement
import com.natpryce.xmlk.minusAttribute
import com.natpryce.xmlk.minusChild
import com.natpryce.xmlk.withAttribute
import javax.xml.namespace.QName


fun removeAttribute() = XmlMutagen<XmlElement> { _, element ->
    element.attributes.keys.asSequence().map { lazy { element.minusAttribute(it) } }
}

fun removeElement() = XmlMutagen<XmlElement> { _, element ->
    element.children.indices.asSequence().map { lazy { element.minusChild(it) } }
}

fun removeNamespace() = XmlMutagen<XmlElement> { _, element ->
    sequenceOf(lazy { element.copy(name = element.name.withoutNamespace()) }) +
        element.attributes.asSequence()
            .filter {(name, _) -> name.namespaceURI != null}
            .map { (name, value) -> lazy { element.minusAttribute(name).withAttribute(name.withoutNamespace() to value) }
        }
}

private fun QName.withoutNamespace() = QName(localPart)

fun defaultXmlMutagens() = combine(
    removeAttribute(),
    removeElement(),
    removeNamespace())
