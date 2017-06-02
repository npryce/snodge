package com.natpryce.snodge.text

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.always
import com.natpryce.snodge.bytes.splice
import com.natpryce.snodge.combine
import com.natpryce.snodge.mapped
import java.nio.charset.Charset

/**
 * Transform a Mutagen of strings to a mutagen of byte arrays by using the given character encoding
 */
fun Mutagen<String>.encodedAs(encoding: Charset): Mutagen<ByteArray> =
    mapped({ it.toString(encoding) }, { it.toByteArray(encoding) })

/**
 * Transform a Mutagen of byte arrays to a mutagen of strings by using the given character encoding
 */
fun Mutagen<ByteArray>.decodedAs(encoding: Charset): Mutagen<String> =
    mapped({ it.toByteArray(encoding) }, { it.toString(encoding) })

fun invalidUTF8(): Mutagen<ByteArray> = combine(
    (listOf(192, 193) + (245..255))
        .map { splice(byteArrayOf(it.toByte())) })
