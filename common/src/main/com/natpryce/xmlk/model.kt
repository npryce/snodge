package com.natpryce.xmlk

interface HasChildren {
    val children: List<XmlNode>
}

data class XmlDocument(
    override val children: List<XmlNode>
) : HasChildren {
    val root = children.filterIsInstance<XmlElement>().single()
}


sealed class XmlNode

data class XmlText(val text: String, val asCData: Boolean = false) : XmlNode()

data class QName(
    val localPart: String,
    val namespaceURI: String? = null,
    val prefix: String? = null
)

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
fun XmlElement.replaceChild(index: Int, replacement: XmlNode) =
    copy(children = children.toMutableList().also { it[index] = replacement }.toList())

data class XmlProcessingInstruction(val target: String, val data: String? = null) : XmlNode()

data class XmlComment(val text: String) : XmlNode()
