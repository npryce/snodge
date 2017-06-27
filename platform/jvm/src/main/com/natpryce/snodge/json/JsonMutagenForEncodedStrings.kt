package com.natpryce.snodge.json

import com.natpryce.jsonk.JsonElement
import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.text.encodedAs
import java.nio.charset.Charset

fun Mutagen<JsonElement>.forEncodedStrings(encoding: Charset) =
    forStrings().encodedAs(encoding)
