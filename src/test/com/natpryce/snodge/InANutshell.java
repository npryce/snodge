package com.natpryce.snodge;

public class InANutshell {
    public static void main(String[] args) {
        JsonMutator mutator = new JsonMutator();
        String originalJson = "{\"x\": \"hello\", \"y\": [1,2,3]}";

        mutator.forStrings().mutate(originalJson, 10)
                .forEach(System.out::println);
    }
}
