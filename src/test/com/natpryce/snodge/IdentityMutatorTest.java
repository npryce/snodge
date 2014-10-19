package com.natpryce.snodge;

import org.junit.Test;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class IdentityMutatorTest {
    @Test
    public void createsASingleNonMutationOfOriginal() {
        Mutator<Integer> m = Mutator.id();

        int original = 8;
        int mutantCount = 100;

        assertThat("mutations",
                m.mutate(original, mutantCount).collect(toList()), equalTo(singletonList(original)));
    }
}
