package com.natpryce.snodge.xml

import com.natpryce.snodge.combine
import com.natpryce.snodge.reflect.troublesomeClasses
import com.natpryce.snodge.text.possiblyMeaningfulStrings
import com.natpryce.xmlk.QName
import com.natpryce.xmlk.XmlElement
import com.natpryce.xmlk.XmlNode
import com.natpryce.xmlk.XmlText
import com.natpryce.xmlk.minusAttribute
import com.natpryce.xmlk.minusChild
import com.natpryce.xmlk.replaceChild
import com.natpryce.xmlk.withAttribute


fun removeAttribute() = XmlMutagen<XmlElement> { _, element ->
    element.attributes.keys.asSequence().map { lazy { element.minusAttribute(it) } }
}

fun removeElement() = XmlMutagen<XmlElement> { _, element ->
    element.children.indices.map { lazy { element.minusChild(it) } }.asSequence()
}

fun removeNamespace() = XmlMutagen<XmlElement> { _, element ->
    sequenceOf(lazy { element.copy(name = element.name.withoutNamespace()) }) +
        element.attributes.asSequence()
            .filter { (name, _) -> name.namespaceURI != null }
            .map { (name, value) ->
                lazy { element.minusAttribute(name).withAttribute(name.withoutNamespace() to value) }
            }
}

inline fun <reified T: XmlNode> replaceNode(replacement: XmlNode) = XmlMutagen<XmlElement> { _, element ->
    element.children.withIndex().asSequence()
        .filter { (_, child) -> child is T }
        .map { (i, _) -> lazy { element.replaceChild(i, replacement) } }
}

fun replaceText(newText: String) = replaceNode<XmlText>(XmlText(newText))

private fun QName.withoutNamespace() = QName(localPart)

fun defaultXmlMutagens() = combine(
    removeAttribute(),
    removeElement(),
    removeNamespace(),
    replaceNode<XmlNode>(XmlElement(QName("replacement"))),
    replaceNode<XmlNode>(XmlText("replacement")),
    combine(
        possiblyMeaningfulStrings().map(::replaceText)
    ),
    combine(
        troublesomeClasses().map(::replaceText)
    )
)
