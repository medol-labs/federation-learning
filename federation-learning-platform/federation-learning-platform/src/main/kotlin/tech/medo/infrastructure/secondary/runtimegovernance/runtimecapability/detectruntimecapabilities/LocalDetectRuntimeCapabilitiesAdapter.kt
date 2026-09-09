package tech.medo.infrastructure.secondary.runtimegovernance.runtimecapability.detectruntimecapabilities

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesInput
import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesResult
import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesService

@Component
class LocalDetectRuntimeCapabilitiesAdapter : DetectRuntimeCapabilitiesService {
    private val log = LoggerFactory.getLogger(LocalDetectRuntimeCapabilitiesAdapter::class.java)

    override fun supports(input: DetectRuntimeCapabilitiesInput): Boolean = true

    override fun execute(input: DetectRuntimeCapabilitiesInput): DetectRuntimeCapabilitiesResult {
        log.info(
            "Detected runtime capabilities locally. runtimeId={}, capabilityTypes={}",
            input.runtimeId,
            input.capabilityTypes
        )
        return DetectRuntimeCapabilitiesResult.Succeeded()
    }
}
