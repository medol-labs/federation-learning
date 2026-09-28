package tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AggregationRuntimePropertiesTest {
    @Test
    fun defaultsToEmbeddedAggregationWithoutRuntimeEngineEndpoint() {
        val properties = AggregationRuntimeProperties()

        assertTrue(properties.usesEmbedded())
        assertEquals("", properties.runtimeEngineEndpoint)
    }

    @Test
    fun requiresRuntimeEngineEndpointForRuntimeEngineMode() {
        val error = assertThrows(IllegalArgumentException::class.java) {
            AggregationRuntimeProperties(mode = "runtime-engine").requireRuntimeEngineEndpoint()
        }

        assertEquals(
            "federation-learning.aggregation.runtime-engine-endpoint is required when FL_AGGREGATION_MODE=runtime-engine.",
            error.message
        )
    }

    @Test
    fun rejectsFrontendGatewayRootAsRuntimeEngineEndpoint() {
        val error = assertThrows(IllegalArgumentException::class.java) {
            AggregationRuntimeProperties(
                mode = "runtime-engine",
                runtimeEngineEndpoint = "http://localhost:30080"
            ).requireRuntimeEngineEndpoint()
        }

        assertEquals(
            "federation-learning.aggregation.runtime-engine-endpoint points to a frontend gateway root. " +
                "Use the runtime engine API endpoint, or set FL_AGGREGATION_MODE=embedded for dev/k3d.",
            error.message
        )
    }
}
