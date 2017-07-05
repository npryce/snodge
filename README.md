Snodge
======

A small, extensible library to randomly mutate JSON documents, XML, HTML forms, text and binary data. Useful for [fuzz testing](https://en.wikipedia.org/wiki/Fuzzing).

Examples of things you can test by mutating known good input:

- unexpected structures will not make your application code throw unchecked exceptions
- your application code ignores additional fields
- your application code does not throw unchecked exceptions when parsing values from JSON strings
- your application does not instantiate arbitrary classes named in JSON data (a potential security risk)
- and more!

See an [interactive demonstration](https://npryce.github.io/snodge/demo/demo.html).

[Download from Maven Central](http://mvnrepository.com/artifact/com.natpryce/snodge)

- Version 3.x.x.x ([kotlin branch](https://github.com/npryce/snodge/tree/kotlin)) is for Kotlin
- Version 2.x.x.x ([java8 branch](https://github.com/npryce/snodge/tree/java8)) is for Java 8, and uses streams and Java 8 function types
- Version 1.x.x.x ([java7 branch](https://github.com/npryce/snodge/tree/java7)) is for Java 7 and depends on Guava
