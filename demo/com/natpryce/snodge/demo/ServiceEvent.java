package com.natpryce.snodge.demo;

import java.net.URI;

public class ServiceEvent {
    public static enum Type {
        STARTING, READY, BUSY, POWERSAVE, STOPPING, STOPPED
    }

    public final long timestamp;
    public final URI service;
    public final Type type;

    public ServiceEvent(long timestamp, URI service, Type eventType) {
        this.timestamp = timestamp;
        this.service = service;
        this.type = eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceEvent that = (ServiceEvent) o;

        if (timestamp != that.timestamp) return false;
        if (!service.equals(that.service)) return false;
        if (type != that.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + service.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
