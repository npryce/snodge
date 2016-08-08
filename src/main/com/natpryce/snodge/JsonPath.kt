package com.natpryce.snodge

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.natpryce.snodge.internal.JsonFunctions
import java.util.function.Predicate

data class JsonPath(
    private val steps: List<Any>

) : (JsonElement) -> JsonElement {
    
    
    override fun toString(): String {
        return pathBitsToString(steps, steps.size)
    }
    
    fun size(): Int {
        return steps.size
    }
    
    val isRoot: Boolean
        get() = size() == 0
    
    fun at(n: Int) = steps[(steps.size + n) % steps.size]
    
    fun extend(vararg morePath: Any) = JsonPath(steps + morePath.toList())
    
    override fun invoke(json: JsonElement): JsonElement {
        var result = json
        
        for (i in steps.indices) {
            result = applyPathElement(json, i, result)
        }
        
        return result
    }
    
    private fun applyPathElement(root: JsonElement, i: Int, parent: JsonElement): JsonElement {
        val pathBit = steps[i]
        
        if (pathBit is String) {
            return jsonObjectWithProperty(root, i, parent, pathBit).get(pathBit)
        }
        else if (pathBit is Int) {
            return jsonArrayWithIndex(root, i, parent, pathBit).get(pathBit)
        }
        else {
            throw IllegalArgumentException("unexpected path element: " + pathBitsToString(steps, i))
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
    
    fun map(f: (JsonElement) -> JsonElement) = object : DocumentMutation {
        override fun invoke(p1: JsonElement) = map(p1, f)
    }
    
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
        val replaced = JsonObject()
        
        for ((key, value) in original.entrySet()) {
            if (key == memberName) {
                replaced.add(memberName, replacement)
            }
            else {
                replaced.add(key, value)
            }
        }
        
        return replaced
    }
    
    private fun replaceArrayElement(original: JsonArray, index: Int, replacement: JsonElement): JsonElement {
        val replaced = JsonArray()
        
        for (j in 0..index - 1) {
            replaced.add(original.get(j))
        }
        replaced.add(replacement)
        for (j in index + 1..original.size() - 1) {
            replaced.add(original.get(j))
        }
        
        return replaced
    }
    
    fun remove(): DocumentMutation {
        return object : DocumentMutation {
            override fun invoke(p1: JsonElement) = remove(p1)
        }
    }
    
    fun remove(root: JsonElement): JsonElement {
        val lastIndex = steps.size - 1
        
        return map(root, lastIndex, { input -> removeElement(root, lastIndex, input, steps[lastIndex]) })
    }
    
    private fun removeElement(root: JsonElement, i: Int, parent: JsonElement, pathBit: Any): JsonElement {
        if (pathBit is String) {
            val original = jsonObjectWithProperty(root, i, parent, pathBit)
            return JsonFunctions.removeObjectProperty(original, pathBit)
            
        }
        else if (pathBit is Int) {
            val original = jsonArrayWithIndex(root, i, parent, pathBit)
            return JsonFunctions.removeArrayElement(original, pathBit)
            
        }
        else {
            throw IllegalArgumentException("unexpected path element: " + pathBitsToString(steps, i))
        }
    }
    
    private fun map(json: JsonElement, pathLength: Int, f: (JsonElement) -> JsonElement): JsonElement {
        val parents = arrayOfNulls<JsonElement>(pathLength + 1)
        parents[0] = json
        
        for (i in 0..pathLength - 1) {
            parents[i + 1] = applyPathElement(json, i, parents[i]!!)
        }
        
        var replaced: JsonElement = f.invoke(parents[pathLength]!!)
        
        for (i in pathLength - 1 downTo 0) {
            replaced = replaceElement(json, parents[i]!!, i, replaced)
        }
        
        return replaced
    }
    
    private fun jsonObjectWithProperty(root: JsonElement, i: Int, parent: JsonElement, memberName: String): JsonObject {
        check(parent.isJsonObject, "expected object", steps, i, root)
        val original = parent.asJsonObject
        check(original.has(memberName), "no such member", steps, i, root)
        return original
    }
    
    private fun jsonArrayWithIndex(root: JsonElement, i: Int, parent: JsonElement, index: Int): JsonArray {
        check(parent.isJsonArray, "expected array", steps, i, root)
        val array = parent.asJsonArray
        check(array.size() > index, "index out of bounds", steps, i, root)
        return array
    }
    
    object functions {
        @JvmStatic
        fun endsWith(vararg suffix: Any): Predicate<JsonPath> {
            return object : Predicate<JsonPath> {
                override fun test(path: JsonPath): Boolean {
                    return path.endsWith(*suffix)
                }
                
                override fun toString(): String {
                    return "endsWith(..." + JsonPath.of(*suffix) + ")"
                }
            }
        }
    
        @JvmStatic
        fun startsWith(vararg prefix: Any): Predicate<JsonPath> {
            return startsWith(JsonPath.of(*prefix))
        }
    
        @JvmStatic
        fun startsWith(prefix: JsonPath): Predicate<JsonPath> {
            return object : Predicate<JsonPath> {
                override fun test(path: JsonPath): Boolean {
                    return path.startsWith(prefix)
                }
                
                override fun toString(): String {
                    return "startsWith($prefix)"
                }
            }
        }
    }
    
    companion object {
        @JvmField
        val root = JsonPath(emptyList())
        
        @JvmStatic
        fun of(vararg path: Any): JsonPath {
            return JsonPath(path.toList())
        }
        
        @JvmStatic
        private fun check(isOk: Boolean, what: String, pathBits: List<Any>, badOne: Int, json: JsonElement) {
            if (!isOk) {
                throw IllegalArgumentException(what + " at " + pathBitsToString(pathBits, badOne + 1) + " in " + json)
            }
        }
        
        @JvmStatic
        private fun pathBitsToString(pathBits: List<Any>, count: Int): String {
            return pathBits.subList(0, count)
                .map { it.toString() }
                .joinToString(prefix = "/", separator = "/", postfix = "")
        }
    }
}
