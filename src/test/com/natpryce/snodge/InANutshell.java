package com.natpryce.snodge;

public class InANutshell {
    public static void main(String[] args) {
        JsonMutator mutator = new JsonMutator();
        String originalJson = "{\"x\": 1, \"y\": 2}";

        for (String mutatedJson : mutator.mutate(originalJson, 10)) {
            System.out.println(mutatedJson);
        }
    }
}
