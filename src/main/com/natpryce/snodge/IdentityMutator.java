package com.natpryce.snodge;

import static java.util.Collections.singleton;

public class IdentityMutator<T> implements Mutator<T> {
    @Override
    public Iterable<T> mutate(T original, int mutationCount) {
        return singleton(original);
    }

    public static <T> Mutator<T> id() {
        return new IdentityMutator<>();
    }
}
