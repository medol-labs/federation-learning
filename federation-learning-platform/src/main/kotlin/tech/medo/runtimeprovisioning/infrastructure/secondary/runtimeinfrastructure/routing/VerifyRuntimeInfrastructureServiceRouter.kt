package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.routing

import org.slf4j.LoggerFactory
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerificationInput
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.VerifyRuntimeInfrastructureService
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.RuntimeInfrastructureVerification
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class VerifyRuntimeInfrastructureServiceRouter(private val adapters: ObjectProvider<VerifyRuntimeInfrastructureService>) : VerifyRuntimeInfrastructureService {
    override fun supports(input: RuntimeInfrastructureVerificationInput): Boolean = true

    override fun verify(input: RuntimeInfrastructureVerificationInput): RuntimeInfrastructureVerification {
        log.debug(
            "Routing VerifyRuntimeInfrastructure request runtimeInfrastructureId={}, agentInstallMode={}",
            input.runtimeInfrastructureId,
            input.agentInstallMode
        )
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        log.debug(
            "VerifyRuntimeInfrastructure adapter candidates count={}, adapters={}",
            candidates.size,
            candidates.map { it::class.simpleName }
        )
        return when (candidates.size) {
            1 -> try {
                val adapter = candidates.first()
                log.debug("VerifyRuntimeInfrastructure selected adapter={}", adapter::class.simpleName)
                adapter.verify(input).also {
                    log.debug("VerifyRuntimeInfrastructure adapter={} returned result={}", adapter::class.simpleName, it::class.simpleName)
                }
            } catch (ex: Exception) {
                log.warn("VerifyRuntimeInfrastructure adapter failed", ex)
                RuntimeInfrastructureVerification.Unavailable(
                    failureReason = ex.message ?: "VerifyRuntimeInfrastructureService is unavailable."
                )
            }
            0 -> {
                log.debug("No VerifyRuntimeInfrastructure adapter supports runtimeInfrastructureId={}", input.runtimeInfrastructureId)
                RuntimeInfrastructureVerification.Unavailable(
                    failureReason = "No VerifyRuntimeInfrastructureService adapter supports the requested input."
                )
            }
            else -> {
                log.debug("Multiple VerifyRuntimeInfrastructure adapters support runtimeInfrastructureId={}", input.runtimeInfrastructureId)
                RuntimeInfrastructureVerification.Unavailable(
                    failureReason = "Multiple VerifyRuntimeInfrastructureService adapters support the requested input."
                )
            }
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(VerifyRuntimeInfrastructureServiceRouter::class.java)
    }
}
