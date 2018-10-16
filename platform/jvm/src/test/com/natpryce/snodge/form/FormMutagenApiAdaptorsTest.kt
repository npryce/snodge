package com.natpryce.snodge.form

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import kotlin.test.Test

class FormMutagenApiAdaptorsTest {
    val original = form(
        "a" to "a1",
        "b" to "b1",
        "c" to "",
        "d" to "d1",
        "b" to "b2",
        "a" to "a2")
    
    @Test
    fun `can apply mutagen to Undertow FormData`() {
        assertThat(original.groupBy { it.first }, equalTo(original.toFormData().toForm().groupBy { it.first }))
    }
}
