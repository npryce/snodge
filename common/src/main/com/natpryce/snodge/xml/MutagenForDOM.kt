package com.natpryce.snodge.xml

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.mapped
import com.natpryce.xmlk.XmlDocument
import com.natpryce.xmlk.defaultDOMImplementation
import com.natpryce.xmlk.toDOM
import com.natpryce.xmlk.toXmlDocument
import org.w3c.dom.DOMImplementation
import org.w3c.dom.Document


fun Mutagen<XmlDocument>.forDOM(implementation: DOMImplementation = defaultDOMImplementation()): Mutagen<Document> =
    mapped({it.toXmlDocument()}, {it.toDOM(implementation)})
