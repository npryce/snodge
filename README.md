Snodge
======

A small, extensible Java library to randomly mutate JSON documents. Useful for fuzz testing.

Examples of things you can test by mutating known good JSON documents:

- unexpected JSON structures will not make your application code throw unchecked exceptions
- your application code ignores additional fields
- your application code does not throw unchecked exceptions when parsing values from JSON strings
- and more!


In a Nutshell
-------------

~~~~~~~~~~~~~~~~~~~~~~java
JsonMutator mutator = new JsonMutator();
String originalJson = "{\"x\": 1, \"y\": 2}";

for (String mutatedJson : mutator.mutate(originalJson, 10)) {
    System.out.println(mutatedJson);
}
~~~~~~~~~~~~~~~~~~~~~~

Outputs 10 random mutations of the JSON document, for example:

~~~~~~~~~~~~~~~~~~~~~~
null
{"y":2,"x":null}
false
99
{"x":1,"y":true}
{"y":2,"x":{}}
[]
{"x":1}
{"x":1,"y":2,"xx":{}}
{"y":2,"x":-99}
~~~~~~~~~~~~~~~~~~~~~~


Concepts
--------

| **Concept**        | **Explanation** |
|--------------------|-----------------|
| *JsonMutator*      | maps a document to a number of mutated versions of that document. Is created with a set of Mutagens that define how the document can be mutated.  Mutations applied are selected randomly so that there is an equal chance of any mutation being applied. |
| *Mutagen*          | given an element in a JSON document, returns zero or more DocumentMutations that mutate that element.  You can write your own Mutagens to perform application-specific mutation. |
| *DocumentMutation* |  a function from JSON document to JSON document, that returns the original document mutated in some way. |

Dependencies
------------

* [Gson](https://code.google.com/p/google-gson/) - for JSON parsing and document model
* [Guava](https://code.google.com/p/guava-libraries/) - for transforming and filtering data

To build:

* Java JDK 7
* GNU Make 
