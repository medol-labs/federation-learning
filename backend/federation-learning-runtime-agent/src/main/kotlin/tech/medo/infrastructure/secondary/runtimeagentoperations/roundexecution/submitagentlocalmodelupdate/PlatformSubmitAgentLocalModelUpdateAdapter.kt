package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.submitagentlocalmodelupdate

import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.startroundexecution.LocalRuntimeEngineProperties
import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateInput
import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateResult
import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateService
import java.nio.file.Files
import java.nio.file.Path

@Component
class PlatformSubmitAgentLocalModelUpdateAdapter(
    private val client: PlatformModelUpdateSubmissionClient,
    private val properties: LocalModelUpdateSubmissionProperties,
    private val runtimeEngineProperties: LocalRuntimeEngineProperties,
    private val fileUploadClient: SupportFileUploadClient
) : SubmitAgentLocalModelUpdateService {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun supports(input: SubmitAgentLocalModelUpdateInput): Boolean = properties.enabled

    override fun execute(input: SubmitAgentLocalModelUpdateInput): SubmitAgentLocalModelUpdateResult {
        val localArtifactPath = toReadablePath(input.artifactRef)
        require(Files.isRegularFile(localArtifactPath)) {
            "Local model update artifact is not readable: $localArtifactPath"
        }
        val uploadedFile = fileUploadClient.upload(input.updateArtifactId, localArtifactPath)
        val request = SubmitModelUpdateSubmissionRequest(
            modelUpdateSubmissionId = input.modelUpdateSubmissionId,
            executionSessionId = input.executionSessionId,
            executionPlanId = input.executionPlanId,
            trainingJobId = input.trainingJobId,
            trainingRunConfigurationId = input.trainingRunConfigurationId,
            roundId = input.roundId,
            roundExecutionId = input.roundExecutionId,
            runtimeId = input.runtimeId,
            featureSchemaId = input.featureSchemaId,
            secureAggregationRequired = input.secureAggregationRequired,
            secureAggregationSessionId = input.secureAggregationSessionId,
            encryptionScheme = input.encryptionScheme,
            publicKeyVersion = input.publicKeyVersion,
            localModelId = input.localModelId,
            updateArtifactId = input.updateArtifactId,
            artifactRef = uploadedFile.artifactUri,
            artifactDigest = uploadedFile.digest,
            updateProtectionType = input.updateProtectionType,
            trainingLoss = input.trainingLoss
        )

        return try {
            log.info(
                "Submitting local model update to platform. trainingJobId={}, roundId={}, roundExecutionId={}, runtimeId={}, secureAggregationRequired={}, updateProtectionType={}, artifactRef={}, artifactDigest={}",
                request.trainingJobId,
                request.roundId,
                request.roundExecutionId,
                request.runtimeId,
                request.secureAggregationRequired,
                request.updateProtectionType,
                request.artifactRef,
                request.artifactDigest
            )
            client.submitModelUpdateSubmission(request)
            SubmitAgentLocalModelUpdateResult.Succeeded()
        } catch (ex: FeignException) {
            val body = ex.contentUTF8().takeIf { it.isNotBlank() } ?: ex.message
            throw IllegalStateException("Platform model update submission failed with status ${ex.status()}: $body", ex)
        } catch (ex: Exception) {
            throw IllegalStateException("Platform model update submission unavailable: ${ex.message ?: ex.javaClass.name}", ex)
        }
    }

    private fun toReadablePath(artifactRef: String): Path {
        val normalizedRef = artifactRef.removePrefix("file://")
        val runtimeRoot = runtimeEngineProperties.runtimeRoot.trimEnd('/')
        val runtimeRootHostRoot = runtimeEngineProperties.runtimeRootHostRoot.trimEnd('/')
        return if (runtimeRoot.isNotBlank() && runtimeRootHostRoot.isNotBlank() && normalizedRef.startsWith("$runtimeRoot/")) {
            Path.of(runtimeRootHostRoot + normalizedRef.removePrefix(runtimeRoot))
        } else {
            Path.of(normalizedRef)
        }
    }

}
