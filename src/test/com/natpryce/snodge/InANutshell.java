package com.natpryce.snodge;

public class InANutshell {
    public static void main(String[] args) {
        JsonMutator mutator = new JsonMutator();
        String originalJson = "{\"x\": \"hello\", \"y\": [1,2,3]}";

        for (String mutatedJson : mutator.forStrings().mutate(originalJson, 10)) {
            System.out.println(mutatedJson);
        }
    }
}
