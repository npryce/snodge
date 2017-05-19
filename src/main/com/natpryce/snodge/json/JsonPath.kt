package com.natpryce.snodge.json

data class JsonPath(
    private val steps: List<Any>

) : (com.google.gson.JsonElement) -> com.google.gson.JsonElement {
    
    
    override fun toString(): String {
        return com.natpryce.snodge.json.JsonPath.Companion.pathBitsToString(steps, steps.size)
    }
    
    fun size(): Int {
        return steps.size
    }
    
    val isRoot: Boolean
        get() = size() == 0
    
    fun at(n: Int) = steps[(steps.size + n) % steps.size]
    
    fun extend(vararg morePath: Any) = com.natpryce.snodge.json.JsonPath(steps + morePath.toList())
    
    override fun invoke(json: com.google.gson.JsonElement): com.google.gson.JsonElement {
        var result = json
        
        for (i in steps.indices) {
            result = applyPathElement(json, i, result)
        }
        
        return result
    }
    
    private fun applyPathElement(root: com.google.gson.JsonElement, i: Int, parent: com.google.gson.JsonElement): com.google.gson.JsonElement {
        val pathBit = steps[i]
        
        if (pathBit is String) {
            return jsonObjectWithProperty(root, i, parent, pathBit).get(pathBit)
        }
        else if (pathBit is Int) {
            return jsonArrayWithIndex(root, i, parent, pathBit).get(pathBit)
        }
        else {
            throw IllegalArgumentException("unexpected path element: " + com.natpryce.snodge.json.JsonPath.Companion.pathBitsToString(steps, i))
        }
    }
    
    fun endsWith(vararg suffix: Any) =
        steps.size >= suffix.size
            && suffix.toList() == steps.subList(size() - suffix.size, size())
    
    fun startsWith(prefix: com.natpryce.snodge.json.JsonPath): Boolean {
        return size() >= prefix.size() && steps.subList(0, prefix.size()) == prefix.steps
    }
    
    fun map(json: com.google.gson.JsonElement, f: (com.google.gson.JsonElement) -> com.google.gson.JsonElement) =
        map(json, steps.size, f)
    
    fun replace(root: com.google.gson.JsonElement, replacement: com.google.gson.JsonElement) =
        map(root) { replacement }
    
    private fun replaceElement(root: com.google.gson.JsonElement, parent: com.google.gson.JsonElement, i: Int, replacement: com.google.gson.JsonElement): com.google.gson.JsonElement {
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
            throw IllegalArgumentException("unexpected path element: " + com.natpryce.snodge.json.JsonPath.Companion.pathBitsToString(steps, i))
        }
    }
    
    private fun replaceObjectPropertyValue(original: com.google.gson.JsonObject, memberName: String, replacement: com.google.gson.JsonElement): com.google.gson.JsonElement {
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
    
    private fun replaceArrayElement(original: com.google.gson.JsonArray, index: Int, replacement: com.google.gson.JsonElement): com.google.gson.JsonElement {
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
    
    fun remove(root: com.google.gson.JsonElement): com.google.gson.JsonElement {
        val lastIndex = steps.size - 1
        
        return map(root, lastIndex, { input -> removeElement(root, lastIndex, input, steps[lastIndex]) })
    }
    
    private fun removeElement(root: com.google.gson.JsonElement, i: Int, parent: com.google.gson.JsonElement, pathBit: Any): com.google.gson.JsonElement {
        if (pathBit is String) {
            val original = jsonObjectWithProperty(root, i, parent, pathBit)
            return removeObjectProperty(original, pathBit)
            
        }
        else if (pathBit is Int) {
            val original = jsonArrayWithIndex(root, i, parent, pathBit)
            return removeArrayElement(original, pathBit)
            
        }
        else {
            throw IllegalArgumentException("unexpected path element: " + com.natpryce.snodge.json.JsonPath.Companion.pathBitsToString(steps, i))
        }
    }
    
    private fun map(json: com.google.gson.JsonElement, pathLength: Int, f: (com.google.gson.JsonElement) -> com.google.gson.JsonElement): com.google.gson.JsonElement {
        val parents = arrayOfNulls<com.google.gson.JsonElement>(pathLength + 1)
        parents[0] = json
        
        for (i in 0..pathLength - 1) {
            parents[i + 1] = applyPathElement(json, i, parents[i]!!)
        }
        
        var replaced: com.google.gson.JsonElement = f.invoke(parents[pathLength]!!)
        
        for (i in pathLength - 1 downTo 0) {
            replaced = replaceElement(json, parents[i]!!, i, replaced)
        }
        
        return replaced
    }
    
    private fun jsonObjectWithProperty(root: com.google.gson.JsonElement, i: Int, parent: com.google.gson.JsonElement, memberName: String): com.google.gson.JsonObject {
        com.natpryce.snodge.json.JsonPath.Companion.check(parent.isJsonObject, "expected object", steps, i, root)
        val original = parent.asJsonObject
        com.natpryce.snodge.json.JsonPath.Companion.check(original.has(memberName), "no such member", steps, i, root)
        return original
    }
    
    private fun jsonArrayWithIndex(root: com.google.gson.JsonElement, i: Int, parent: com.google.gson.JsonElement, index: Int): com.google.gson.JsonArray {
        com.natpryce.snodge.json.JsonPath.Companion.check(parent.isJsonArray, "expected array", steps, i, root)
        val array = parent.asJsonArray
        com.natpryce.snodge.json.JsonPath.Companion.check(array.size() > index, "index out of bounds", steps, i, root)
        return array
    }
    
    object functions {
        @JvmStatic
        fun endsWith(vararg suffix: Any): (com.natpryce.snodge.json.JsonPath)->Boolean {
            return object : (com.natpryce.snodge.json.JsonPath)->Boolean {
                override fun invoke(path: com.natpryce.snodge.json.JsonPath): Boolean {
                    return path.endsWith(*suffix)
                }
                
                override fun toString(): String {
                    return "endsWith(..." + com.natpryce.snodge.json.JsonPath.Companion.of(*suffix) + ")"
                }
            }
        }
    
        @JvmStatic
        fun startsWith(vararg prefix: Any): (com.natpryce.snodge.json.JsonPath)->Boolean {
            return com.natpryce.snodge.json.JsonPath.functions.startsWith(JsonPath.of(*prefix))
        }
    
        @JvmStatic
        fun startsWith(prefix: com.natpryce.snodge.json.JsonPath): (com.natpryce.snodge.json.JsonPath)->Boolean {
            return object : (com.natpryce.snodge.json.JsonPath)->Boolean {
                override fun invoke(path: com.natpryce.snodge.json.JsonPath): Boolean {
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
        val root = com.natpryce.snodge.json.JsonPath(emptyList())
        
        @JvmStatic
        fun of(vararg path: Any): com.natpryce.snodge.json.JsonPath {
            return com.natpryce.snodge.json.JsonPath(path.toList())
        }
        
        @JvmStatic
        private fun check(isOk: Boolean, what: String, pathBits: List<Any>, badOne: Int, json: com.google.gson.JsonElement) {
            if (!isOk) {
                throw IllegalArgumentException(what + " at " + com.natpryce.snodge.json.JsonPath.Companion.pathBitsToString(pathBits, badOne + 1) + " in " + json)
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
