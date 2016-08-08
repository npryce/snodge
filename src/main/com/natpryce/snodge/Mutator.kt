package com.natpryce.snodge

import java.util.stream.Stream

interface Mutator<T> {
    fun mutate(original: T, mutationCount: Int): Stream<T>
    
    companion object {
        fun <T> id() = IdentityMutator<T>()
    }
}
