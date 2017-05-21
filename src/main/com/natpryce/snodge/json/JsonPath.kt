package com.natpryce.snodge.json

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject

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
    
    override fun invoke(root: JsonElement): JsonElement {
        return steps.foldIndexed(root) { i, result, step ->
            applyPathElement(root, i, result)
        }
    }
    
    private fun applyPathElement(root: JsonElement, i: Int, parent: JsonElement): JsonElement {
        val pathBit = steps[i]
        
        return if (pathBit is String) {
            jsonObjectWithProperty(root, i, parent, pathBit).get(pathBit)
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
    
    private fun replaceObjectPropertyValue(original: com.google.gson.JsonObject, memberName: String, replacement: JsonElement): JsonElement {
        val replaced = com.google.gson.JsonObject()
        
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
    
    private fun replaceArrayElement(original: com.google.gson.JsonArray, index: Int, replacement: JsonElement): JsonElement {
        val replaced = com.google.gson.JsonArray()
        
        for (j in 0..index - 1) {
            replaced.add(original.get(j))
        }
        replaced.add(replacement)
        for (j in index + 1..original.size() - 1) {
            replaced.add(original.get(j))
        }
        
        return replaced
    }
    
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
    
    private fun jsonObjectWithProperty(root: JsonElement, i: Int, parent: JsonElement, memberName: String): com.google.gson.JsonObject {
        JsonPath.Companion.check(parent.isJsonObject, "expected object", steps, i, root)
        val original = parent.asJsonObject
        JsonPath.Companion.check(original.has(memberName), "no such member", steps, i, root)
        return original
    }
    
    private fun jsonArrayWithIndex(root: JsonElement, i: Int, parent: JsonElement, index: Int): com.google.gson.JsonArray {
        JsonPath.Companion.check(parent.isJsonArray, "expected array", steps, i, root)
        val array = parent.asJsonArray
        JsonPath.Companion.check(array.size() > index, "index out of bounds", steps, i, root)
        return array
    }
    
    private fun removeArrayElement(original: JsonArray, indexToRemove: Int) =
        JsonArray().apply {
            addAll(original)
            remove(indexToRemove)
        }
    
    private fun removeObjectProperty(original: JsonObject, nameToRemove: String) =
        JsonObject().apply {
            original.entrySet()
                .filter { e -> e.key != nameToRemove }
                .forEach { e -> add(e.key, e.value) }
        }
    
    object functions {
        @JvmStatic
        fun endsWith(vararg suffix: Any): (JsonPath) -> Boolean {
            return object : (JsonPath) -> Boolean {
                override fun invoke(path: JsonPath): Boolean {
                    return path.endsWith(*suffix)
                }
                
                override fun toString(): String {
                    return "endsWith(..." + JsonPath.Companion.of(*suffix) + ")"
                }
            }
        }
        
        @JvmStatic
        fun startsWith(vararg prefix: Any): (JsonPath) -> Boolean {
            return JsonPath.functions.startsWith(JsonPath.of(*prefix))
        }
        
        @JvmStatic
        fun startsWith(prefix: JsonPath): (JsonPath) -> Boolean {
            return object : (JsonPath) -> Boolean {
                override fun invoke(path: JsonPath): Boolean {
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

