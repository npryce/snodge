package com.natpryce.snodge;

public interface Mutator<T> {
    Iterable<T> mutate(T original, int mutationCount);
}
