Snodge
======

A small, extensible library to randomly mutate JSON documents. Useful for fuzz testing.

Examples of things you can test by mutating known good JSON documents:

- unexpected JSON structures will not make your application code throw unchecked exceptions
- your application code ignores additional fields
- your application code does not throw unchecked exceptions when parsing values from JSON strings
- your application does not instantiate arbitrary classes named in JSON data (a potential security risk)
- and more!


[Download from Maven Central](http://mvnrepository.com/artifact/com.natpryce/snodge)

- Version 3.x.x.x (kotlin branch) is for Kotlin
- Version 2.x.x.x (java8 branch) is for Java 8, and uses streams and Java 8 function types
- Version 1.x.x.x (java7 branch) is for Java 7 and depends on Guava
