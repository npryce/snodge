package com.natpryce.snodge.bytes

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.always
import com.natpryce.snodge.internal.replaceRange
import com.natpryce.snodge.splice


fun splice(b: ByteArray): Mutagen<ByteArray> = splice(always(b))

fun splice(sliceMutagen: Mutagen<ByteArray>): Mutagen<ByteArray> =
    splice(sliceMutagen, { it.size }, ByteArray::sliceArray, ByteArray::replaceRange)

