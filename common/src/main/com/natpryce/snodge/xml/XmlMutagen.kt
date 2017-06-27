package com.natpryce.snodge.xml

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.Random
import com.natpryce.snodge.internal.mapLazy
import com.natpryce.snodge.mapped
import com.natpryce.xmlk.XmlDocument
import com.natpryce.xmlk.XmlNode
import com.natpryce.xmlk.toXmlDocument
import com.natpryce.xmlk.toXmlString

typealias XmlNodeMutagen<T> = (random: Random, elementToMutate: T) -> Sequence<Lazy<XmlNode>>

inline fun <reified T : XmlNode> XmlMutagen(crossinline elementMutagen: XmlNodeMutagen<T>): Mutagen<XmlDocument> =
    { random: Random, original: XmlDocument ->
        original.walk().flatMap { (node: XmlNode, replaceInDocument) ->
            if (node is T) {
                elementMutagen(random, node).mapLazy(replaceInDocument)
            }
            else {
                emptySequence()
            }
        }
    }


fun Mutagen<XmlDocument>.forStrings() = mapped({it.toXmlDocument()}, {it.toXmlString()})

