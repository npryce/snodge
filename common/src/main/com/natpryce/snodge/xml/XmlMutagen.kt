package com.natpryce.snodge.xml

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.mapped
import com.natpryce.snodge.recurse
import com.natpryce.snodge.walk
import com.natpryce.xmlk.XmlDocument
import com.natpryce.xmlk.XmlElement
import com.natpryce.xmlk.XmlNode
import com.natpryce.xmlk.defaultDOMImplementation
import com.natpryce.xmlk.toDOM
import com.natpryce.xmlk.toXmlDocument
import com.natpryce.xmlk.toXmlString
import org.w3c.dom.DOMImplementation
import org.w3c.dom.Document

inline fun <reified T : XmlNode> XmlMutagen(crossinline elementMutagen: Mutagen<T>): Mutagen<XmlDocument> =
    recurse({ d: XmlDocument -> d.walk() }, elementMutagen)

fun Mutagen<XmlDocument>.forStrings() = mapped({ it.toXmlDocument() }, { it.toXmlString() })

fun Mutagen<XmlDocument>.forDOM(implementation: DOMImplementation = defaultDOMImplementation()): Mutagen<Document> =
    mapped({it.toXmlDocument()}, {it.toDOM(implementation)})


fun XmlDocument.walk(): Sequence<Pair<XmlNode, (XmlNode) -> XmlDocument>> =
    children.asSequence().withIndex().flatMap { (i, child) ->
        child.walk(XmlNode::listChildren, { copy(children = children.replace(i, it)) })
    }

private fun XmlNode.listChildren(): Sequence<Pair<XmlNode, (XmlNode) -> XmlNode>> =
    if (this is XmlElement) {
        children.asSequence().withIndex().map { (i, child) -> child to childReplacer(i) }
    }
    else {
        emptySequence()
    }

private fun XmlElement.childReplacer(i: Int) =
    fun(newChild: XmlNode) =
        copy(children = children.replace(i, newChild))

private fun <T> List<T>.replace(i: Int, newElement: T) =
    toMutableList().apply { set(i, newElement) }.toList()

