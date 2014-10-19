package com.natpryce.snodge;

import java.util.stream.Stream;

public class IdentityMutator<T> implements Mutator<T> {
    @Override
    public Stream<T> mutate(T original, int mutationCount) {
        return Stream.of(original);
    }
}
