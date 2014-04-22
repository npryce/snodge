package com.natpryce.snodge;

import java.util.stream.Stream;

public interface Mutator<T> {
    Stream<T> mutate(T original, int mutationCount);
}
