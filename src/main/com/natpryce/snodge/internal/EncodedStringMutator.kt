package com.natpryce.snodge.internal

import com.natpryce.snodge.Mutator
import java.nio.charset.Charset

class EncodedStringMutator(
    private val charset: Charset,
    private val stringMutator: Mutator<String>

) : Mutator<ByteArray> {
    
    override fun invoke(original: ByteArray, mutationCount: Int) =
        stringMutator(String(original, charset), mutationCount).map { stringMutant -> stringMutant.toByteArray(charset) }
}
