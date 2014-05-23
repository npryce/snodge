Snodge
======

A small, extensible Java library to randomly mutate JSON documents. Useful for fuzz testing.

Examples of things you can test by mutating known good JSON documents:

- unexpected JSON structures will not make your application code throw unchecked exceptions
- your application code ignores additional fields
- your application code does not throw unchecked exceptions when parsing values from JSON strings
- and more!


[Download from Bintray](https://bintray.com/npryce/maven/snodge/)

- Version 2.x.x.x (java8 branch) is for Java 8, and uses streams and Java 8 function types
- Version 1.x.x.x (master branch) is for Java 7 and depends on Guava


In a Nutshell
-------------

Output 10 random mutations of the JSON document:

Java 7:

~~~~~~~~~~~~~~~~~~~~~~java
JsonMutator mutator = new JsonMutator();

String originalJson = "{\"x\": \"hello\", \"y\": [1,2,3]}";

for (String mutatedJson : mutator.forStrings().mutate(originalJson, 10)) {
    System.out.println(mutatedJson);
}
~~~~~~~~~~~~~~~~~~~~~~

Java 8:

~~~~~~~~~~~~~~~~~~~~~~java
JsonMutator mutator = new JsonMutator();

String originalJson = "{\"x\": \"hello\", \"y\": [1,2,3]}";

mutator.forStrings().mutate(originalJson, 10)
        .forEach(System.out::println);
~~~~~~~~~~~~~~~~~~~~~~

Example output:

~~~~~~~~~~~~~~~~~~~~~~
{"x":"hello","y":[1,2,3,null]}
{"y":[1,2,3],"x":{}}
{"x":"hello","y":[2,3]}
{"x":"hello","y":[{},2,3]}
{"x":"hello"}
{"x":"hello","y":[1,2,{}]}
{"x":"hello","y":[1,null,3]}
{"y":[1,2,3],"x":"hello"}
{"y":[1,2,3],"x":"a string"}
{"x":"hello","y":[99,2,3]}
~~~~~~~~~~~~~~~~~~~~~~


Concepts
--------

| **Concept**        | **Explanation** |
|--------------------|-----------------|
| *JsonMutator*      | maps a document to a number of mutated versions of that document. Is created with a set of Mutagens that define how the document can be mutated.  Mutations applied are selected randomly so that there is an equal chance of any mutation being applied. |
| *Mutagen*          | given an element in a JSON document, returns zero or more DocumentMutations that mutate that element.  You can write your own Mutagens to perform application-specific mutation. |
| *DocumentMutation* | a function from JSON document to JSON document, that returns the original document mutated in some way. |


To build:

* Java JDK 7 (master branch) or JDK 8 (java8 branch)
* GNU Make
