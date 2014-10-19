package com.natpryce.snodge.internal;

import com.google.common.base.Function;
import com.natpryce.snodge.Mutator;

import java.nio.charset.Charset;

import static com.google.common.collect.Iterables.transform;

public class EncodedStringMutator implements Mutator<byte[]> {
    private final Mutator<String> stringMutator;
    private final Charset charset;

    public EncodedStringMutator(Charset charset, Mutator<String> stringMutator) {
        this.charset = charset;
        this.stringMutator = stringMutator;
    }

    @Override
    public Iterable<byte[]> mutate(byte[] originalBytes, int mutationCount) {
        return transform(stringMutator.mutate(new String(originalBytes, charset), mutationCount), new Function<String, byte[]>() {
            @Override
            public byte[] apply(String stringMutant) {
                return stringMutant.getBytes(charset);
            }
        });
    }
}
