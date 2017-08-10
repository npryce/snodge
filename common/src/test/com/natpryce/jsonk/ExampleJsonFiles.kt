package com.natpryce.jsonk

private val arrayExample = """[
  {
    "a": 1,
    "b": 2
  },
  {
    "c": 3,
    "d": 4
  },
  {
    "a": 1,
    "b": 2,
    "c": 3,
    "d": 4
  }
]"""

private val objectExample = """{
  "foo": "bar",
  "a_null": null,
  "space in key": "ok",
  "truth": true,
  "beauty": false,
  "an_array": [
    1,
    "2",
    "3"
  ],
  "an_object": {
    "one": 1,
    "2": 2,
    "three": [
      1,
      2,
      3
    ]
  }
}"""

object ExampleJsonFiles {
    private val examples = mapOf(
        "object-example.json" to objectExample,
        "array-example.json" to arrayExample
    )
    
    fun list() = examples.keys
    
    operator fun get(name: String): JsonElement =
        (examples[name]?: throw IllegalArgumentException("JSON example $name not found"))
            .toJsonElement()
            
    
    fun forEach(block: (String, JsonElement) -> Unit) {
        list().forEach { name -> block(name, get(name)) }
    }
}
