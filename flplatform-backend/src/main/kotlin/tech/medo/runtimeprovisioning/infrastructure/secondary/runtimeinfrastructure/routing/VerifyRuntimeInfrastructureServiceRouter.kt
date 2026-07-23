package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.routing

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
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().verify(input)
            } catch (ex: Exception) {
                RuntimeInfrastructureVerification.Unavailable(
                    failureReason = ex.message ?: "VerifyRuntimeInfrastructureService is unavailable."
                )
            }
            0 -> RuntimeInfrastructureVerification.Unavailable(
                failureReason = "No VerifyRuntimeInfrastructureService adapter supports the requested input."
            )
            else -> RuntimeInfrastructureVerification.Unavailable(
                failureReason = "Multiple VerifyRuntimeInfrastructureService adapters support the requested input."
            )
        }
    }
}
