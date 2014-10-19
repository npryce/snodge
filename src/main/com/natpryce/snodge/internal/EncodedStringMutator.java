package com.natpryce.snodge.internal;

import com.natpryce.snodge.Mutator;

import java.nio.charset.Charset;
import java.util.stream.Stream;

public class EncodedStringMutator implements Mutator<byte[]> {
    private final Mutator<String> stringMutator;
    private final Charset charset;

    public EncodedStringMutator(Charset charset, Mutator<String> stringMutator) {
        this.charset = charset;
        this.stringMutator = stringMutator;
    }

    @Override
    public Stream<byte[]> mutate(byte[] originalBytes, int mutationCount) {
        return stringMutator.mutate(new String(originalBytes, charset), mutationCount)
            .map(stringMutant -> stringMutant.getBytes(charset));
    }
}
