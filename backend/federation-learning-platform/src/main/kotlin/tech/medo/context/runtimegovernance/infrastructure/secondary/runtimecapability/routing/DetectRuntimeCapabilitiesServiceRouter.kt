package tech.medo.runtimegovernance.infrastructure.secondary.runtimecapability.routing

import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesInput
import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesService
import tech.medo.runtimegovernance.detectruntimecapabilities.DetectRuntimeCapabilitiesResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class DetectRuntimeCapabilitiesServiceRouter(private val adapters: ObjectProvider<DetectRuntimeCapabilitiesService>) : DetectRuntimeCapabilitiesService {
    override fun supports(input: DetectRuntimeCapabilitiesInput): Boolean = true

    override fun execute(input: DetectRuntimeCapabilitiesInput): DetectRuntimeCapabilitiesResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                throw ex
            }
            0 -> error("No DetectRuntimeCapabilitiesService adapter supports the requested input.")
            else -> error("Multiple DetectRuntimeCapabilitiesService adapters support the requested input.")
        }
    }
}
