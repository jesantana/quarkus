package io.quarkus.opentelemetry.runtime;

import java.util.function.Supplier;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.context.ContextStorage;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.OpenTelemetrySdkBuilder;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.quarkus.opentelemetry.runtime.config.OpenTelemetryConfig;
import io.quarkus.runtime.RuntimeValue;
import io.quarkus.runtime.annotations.Recorder;
import io.vertx.core.Vertx;

@Recorder
public class OpenTelemetryRecorder {

    public static final String OPEN_TELEMETRY_DRIVER = "io.opentelemetry.instrumentation.jdbc.OpenTelemetryDriver";

    /* STATIC INIT */
    public void resetGlobalOpenTelemetryForDevMode() {
        GlobalOpenTelemetry.resetForTest();
    }

    /* STATIC INIT */
    public RuntimeValue<OpenTelemetry> createOpenTelemetry(RuntimeValue<SdkTracerProvider> tracerProvider,
            OpenTelemetryConfig openTelemetryConfig) {
        OpenTelemetrySdkBuilder builder = OpenTelemetrySdk.builder();

        // Set tracer provider if present
        if (tracerProvider != null) {
            builder.setTracerProvider(tracerProvider.getValue());
        }

        builder.setPropagators(OpenTelemetryUtil.mapPropagators(openTelemetryConfig.propagators));

        OpenTelemetry openTelemetry = builder.buildAndRegisterGlobal();
        return new RuntimeValue<>(openTelemetry);
    }

    /* STATIC INIT */
    public void eagerlyCreateContextStorage() {
        ContextStorage.get();
    }

    /* RUNTIME INIT */
    public void storeVertxOnContextStorage(Supplier<Vertx> vertx) {
        QuarkusContextStorage.vertx = vertx.get();
    }

}
