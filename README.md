Snodge
======

A small, extensible Java library to randomly mutate JSON documents. Useful for fuzz testing.

Examples of things you can test by mutating known good JSON documents:

- unexpected JSON structures will not make your application code throw unchecked exceptions
- your application code ignores additional fields
- your application code does not throw unchecked exceptions when parsing values from JSON strings
- and more!


Dependencies
------------

* [Gson](https://code.google.com/p/google-gson/) - for JSON parsing and document model
* [Guava](https://code.google.com/p/guava-libraries/) - for transforming and filtering data

Concepts
--------

| **Concept**   |                                                                    |
|---------------|--------------------------------------------------------------------|
| *JsonMutator* | maps a document to a number of mutated versions of that document. Is created with a set of Mutagens that define how the document can be mutated.  Mutations applied are selected randomly so that there is an equal chance of any mutation being applied. |
|---------------|--------------------------------------------------------------------|
| *Mutagen* | given an element in a JSON document, returns zero or more DocumentMutations that mutate that element.  You can write your own Mutagens to perform application-specific mutation. |
|---------------|--------------------------------------------------------------------|
| *DocumentMutation * |  a function from JSON document to JSON document, that returns the original document mutated in some way. |

