package com.natpryce.xmlk

import javax.xml.namespace.QName

interface HasText {
    val text: String
}

interface HasChildren {
    val children: List<XmlNode>
}

data class XmlDeclaration(val version: String, val encoding: String? = null)

data class XmlDocument(
    val xml: XmlDeclaration?,
    override val children: List<XmlNode>
): HasChildren {
    val root = children.single { it is XmlElement }
}


sealed class XmlNode

data class XmlText(override val text: String) : XmlNode(), HasText

data class XmlCData(override val text: String) : XmlNode(), HasText

data class XmlElement(
    val name: QName,
    val attributes: Map<QName, String>,
    override val children: List<XmlNode>
) : XmlNode(), HasChildren
