package com.natpryce.snodge.demo

import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.IOException

/*
 * Defects in this class are corrected in the RobustJsonEventFormat
 */
class ReflectomagicJsonEventFormat : EventFormat {
    val mapper = ObjectMapper().apply {
        registerKotlinModule()
        enableDefaultTypingAsProperty(OBJECT_AND_NON_CONCRETE, "_type")
        disable(FAIL_ON_UNKNOWN_PROPERTIES)
    }
    
    override fun serialise(event: ServiceEvent): String {
        return mapper.writeValueAsString(event)
    }
    
    override fun deserialise(input: String): ServiceEvent {
        return mapper.readValue(input, ServiceEvent::class.java)
            ?: throw IOException("invalid JSON: $input")
    }
}
