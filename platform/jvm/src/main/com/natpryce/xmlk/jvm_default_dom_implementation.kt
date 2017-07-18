package com.natpryce.xmlk

import org.w3c.dom.DOMImplementation
import javax.xml.parsers.DocumentBuilderFactory


fun defaultDOMImplementation() =
    DocumentBuilderFactory.newInstance().apply { isCoalescing = false }.newDocumentBuilder().domImplementation

fun DOMImplementation.createEmptyDocument() =
    createDocument(null, null, null)
