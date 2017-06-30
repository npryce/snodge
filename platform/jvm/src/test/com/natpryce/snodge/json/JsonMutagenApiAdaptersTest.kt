package com.natpryce.snodge.json

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.jsonk.ExampleJsonFiles
import com.natpryce.jsonk.JsonElement
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import com.google.gson.JsonArray as GsonArray
import com.google.gson.JsonElement as GsonElement
import com.google.gson.JsonNull as GsonNull
import com.google.gson.JsonObject as GsonObject
import com.google.gson.JsonPrimitive as GsonPrimitive

@RunWith(Parameterized::class)
class JsonMutagenApiAdaptersTest(exampleName: String) {
    companion object {
        @Parameters(name = "{0}") @JvmStatic
        fun examples() = ExampleJsonFiles.list()
    }
    
    val original: JsonElement = ExampleJsonFiles.load(exampleName)
    
    @Test
    fun can_apply_mutagen_to_Gson_tree() {
        assertThat(original.toGson().toJsonk(), equalTo(original))
    }
    
    @Test
    fun can_apply_mutagen_to_Jackson_tree() {
        assertThat(original.toJackson().toJsonk(), equalTo(original))
    }
    
    @Test
    fun can_apply_mutagen_to_JSONP_tree() {
        assertThat(original.toJsonp().toJsonk(), equalTo(original))
    }
}
