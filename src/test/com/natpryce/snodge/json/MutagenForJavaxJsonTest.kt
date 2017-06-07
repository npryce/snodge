package com.natpryce.snodge.json

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.jsonk.JsonElement
import org.junit.Test
import com.google.gson.JsonArray as GsonArray
import com.google.gson.JsonElement as GsonElement
import com.google.gson.JsonNull as GsonNull
import com.google.gson.JsonObject as GsonObject
import com.google.gson.JsonPrimitive as GsonPrimitive


class MutagenForJavaxJsonTest {
    @Test
    fun `can apply mutagen to Gson JSON tree`() {
        val original: JsonElement = obj(
            "a" to 1,
            "b" to 2.5,
            "c" to null,
            "d" to list(3, 4, obj("e" to 5)),
            "f" to true,
            "g" to false
        )
        
        assertThat(original.toJsonp().toJsonk(), equalTo(original))
    }
}
