package com.natpryce.snodge.bytes

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.always
import com.natpryce.snodge.splice


fun splice(b: ByteArray): Mutagen<ByteArray> = splice(always(b))

fun splice(sliceMutagen: Mutagen<ByteArray>): Mutagen<ByteArray> =
    splice(sliceMutagen, {it.size}, ByteArray::sliceArray, ByteArray::replaceRange)

internal fun ByteArray.replaceRange(range: IntRange, inserted: ByteArray): ByteArray =
    sliceArray(0..range.first-1) + inserted + this.sliceArray(range.last+1..size-1)
