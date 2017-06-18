package com.natpryce.snodge.form

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import kotlin.arrayOf as example

@RunWith(Parameterized::class)
class XWwwFormUrlencodedTest(@Suppress("UNUSED_PARAMETER") name: String, val encoded: String, val form: Form) {
    companion object {
        @JvmStatic @Parameters(name = "{0}")
        fun examples() = listOf(
            example("simple form fields and values",
                "a=b&c=d",
                form("a" to "b", "c" to "d")
            ),
            example("allows multiple values for same property",
                "a=1&c=X&a=2&a=3&c=Y",
                form("a" to "1", "c" to "X", "a" to "2", "a" to "3", "c" to "Y")
            ),
            example("encoded values",
                "a=hello+mum&b=hello%20world",
                form(
                    "a" to "hello mum",
                    "b" to "hello world")),
            example("encoded keys",
                "a+b=1&c%20d=2",
                form(
                    "a b" to "1",
                    "c d" to "2")
            )
        )
    }
    
    @Test
    fun `parses`() {
        assertThat(parseForm(encoded), equalTo(form))
    }
    
    @Test
    fun `encodes`() {
        assertThat(parseForm(form.toXWwwFormUrlencoded()), equalTo(form))
    }
}
