package com.natpryce.snodge.form

typealias Form = List<Pair<String, String>>
fun form(vararg fields: Pair<String, String>): Form = fields.toList()

operator fun Form.get(key: String): List<String> =
    filter { it.first == key }.map { it.second }

val Form.keys get() = map { it.first }.toSet()
