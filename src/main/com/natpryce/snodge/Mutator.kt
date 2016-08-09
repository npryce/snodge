package com.natpryce.snodge

interface Mutator<T> {
    fun mutate(original: T, mutationCount: Int): List<T>
    
    companion object {
        fun <T> id() = IdentityMutator<T>()
    }
}
