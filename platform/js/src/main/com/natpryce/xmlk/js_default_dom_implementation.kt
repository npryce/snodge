package com.natpryce.xmlk

import org.w3c.dom.DOMImplementation
import kotlin.browser.document

fun defaultDOMImplementation() = document.implementation

fun DOMImplementation.createEmptyDocument() =
    createDocument("", "", null)