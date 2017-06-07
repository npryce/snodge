package com.natpryce.snodge.json

import com.google.gson.Gson
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.jsonk.JsonElement
import org.junit.Test


class MutagenForJacksonTest {
    @Test
    fun `can apply mutagen to Jackson JSON tree`() {
        val original: JsonElement = obj(
            "a" to 1,
            "b" to 2.5,
            "c" to null,
            "d" to list(3, 4, obj("e" to 5)),
            "f" to true,
            "g" to false
        )
        
        assertThat(original.toJackson().toJsonk(), equalTo(original))
    }
}

