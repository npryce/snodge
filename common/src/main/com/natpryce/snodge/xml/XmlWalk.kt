package com.natpryce.snodge.xml

import com.natpryce.snodge.walk
import com.natpryce.xmlk.XmlDocument
import com.natpryce.xmlk.XmlElement
import com.natpryce.xmlk.XmlNode


fun XmlDocument.walk(): Sequence<Pair<XmlNode, (XmlNode) -> XmlDocument>> =
    children.asSequence().withIndex()
        .flatMap { (i, child) -> child.walk(XmlNode::listChildren, { copy(children = children.replace(i, it)) }) }

private fun XmlNode.listChildren(): Sequence<Pair<XmlNode, (XmlNode) -> XmlNode>> =
    if (this is XmlElement) {
        children.asSequence().withIndex()
            .map { (i, child) -> child to childReplacer(i) }
    }
    else {
        emptySequence()
    }

private fun XmlElement.childReplacer(i: Int) =
    fun(newChild: XmlNode) =
        copy(children = children.replace(i, newChild))

private fun <T> List<T>.replace(i: Int, newElement: T) =
    toMutableList().apply { set(i, newElement) }.toList()

