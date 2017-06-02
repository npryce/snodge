package com.natpryce.snodge.text

import com.natpryce.hamkrest.should.shouldMatch
import com.natpryce.snodge.mutants
import org.junit.Assert.fail
import org.junit.Test
import java.nio.ByteBuffer
import java.nio.charset.CharacterCodingException
import java.nio.charset.CodingErrorAction
import java.util.Random
import kotlin.text.Charsets.UTF_8

class InvalidUTF8Test {
    @Test
    fun `makes text invalid UTF-8`() {
        val original = "hello, world.".toByteArray(UTF_8)
        
        val decoder = UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT)
        
        Random().mutants(invalidUTF8(), 1000, original).forEach { mutantEncoded ->
            try {
                decoder.decode(ByteBuffer.wrap(mutantEncoded))
                fail("decoding should have failed")
            }
            catch (e: CharacterCodingException) {
                // expected
            }
        }
    }
}
