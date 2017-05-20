package com.natpryce.snodge.demo

interface EventFormat {
    fun serialise(event: ServiceEvent): String
    fun deserialise(input: String): ServiceEvent
}