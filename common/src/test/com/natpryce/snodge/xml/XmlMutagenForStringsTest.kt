package com.natpryce.snodge.xml;

import com.natpryce.snodge.Random
import com.natpryce.snodge.mutant
import com.natpryce.xmlk.toXmlDocument
import kotlin.test.Test
import kotlin.test.assertNotEquals

class XmlMutagenForStringsTest {
    val mutagen = replaceText("XXXX")
    val random = Random()
    
    @Test
    fun can_mutate_xml_text() {
        val original = """
        |<example>
        |  <!-- a comment -->
        |  <a>A</a>
        |  <b><![CDATA[B]]></b>
        |  <c:d xmlns:c="http://example.com/c" e="f">G</c:d>
        |  <h>
        |    <i>I</i>
        |    <j>J</j>
        |  </h>
        |</example>
        """.trimMargin()
        
        val mutant = random.mutant(mutagen.forStrings(), original)
        
        assertNotEquals(actual = mutant, illegal = original)
        
        canParse(mutant)
    }
    
    private fun canParse(mutated: String) {
        // Does not blow up
        mutated.toXmlDocument()
    }
}