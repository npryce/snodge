package com.natpryce.jsonk

import com.natpryce.testing.projectFile
import org.junit.Test
import kotlin.test.assertEquals

class ExampleJsonFilesConsistencyTest {
    val dataDir = projectFile("test-data/json")
    
    @Test
    fun `all files are listed`() {
        assertEquals(expected = dataDir.list().filter { it.endsWith(".json") }, actual = ExampleJsonFiles.list())
    }
}
