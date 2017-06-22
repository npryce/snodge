package com.natpryce.snodge.xml

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.xmlk.XmlComment
import com.natpryce.xmlk.XmlDocument
import com.natpryce.xmlk.XmlElement
import com.natpryce.xmlk.XmlText
import org.junit.Test
import javax.xml.namespace.QName

class XmlWalkTest {
    @Test
    fun `returns all elements`() {
        val doc= XmlDocument(
            xml=null,
            children = listOf(
                XmlComment("example xml"),
                XmlElement(name= QName("alice"), attributes = mapOf(QName("a") to "b"), children = listOf(
                    XmlElement(QName("bob")),
                    XmlText("hello world"),
                    XmlElement(QName("carol"))))
                )
            )
    
        assertThat("walk length", doc.walk().count(), equalTo(expected = 5))
    }
}
