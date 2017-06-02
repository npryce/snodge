package com.natpryce.snodge.bytes

import com.natpryce.snodge.Mutagen
import com.natpryce.snodge.always
import com.natpryce.snodge.splice


fun splice(b: ByteArray): Mutagen<ByteArray> = splice(always(b))

fun splice(sliceMutagen: Mutagen<ByteArray>): Mutagen<ByteArray> =
    splice(sliceMutagen, {it.size}, ByteArray::sliceArray, ByteArray::replaceRange)

private fun ByteArray.replaceRange(range: IntRange, inserted: ByteArray): ByteArray =
    sliceArray(0..range.first) + inserted + this.sliceArray(range.last..size)
