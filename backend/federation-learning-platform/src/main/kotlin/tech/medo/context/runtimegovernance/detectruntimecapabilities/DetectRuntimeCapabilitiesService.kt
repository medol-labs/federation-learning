package tech.medo.runtimegovernance.detectruntimecapabilities

import java.util.UUID;

interface DetectRuntimeCapabilitiesService {
    fun supports(input: DetectRuntimeCapabilitiesInput): Boolean = true
    fun execute(input: DetectRuntimeCapabilitiesInput): DetectRuntimeCapabilitiesResult
}

data class DetectRuntimeCapabilitiesInput(
    val runtimeId: UUID,
    val capabilityTypes: List<String>
)

sealed interface DetectRuntimeCapabilitiesResult {
    class Succeeded : DetectRuntimeCapabilitiesResult


}
