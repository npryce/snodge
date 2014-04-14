package com.natpryce.snodge;

import java.util.List;

public interface Mutator<T> {
    List<T> mutate(T original, int mutationCount);
}
