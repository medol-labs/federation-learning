package tech.medo.runtimeprovisioning.infrastructure.secondary.runtimeinfrastructure.routing

import tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification.RetryRuntimeInfrastructureVerificationInput
import tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification.RetryRuntimeInfrastructureVerificationService
import tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification.RetryRuntimeInfrastructureVerificationResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class RetryRuntimeInfrastructureVerificationServiceRouter(private val adapters: ObjectProvider<RetryRuntimeInfrastructureVerificationService>) : RetryRuntimeInfrastructureVerificationService {
    override fun supports(input: RetryRuntimeInfrastructureVerificationInput): Boolean = true

    override fun execute(input: RetryRuntimeInfrastructureVerificationInput): RetryRuntimeInfrastructureVerificationResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                RetryRuntimeInfrastructureVerificationResult.Unavailable(
                    failureReason = ex.message ?: "RetryRuntimeInfrastructureVerificationService is unavailable."
                )
            }
            0 -> RetryRuntimeInfrastructureVerificationResult.Unavailable(
                failureReason = "No RetryRuntimeInfrastructureVerificationService adapter supports the requested input."
            )
            else -> RetryRuntimeInfrastructureVerificationResult.Unavailable(
                failureReason = "Multiple RetryRuntimeInfrastructureVerificationService adapters support the requested input."
            )
        }
    }
}
