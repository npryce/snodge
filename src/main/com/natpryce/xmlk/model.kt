package com.natpryce.xmlk

import javax.xml.namespace.QName

interface HasChildren {
    val children: List<XmlNode>
}

data class XmlDeclaration(val version: String, val encoding: String? = null)

data class XmlDocument(
    val xml: XmlDeclaration?,
    override val children: List<XmlNode>
) : HasChildren {
    val root = children.single { it is XmlElement }
}


sealed class XmlNode

data class XmlText(val text: String, val asCData: Boolean = false) : XmlNode()

data class XmlElement(
    val name: QName,
    val attributes: Map<QName, String> = emptyMap(),
    override val children: List<XmlNode> = emptyList()
) : XmlNode(), HasChildren

fun XmlElement.withAttribute(attr: Pair<QName, String>) =
    copy(attributes = attributes + attr)
fun XmlElement.minusAttribute(attrName: QName) =
    copy(attributes = attributes - attrName)

fun XmlElement.plusChild(c: XmlNode) =
    copy(children = children + c)
fun XmlElement.minusChild(index: Int) =
    copy(children = children.toMutableList().apply { removeAt(index) }.toList())

data class XmlProcessingInstruction(val target: String, val data: String? = null) : XmlNode()

data class XmlComment(val text: String) : XmlNode()
