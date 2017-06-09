package com.natpryce.snodge.xml

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.internal.mapLazy
import com.natpryce.xmlk.XmlDocument
import com.natpryce.xmlk.XmlNode
import java.util.Random

typealias XmlNodeMutagen<T> = (random: Random, elementToMutate: T) -> Sequence<Lazy<XmlNode>>

inline fun <reified T: XmlNode> XmlMutagen(noinline elementMutagen: XmlNodeMutagen<T>): Mutagen<XmlDocument> =
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
