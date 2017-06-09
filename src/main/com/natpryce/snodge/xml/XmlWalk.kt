package com.natpryce.snodge.xml

import com.natpryce.xmlk.XmlDocument
import com.natpryce.xmlk.XmlElement
import com.natpryce.xmlk.XmlNode


fun XmlDocument.walk(): Sequence<Pair<XmlNode, (XmlNode) -> XmlDocument>> =
    children.asSequence().withIndex().flatMap { (i, child) ->
        walk<XmlDocument>(child, { copy(children = children.replace(i, it)) })
    }

private fun <T> walk(element: XmlNode, replaceInDocument: (XmlNode) -> T): Sequence<Pair<XmlNode, (XmlNode) -> T>> =
    sequenceOf(Pair(element, replaceInDocument)) + element.walkChildren(replaceInDocument)

private fun <T> XmlNode.walkChildren(replaceInDocument: (XmlNode) -> T) =
    if (this is XmlElement) {
        children.asSequence().withIndex().flatMap { (i, child) ->
            walk(child, { replaceInDocument(copy(children = children.replace(i, it))) })
        }
    }
    else {
        emptySequence()
    }

private fun <T> List<T>.replace(i: Int, newElement: T) =
    toMutableList().apply { set(i, newElement) }.toList()

