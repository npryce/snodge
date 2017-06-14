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

inline fun <reified T: XmlNode> replaceNode(replacement: XmlNode) = XmlMutagen<XmlElement> { _, element ->
    element.children.withIndex().asSequence()
        .filter { (_, child) -> child is T }
        .map { (i, _) -> lazy { element.replaceChild(i, replacement) } }
}

private fun QName.withoutNamespace() = QName(localPart)

private fun textReplacements() = listOf(
    "1",
    "-1",
    "0",
    "true",
    "false",
    "",
    "1.0",
    "-1.0",
    "0.0",
    "NaN",
    Long.MAX_VALUE.toString(),
    Long.MIN_VALUE.toString()
)

fun defaultXmlMutagens() = combine(
    removeAttribute(),
    removeElement(),
    removeNamespace(),
    replaceNode<XmlNode>(XmlElement(QName("replacement"))),
    replaceNode<XmlNode>(XmlText("replacement")),
    combine(
        textReplacements().map { replaceNode<XmlText>(XmlText(it)) }
    )
)
