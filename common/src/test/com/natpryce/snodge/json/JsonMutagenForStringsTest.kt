package com.natpryce.snodge.json

import com.natpryce.jsonk.JsonNull
import com.natpryce.jsonk.toJsonElement
import com.natpryce.snodge.Random
import com.natpryce.snodge.mutant
import com.natpryce.snodge.plus
import kotlin.test.Test
import kotlin.test.assertNotEquals

class JsonMutagenForStringsTest {
    val mutagen = addObjectProperty(JsonNull) + addArrayElement(JsonNull)
    val random = Random()
    
    @Test
    fun can_mutate_json_text() {
        val original = """
        {
            "num": 1,
            "list": [1,2,3]
        }
        """
        
        val mutant = random.mutant(mutagen.forStrings(), original)
        
        assertNotEquals(actual = mutant, illegal = original)
        
        canParse(mutant)
    }
    
    private fun canParse(mutated: String) {
        // Does not blow up
        mutated.toJsonElement()
    }
}