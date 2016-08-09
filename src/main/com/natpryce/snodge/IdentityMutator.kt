package com.natpryce.snodge

class IdentityMutator<T> : Mutator<T> {
    override fun mutate(original: T, mutationCount: Int): List<T> {
        return listOf(original)
    }
}
