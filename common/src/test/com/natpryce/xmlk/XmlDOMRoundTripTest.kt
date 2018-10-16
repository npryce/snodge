package com.natpryce.xmlk

import kotlin.test.Test
import kotlin.test.assertEquals


class XmlDOMRoundTripTest {
    @Test
    fun `round_trips_XML`() {
        ExampleXmlFiles.forEach { exampleName, original ->
            assertEquals(expected = original, actual = original.toDOM().toXmlDocument(), message = exampleName)
        }
    }
}
