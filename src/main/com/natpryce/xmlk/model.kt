package com.natpryce.xmlk

import javax.xml.namespace.QName

interface HasText {
    val text: String
}

data class XmlDocument(val root: XmlElement)

sealed class XmlNode
data class XmlText(override val text: String): XmlNode(), HasText
data class XmlCData(override val text: String): XmlNode(), HasText
data class XmlElement(val name: QName, val attributes: Map<QName,String>, val children: List<XmlNode>): XmlNode()
