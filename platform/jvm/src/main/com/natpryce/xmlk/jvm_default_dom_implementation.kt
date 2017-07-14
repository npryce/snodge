package com.natpryce.xmlk

import javax.xml.parsers.DocumentBuilderFactory


fun defaultDOMImplementation() =
    DocumentBuilderFactory.newInstance().newDocumentBuilder().domImplementation

