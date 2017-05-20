package com.natpryce.snodge.demo

import java.net.URI

sealed class ServiceState
object STARTING: ServiceState()
object READY: ServiceState()
object STOPPING: ServiceState()
object STOPPED: ServiceState()
data class FAILED(val error: String): ServiceState()


data class ServiceEvent(
    val timestamp: Long,
    val service: URI,
    val serviceState: ServiceState
)
