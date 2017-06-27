package com.natpryce.snodge.form

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.mapped
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset
import kotlin.text.Charsets.UTF_8

fun parseForm(s: String, charset: Charset = UTF_8): Form =
    s.split('&')
        .map { field ->
            val index = field.indexOf('=')
            if (index < 0) {
                field to ""
            }
            else {
                field.substring(0, index) to field.substring(index + 1)
            }
        }
        .map { (first, second) ->
            URLDecoder.decode(first, charset.name()) to URLDecoder.decode(second, charset.name())
        }

fun Form.toXWwwFormUrlencoded(charset: Charset = UTF_8) =
    map { (key, value) -> URLEncoder.encode(key, charset.name()) + "=" + URLEncoder.encode(value, charset.name()) }
        .joinToString("&")

fun Mutagen<Form>.forStrings(charset: Charset = UTF_8): Mutagen<String> =
    mapped({ parseForm(it, charset) }, { it.toXWwwFormUrlencoded(charset) })
