# Writing a JSON Mutagen

The `JsonMutagen` function turns a mutagen of a node within a JSON document into a mutagen of the entire document. It takes care of walking the JSON tree and selecting only those nodes that have the required type.

For example, a Mutagen that replaces any number node with a random integer:

~~~~kotlin
fun numberToRandomInteger() = JsonMutagen<JsonNumber> { random, original ->
    sequenceOf(lazy { JsonNumber(random.nextInt()) })
}
~~~~
