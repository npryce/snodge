package com.natpryce.xmlk

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters


@RunWith(Parameterized::class)
class JsonRoundTripTests(val exampleName: String) {
    companion object {
        @Parameters(name = "{0}") @JvmStatic
        fun examples() = ExampleXmlFiles.list()
    }
    
    @Test
    fun `round trips XML`() {
        val xmlText = javaClass.getResource(exampleName).readText()
        val roundTrippedViaXmlK = xmlText.toXmlDocument().toXmlString()
        
        Assert.assertEquals(xmlText, roundTrippedViaXmlK)
    }
}
