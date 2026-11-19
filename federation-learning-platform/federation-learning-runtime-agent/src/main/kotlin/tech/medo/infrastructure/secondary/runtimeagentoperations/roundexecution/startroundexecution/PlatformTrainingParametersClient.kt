package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import tech.medo.shared.security.MedolFeignSecurityConfiguration
import java.util.UUID

@FeignClient(
    name = "executionTrainingParametersClient",
    url = "\${runtime-agent.training-configuration.platform-url:\${runtime-agent.feature-schema-lookup.platform-url:http://localhost:8081}}",
    configuration = [MedolFeignSecurityConfiguration::class]
)
fun interface PlatformTrainingParametersClient {
    @GetMapping("/trainingrunconfiguration/trainingrunconfigurationcatalog/{id}")
    fun get(@PathVariable("id") id: UUID): ExecutionTrainingParameters
}

data class ExecutionTrainingParameters(val localEpochs: Int, val learningRate: Double) {
    init {
        require(localEpochs > 0 && learningRate.isFinite() && learningRate > 0)
    }
}
