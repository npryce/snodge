# Mutagen Operators

A _Mutagen_ that mutates values of type _T_ is a function of type `(Random, T)->Sequence<Lazy<T>>`.  Any operations that apply to functions can be used with Mutagens.  Snodge also defines convenience operators for composing and modifying Mutagens. 

## Generic Mutagen Operators

### Always

The `always` function returns a Mutagen that ignores the original value and returns a fixed sequence of values. 

For example:

* a mutagen of Strings that generates three fixed mutations: "a", "b", and "c":

  ~~~~~~~~kotlin
  always("a", "b", "c") 
  ~~~~~~~~

* a mutagen of Strings that generates one fixed mutation, in JSON format:

  ~~~~~~~~kotlin
  always("""{"a": 10}""")
  ~~~~~~~~


### Combine

The `combine` function builds a Mutagen that returns all mutations from the mutagens passed as arguments.

The `+` operator is overloaded to combine two mutagens.  

For example:

* a mutagen of Strings that generates six fixed mutations: "a", "b", "c", "d", "e", and "f":

  ~~~~~~~~kotlin
  combine(always("a", "b"), always("c", "d"), always("e", "f"))
  ~~~~~~~~

* The following expression, using the + operator, is equivalent to the one above:

  ~~~~~~~~kotlin
  always("a", "b") + always("c", "d") + always("e", "f")
  ~~~~~~~~


### And

The `Mutagen.and` extension method further mutates the mutants returned by a mutagen by applying another mutagen to them, returning both the original mutants _and_ the mutated mutants.   

For example:

* a mutagen for JSON serialised as UTF-8 text, that also generates mutants with invalid UTF-8 byte sequences:

  ~~~~~~~~kotlin
  allJsonLMutagens().forEncodedStrings(UTF_8).and(invalidUTF8())
  ~~~~~~~~  

### Repeat

The `repeat` function applies a Mutagen multiple times to the original value.  If the Mutagen is deterministic, then this is a pointless operation.  But if the Mutagen is randomised, then this operation will produce multiple (probably) different mutants.
    
For example:

* a mutagen that mutates a String by generating three mutants in which random substrings have been replaced with "xyz":

  ~~~~~~~~kotlin
  repeat(3, splice("xyz"))
  ~~~~~~~~

### Filtered

The `Mutagen.filtered` extension method applies the mutagen only to values that match a given predicate.


### Mapped

The `Mutagen.mapped` extension method converts a Mutagen of one type into a Mutagen of another given two functions that map values between the two types.

This lets you apply further Mutagens to fuzz the mapped mutations.

For example:

* A hypothetical example that converts a mutagen of XML Documents to a mutagen of Strings by serialising and deserialising the DOM to and from Strings:
 
  ~~~~~~~~kotlin
  allXMLMutagens().mapped({XML.toString(it)}, {XML.fromString(it)})
  ~~~~~~~~  
  
Convenience functions are provided for common use cases:

* `Mutagen<JsonElement>.forStrings()` turns a mutagen of a JSON document object model into a mutagen of JSON serialised as text.
* `Mutagen<String>.encodedAs(charset: Charset): Mutagen<ByteArray>` performs character encoding/decoding.
* `Mutagen<ByteArray>.decodedAs(charset: Charset): Mutagen<String>` performs the opposite mapping.


### With Probability

The function `Mutagen<T>.withProbability(p: Double)` makes a mutagen have an effect some of the time, and return 
no mutations otherwise.

For example:

* a mutagen of Strings that generates a fixed mutation, "xxx", 10% of the time:

  ~~~~~~~~kotlin
  always("xxx").withProbability(0.1)
  ~~~~~~~~

* a more realistic example, a stream of mutated JSON serialised UTF-8 text, with occasional mutations that contain invalid UTF-8 byte sequences:

  ~~~~~~~~kotlin
  allJsonMutagens().forEncodedString(UTF_8).and(invalidUTF8().withProbability(0.1))
  ~~~~~~~~

