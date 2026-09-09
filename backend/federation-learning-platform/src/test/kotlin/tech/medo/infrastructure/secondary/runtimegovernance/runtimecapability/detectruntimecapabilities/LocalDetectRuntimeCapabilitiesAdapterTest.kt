package tech.medo.infrastructure.secondary.runtimegovernance.runtimecapability.detectruntimecapabilities

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesInput
import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesResult
import java.util.UUID

class LocalDetectRuntimeCapabilitiesAdapterTest {
    @Test
    fun detectsRuntimeCapabilities() {
        val adapter = LocalDetectRuntimeCapabilitiesAdapter()
        val input = DetectRuntimeCapabilitiesInput(
            runtimeId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            capabilityTypes = listOf("CSV_DATASET", "LOCAL_TRAINING")
        )

        val result = adapter.execute(input)

        assertTrue(adapter.supports(input))
        assertTrue(result is DetectRuntimeCapabilitiesResult.Succeeded)
    }
}
