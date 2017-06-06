package com.natpryce.snodge.json

import com.natpryce.jsonk.JsonArray
import com.natpryce.jsonk.JsonElement
import com.natpryce.jsonk.JsonObject

data class JsonPath(
    private val steps: List<Any>

) : (JsonElement) -> JsonElement {
    
    constructor(vararg steps: Any) : this(steps.toList())
    
    override fun toString(): String {
        return pathBitsToString(steps, steps.size)
    }
    
    fun size(): Int {
        return steps.size
    }
    
    val isRoot: Boolean
        get() = size() == 0
    
    operator fun get(n: Int) = steps[(steps.size + n) % steps.size]
    
    fun extend(vararg morePath: Any) = JsonPath(steps + morePath.toList())
    
    override fun invoke(root: JsonElement): JsonElement {
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
    
    fun endsWith(vararg suffix: Any) =
        steps.size >= suffix.size
            && suffix.toList() == steps.subList(size() - suffix.size, size())
    
    fun startsWith(prefix: JsonPath): Boolean {
        return size() >= prefix.size() && steps.subList(0, prefix.size()) == prefix.steps
    }
    
    fun map(json: JsonElement, f: (JsonElement) -> JsonElement) =
        map(json, steps.size, f)
    
    fun replace(root: JsonElement, replacement: JsonElement) =
        map(root) { replacement }
    
    private fun replaceElement(root: JsonElement, parent: JsonElement, i: Int, replacement: JsonElement): JsonElement {
        val pathBit = steps[i]
        
        if (pathBit is String) {
            val original = jsonObjectWithProperty(root, i, parent, pathBit)
            
            return replaceObjectPropertyValue(original, pathBit, replacement)
        }
        else if (pathBit is Int) {
            val original = jsonArrayWithIndex(root, i, parent, pathBit)
            
            return replaceArrayElement(original, pathBit, replacement)
            
        }
        else {
            throw IllegalArgumentException("unexpected path element: " + pathBitsToString(steps, i))
        }
    }
    
    private fun replaceObjectPropertyValue(original: JsonObject, memberName: String, replacement: JsonElement): JsonElement {
        return original.copy(properties = original.properties + (memberName to replacement))
    }
    
    private fun replaceArrayElement(original: JsonArray, index: Int, replacement: JsonElement) =
        original.copy(elements = original.elements.toMutableList().also { it[index] = replacement }.toList())
    
    fun remove(root: JsonElement): JsonElement {
        val lastIndex = steps.size - 1
        
        return map(root, lastIndex, { input -> removeElement(root, lastIndex, input, steps[lastIndex]) })
    }
    
    private fun removeElement(root: JsonElement, i: Int, parent: JsonElement, pathBit: Any): JsonElement =
        if (pathBit is String) {
            removeObjectProperty(jsonObjectWithProperty(root, i, parent, pathBit), pathBit)
        }
        else if (pathBit is Int) {
            removeArrayElement(jsonArrayWithIndex(root, i, parent, pathBit), pathBit)
        }
        else {
            throw IllegalArgumentException("unexpected path element: " + pathBitsToString(steps, i))
        }
    
    private fun map(root: JsonElement, pathLength: Int, f: (JsonElement) -> JsonElement): JsonElement {
        val parents = elementsOnPath(root, pathLength)
        
        return (pathLength - 1 downTo 0).fold(f(parents.last())) { acc, i ->
            replaceElement(root, parents[i], i, acc)
        }
    }
    
    private fun elementsOnPath(root: JsonElement, pathLength: Int): List<JsonElement> {
        return mutableListOf<JsonElement>(root).apply {
            (0 until pathLength).forEach { i ->
                add(applyPathElement(root, i, get(i)))
            }
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
    
    private fun removeArrayElement(original: JsonArray, indexToRemove: Int) =
        original.copy(elements = original.elements.drop(indexToRemove))
    
    private fun removeObjectProperty(original: JsonObject, nameToRemove: String) =
        original.copy(properties = original.properties - nameToRemove)
    
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

