package io.quarkus.opentelemetry.runtime;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.quarkus.arc.DefaultBean;

@Singleton
public class OpenTelemetryProducer {
    @Produces
    @Singleton
    @DefaultBean
    public OpenTelemetry getOpenTelemetry() {
        return GlobalOpenTelemetry.get();
    }
}
