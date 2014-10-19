package com.natpryce.snodge;

import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class IdentityMutatorTest {
    @Test
    public void createsASingleNonMutationOfOriginal() {
        Mutator<Integer> m = IdentityMutator.id();

        int original = 8;
        int mutantCount = 100;

        assertThat("mutations",
                newArrayList(m.mutate(original, mutantCount)), equalTo(singletonList(original)));
    }
}
