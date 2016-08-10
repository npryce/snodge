package com.natpryce.snodge.demo

import java.net.URI

enum class ServiceState {
    STARTING, READY, BUSY, POWERSAVE, STOPPING, STOPPED
}

data class ServiceEvent(
    val timestamp: Long,
    val service: URI,
    val serviceState: ServiceState
)
