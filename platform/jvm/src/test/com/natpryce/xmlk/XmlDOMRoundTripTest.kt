package com.natpryce.xmlk

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters


@RunWith(Parameterized::class)
class XmlDOMRoundTripTest(val exampleName: String) {
    @Test
    fun `round trips XML`() {
        val original = ExampleXmlFiles.load(exampleName)
        val roundTripped = original.toDOM().toXmlDocument()
        
        assertEquals(original, roundTripped)
    }
    
    companion object {
        @Parameters(name = "{0}") @JvmStatic
        fun examples() = ExampleXmlFiles.list()
    }
}
