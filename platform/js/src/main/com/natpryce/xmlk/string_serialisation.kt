package com.natpryce.xmlk

import org.w3c.dom.DOMImplementation
import org.w3c.dom.parsing.DOMParser
import org.w3c.dom.parsing.XMLSerializer
import kotlin.browser.document


fun String.toXmlDocument(): XmlDocument =
    DOMParser().parseFromString(this, "application/xml").toXmlDocument()

fun XmlDocument.toXmlString(implementation: DOMImplementation = document.implementation): String =
    XMLSerializer().serializeToString(toDOM(implementation))
