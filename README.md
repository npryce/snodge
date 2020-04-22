Snodge
======

[![Kotlin](https://img.shields.io/badge/kotlin-1.3.61-blue.svg)](http://kotlinlang.org)
[![Build Status](https://travis-ci.org/npryce/snodge.svg?branch=kotlin)](https://travis-ci.org/npryce/snodge)
[![Maven Central](https://img.shields.io/maven-central/v/com.natpryce/snodge.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.natpryce%22%20AND%20a%3A%22snodge%22)
[![npm](https://img.shields.io/npm/v/snodge-3.svg)]()


A small, extensible Kotlin library to randomly mutate JSON & XML documents, text and binary data. Useful for [fuzz testing](https://en.wikipedia.org/wiki/Fuzzing).

Examples of things you can test by mutating known good data:

- unexpected structures will not make your application code throw unchecked exceptions
- your application code ignores additional properties
- your application code does not throw unchecked exceptions when parsing values from text properties
- your application does not instantiate arbitrary classes named in data (a potential security risk)
- your application copes with invalid Unicode encoding of text
- and much, much more!

See an [interactive demonstration](https://npryce.github.io/snodge/demo/demo.html).



In a Nutshell
-------------

Add a dependency on Snodge (replace `<version>` with the version of Snodge you wish to use):

~~~~~~~~~~~~~~~~~~~~~~gradle
testImplementation 'com.natpryce:snodge:<version>'
~~~~~~~~~~~~~~~~~~~~~~

For the JVM platform, add an implementation of the [JSR-374 JSONP API](http://docs.oracle.com/middleware/1213/wls/WLPRG/java-api-for-json-proc.htm), such as:

~~~~~~~~~~~~~~~~~~~~~~gradle
runtime 'org.glassfish:javax.json:1.1'
~~~~~~~~~~~~~~~~~~~~~~

Import the library:

~~~~~~~~~~~~~~~~~~~~~~kotlin
import com.natpryce.snodge.mutants
import com.natpryce.snodge.json.defaultJsonMutagens
~~~~~~~~~~~~~~~~~~~~~~

Output 10 random mutations of the JSON document:

~~~~~~~~~~~~~~~~~~~~~~kotlin
val random = Random()
val originalJson = "{\"x\": \"hello\", \"y\": [1,2,3]}"

random.mutants(defaultJsonMutagens().forStrings(), 10, originalJson)
    .forEach(::println)
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

API Adapters
------------

On the JVM, Snodge can mutate the JSON object models of the [Jackson](https://github.com/FasterXML/jackson), [GSON](https://github.com/google/gson) & [JSR-374 JSONP](http://docs.oracle.com/middleware/1213/wls/WLPRG/java-api-for-json-proc.htm) and [Argo](http://argo.sourceforge.net/) APIs, XML DOM, and JSON and XML serialised as text and binary. 

On JavaScript, Snodge can mutate XML as DOM Documents, and XML and JSON as text.

For more information, continue reading [the documentation](doc/).

Other versions
--------------

The Kotlin library is version 3.x.x.x.  

Previous versions:

- Version 2.x.x.x (java8 branch) is for Java 8, and uses streams and Java 8 function types
- Version 1.x.x.x (java7 branch) is for Java 7 and depends on Guava

[Download from Maven Central](http://mvnrepository.com/artifact/com.natpryce/snodge)
[Download from NPM](https://www.npmjs.com/package/snodge-3)

