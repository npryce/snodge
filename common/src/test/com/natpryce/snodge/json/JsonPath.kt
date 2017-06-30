package com.natpryce.snodge.json

import com.natpryce.jsonk.JsonArray
import com.natpryce.jsonk.JsonElement
import com.natpryce.jsonk.JsonObject

data class JsonPath(private val steps: List<Any> ) {
    constructor(vararg steps: Any) : this(steps.toList())
    
    override fun toString(): String {
        return pathBitsToString(steps, steps.size)
    }
    
    operator fun invoke(root: JsonElement): JsonElement {
        return steps.foldIndexed(root) { i, result, _ ->
            applyPathElement(root, i, result)
        }
    }
    
    private fun applyPathElement(root: JsonElement, i: Int, parent: JsonElement): JsonElement {
        val pathBit = steps[i]
        
        return if (pathBit is String) {
            jsonObjectWithProperty(root, i, parent, pathBit).get(pathBit)!!
        }
        else if (pathBit is Int) {
            jsonArrayWithIndex(root, i, parent, pathBit).get(pathBit)
        }
        else {
            throw IllegalStateException("unexpected path element: " + pathBitsToString(steps, i))
        }
    }
    
    private fun jsonObjectWithProperty(root: JsonElement, i: Int, parent: JsonElement, memberName: String): JsonObject {
        check(parent is JsonObject, "expected object", steps, i, root)
        val original = parent as JsonObject
        check(memberName in original, "no such member", steps, i, root)
        return original
    }
    
    private fun jsonArrayWithIndex(root: JsonElement, i: Int, parent: JsonElement, index: Int): JsonArray {
        check(parent is JsonArray, "expected array", steps, i, root)
        val array = parent as JsonArray
        check(array.size > index, "index out of bounds", steps, i, root)
        return array
    }
    
    private fun check(isOk: Boolean, what: String, pathBits: List<Any>, badOne: Int, entireDocument: JsonElement) {
        if (!isOk) {
            throw IllegalArgumentException(what + " at " + pathBitsToString(pathBits, badOne + 1) + " in " + entireDocument)
        }
    }
    
    private fun pathBitsToString(pathBits: List<Any>, count: Int): String {
        return pathBits.subList(0, count)
            .map { it.toString() }
            .joinToString(prefix = "/", separator = "/", postfix = "")
    }
    
    companion object {
        val root = JsonPath(emptyList())
    }
}

