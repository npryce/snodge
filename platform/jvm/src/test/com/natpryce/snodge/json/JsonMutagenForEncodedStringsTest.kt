package com.natpryce.snodge.json

import com.natpryce.jsonk.JsonNull
import com.natpryce.jsonk.toJsonElement
import com.natpryce.jsonk.toJsonString
import com.natpryce.snodge.mutant
import com.natpryce.snodge.plus
import kotlin.test.Test
import java.nio.charset.Charset
import kotlin.random.Random
import kotlin.test.assertNotEquals

class JsonMutagenForEncodedStringsTest {
    val random = Random
    val mutagen = addObjectProperty(JsonNull) + addArrayElement(JsonNull)
    
    @Test
    fun can_mutate_encoded_json_text() {
        val charset = Charset.forName("UTF-8")
        
        val originalString = obj(
            "num" to 1,
            "list" to list(1, 2, 3)).toJsonString()
        
        val originalBytes = originalString.toByteArray(charset)
        
        val mutatedBytes = random.mutant(mutagen.forEncodedStrings(charset), originalBytes)
        
        assertNotEquals(actual = mutatedBytes, illegal = originalBytes)
        
        val mutatedString = String(mutatedBytes, charset)
        
        canParse(mutatedString)
    }
    
    private fun canParse(mutated: String) {
        // Does not blow up
        mutated.toJsonElement()
    }
}