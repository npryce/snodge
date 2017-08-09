package com.natpryce.xmlk

import com.natpryce.testing.projectFile
import org.junit.Test
import kotlin.test.assertEquals

class ExampleXmlFilesConsistencyTest {
    val dataDir = projectFile("test-data/xml")
    
    @Test
    fun `all files are listed`() {
        assertEquals(expected = dataDir.list().filter { it.endsWith(".xml") }.toSet(), actual = ExampleXmlFiles.list())
    }
}
