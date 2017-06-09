package com.natpryce.snodge.xml

import com.natpryce.snodge.combine
import com.natpryce.xmlk.XmlElement
import com.natpryce.xmlk.minusAttribute
import com.natpryce.xmlk.minusChild


fun removeAttribute() = XmlMutagen<XmlElement> { _, element ->
    element.attributes.keys.asSequence().map { lazy { element.minusAttribute(it) } }
}

fun removeElement() = XmlMutagen<XmlElement> { _, element ->
    element.children.indices.asSequence().map { lazy { element.minusChild(it) } }
}

fun allXmlMutagens() = combine(
    removeAttribute(),
    removeElement())
