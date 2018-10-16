package com.natpryce.snodge.xml

import com.natpryce.xmlk.QName
import com.natpryce.xmlk.XmlComment
import com.natpryce.xmlk.XmlDocument
import com.natpryce.xmlk.XmlElement
import com.natpryce.xmlk.XmlText
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlWalkTest {
    @Test
    fun returns_all_elements() {
        val doc = XmlDocument(
            children = listOf(
                XmlComment("example xml"),
                XmlElement(name = QName("alice"), attributes = mapOf(QName("a") to "b"), children = listOf(
                    XmlElement(QName("bob")),
                    XmlText("hello world"),
                    XmlElement(QName("carol"))))
            )
        )
        
        assertEquals(actual = doc.walk().count(), expected = 5, message = "walk length")
    }
}
