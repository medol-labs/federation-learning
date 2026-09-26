package tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionInput
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionResult
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionService
import java.time.Instant

@Component
class RuntimeEngineCompleteHomomorphicAggregationSessionAdapter(
    private val runtimeEngineClient: AggregationRuntimeEngineClient,
    private val fileUploadClient: AggregatedModelFileUploadClient,
    private val embeddedAggregationService: EmbeddedAggregationService,
    private val properties: AggregationRuntimeProperties
) : CompleteHomomorphicAggregationSessionService {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun supports(input: CompleteHomomorphicAggregationSessionInput): Boolean = properties.enabled

    override fun execute(
        input: CompleteHomomorphicAggregationSessionInput
    ): CompleteHomomorphicAggregationSessionResult {
        require(input.encryptedUpdateArtifactRefs.isNotEmpty()) {
            "At least one managed model update artifact is required for aggregation."
        }
        if (properties.mode.equals("embedded", ignoreCase = true)) {
            val aggregated = embeddedAggregationService.aggregate(
                aggregatedModelId = input.aggregatedModelId,
                trainingJobId = input.trainingJobId,
                roundNumber = input.roundNumber,
                artifactRefs = input.encryptedUpdateArtifactRefs,
                aggregationAlgorithm = properties.aggregationAlgorithm,
                secureAggregation = true
            )
            return CompleteHomomorphicAggregationSessionResult.Succeeded(
                aggregatedModelName = "training-${input.trainingJobId}",
                aggregatedModelVersion = "round-${input.roundNumber}",
                aggregatedModelDescription = "Federated global model produced by training job ${input.trainingJobId}, round ${input.roundNumber}.",
                modelSourceType = "FEDERATED_TRAINING",
                aggregatedModelArtifactUri = aggregated.artifactUri,
                aggregatedModelRegistryRef = aggregated.registryRef,
                modelFormat = modelFormat(properties.aggregationAlgorithm),
                modelArtifactDigest = aggregated.digest,
                aggregatedModelSignatureUri = null,
                aggregatedModelSizeBytes = aggregated.sizeBytes
            )
        }
        val jobId = "aggregate-${input.trainingJobId}-round-${input.roundNumber}"
        val request = AggregationRuntimeJobRequest(
            jobId = jobId,
            roundId = input.roundNumber,
            nodeName = properties.nodeName,
            role = "aggregator",
            operation = "aggregate",
            input = mapOf("updates" to input.encryptedUpdateArtifactRefs),
            modelParameter = mapOf(
                "aggregationAlgorithm" to properties.aggregationAlgorithm,
                "engine" to "python"
            )
        )

        log.info(
            "Submitting aggregation runtime job. jobId={}, roundId={}, updateCount={}, algorithm={}",
            jobId,
            input.roundId,
            input.encryptedUpdateArtifactRefs.size,
            properties.aggregationAlgorithm
        )
        runtimeEngineClient.submit(properties.runtimeEngineEndpoint, request)
        awaitCompletion(jobId)

        val modelBytes = runtimeEngineClient.downloadArtifact(
            properties.runtimeEngineEndpoint,
            jobId,
            GLOBAL_MODEL_OUTPUT
        )
        val fileName = "${input.aggregatedModelId}.global_model.json"
        val uploadedFile = fileUploadClient.upload(
            supportEndpoint = properties.supportEndpoint,
            internalToken = properties.internalToken,
            fileId = input.aggregatedModelId,
            fileName = fileName,
            contentType = "application/json",
            content = modelBytes
        )

        log.info(
            "Stored aggregated global model. jobId={}, aggregatedModelId={}, artifactUri={}, digest={}, sizeBytes={}",
            jobId,
            input.aggregatedModelId,
            uploadedFile.artifactUri,
            uploadedFile.digest,
            uploadedFile.sizeBytes
        )
        return CompleteHomomorphicAggregationSessionResult.Succeeded(
            aggregatedModelName = "training-${input.trainingJobId}",
            aggregatedModelVersion = "round-${input.roundNumber}",
            aggregatedModelDescription = "Federated global model produced by training job ${input.trainingJobId}, round ${input.roundNumber}.",
            modelSourceType = "FEDERATED_TRAINING",
            aggregatedModelArtifactUri = uploadedFile.artifactUri,
            aggregatedModelRegistryRef = uploadedFile.registryRef,
            modelFormat = "JSON",
            modelArtifactDigest = uploadedFile.digest,
            aggregatedModelSignatureUri = null,
            aggregatedModelSizeBytes = uploadedFile.sizeBytes?.let(Math::toIntExact)
        )
    }

    private fun awaitCompletion(jobId: String) {
        val deadline = Instant.now().plus(properties.jobTimeout)
        while (Instant.now().isBefore(deadline)) {
            val job = runtimeEngineClient.getJob(properties.runtimeEngineEndpoint, jobId)
            when (job.status.trim().uppercase()) {
                "COMPLETED", "SUCCEEDED", "SUCCESS" -> return
                "FAILED", "ERROR", "CANCELLED", "CANCELED" -> {
                    error("Aggregation runtime job $jobId failed with exitCode=${job.exitCode}.")
                }
            }
            Thread.sleep(properties.pollInterval.toMillis().coerceAtLeast(100))
        }
        error("Aggregation runtime job $jobId did not complete within ${properties.jobTimeout}.")
    }

    private companion object {
        private const val GLOBAL_MODEL_OUTPUT = "globalModel"
    }

    private fun modelFormat(aggregationAlgorithm: String): String =
        if (aggregationAlgorithm.contains("PYTORCH", ignoreCase = true)) {
            "PYTORCH_STATE_DICT"
        } else {
            "JSON"
        }
}
