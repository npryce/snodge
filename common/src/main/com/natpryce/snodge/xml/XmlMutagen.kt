package com.natpryce.snodge.xml

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.mapped
import com.natpryce.snodge.recurse
import com.natpryce.xmlk.XmlDocument
import com.natpryce.xmlk.XmlNode
import com.natpryce.xmlk.toXmlDocument
import com.natpryce.xmlk.toXmlString

inline fun <reified T : XmlNode> XmlMutagen(crossinline elementMutagen: Mutagen<T>): Mutagen<XmlDocument> =
    recurse(XmlDocument::walk, elementMutagen)

fun Mutagen<XmlDocument>.forStrings() = mapped({it.toXmlDocument()}, {it.toXmlString()})

