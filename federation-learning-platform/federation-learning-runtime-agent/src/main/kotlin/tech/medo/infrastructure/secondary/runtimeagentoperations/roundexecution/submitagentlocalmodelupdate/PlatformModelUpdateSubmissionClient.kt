package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.submitagentlocalmodelupdate

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import tech.medo.shared.security.MedolFeignSecurityConfiguration
import java.math.BigDecimal
import java.util.UUID

@FeignClient(
    name = "federationLearningPlatformModelUpdateSubmissionClient",
    url = "\${runtime-agent.local-model-update-submission.platform-url:http://localhost:8080}",
    configuration = [MedolFeignSecurityConfiguration::class]
)
interface PlatformModelUpdateSubmissionClient {
    @PostMapping("/traininground/submitmodelupdatesubmission")
    fun submitModelUpdateSubmission(
        @RequestBody request: SubmitModelUpdateSubmissionRequest
    ): SubmitModelUpdateSubmissionResponse
}

data class SubmitModelUpdateSubmissionRequest(
    val modelUpdateSubmissionId: UUID,
    val executionSessionId: UUID,
    val executionPlanId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val roundId: UUID,
    val roundExecutionId: UUID,
    val runtimeId: UUID,
    val featureSchemaId: UUID,
    val secureAggregationRequired: Boolean,
    val secureAggregationSessionId: UUID?,
    val encryptionScheme: String?,
    val publicKeyVersion: String?,
    val localModelId: UUID,
    val updateArtifactId: UUID,
    val artifactRef: String,
    val artifactDigest: String,
    val updateProtectionType: String,
    val trainingLoss: BigDecimal
)

data class SubmitModelUpdateSubmissionResponse(
    val modelUpdateSubmissionId: UUID? = null
)
