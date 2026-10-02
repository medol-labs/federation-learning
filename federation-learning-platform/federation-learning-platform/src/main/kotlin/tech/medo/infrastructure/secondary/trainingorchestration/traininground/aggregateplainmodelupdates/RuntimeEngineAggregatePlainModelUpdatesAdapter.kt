package tech.medo.infrastructure.secondary.trainingorchestration.traininground.aggregateplainmodelupdates

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregatedModelFileUploadClient
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregationRuntimeEngineClient
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregationRuntimeJobRequest
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregationRuntimeProperties
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.EmbeddedAggregationService
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesInput
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesResult
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesService
import java.time.Instant

@Component
class RuntimeEngineAggregatePlainModelUpdatesAdapter(
    private val runtimeEngineClient: AggregationRuntimeEngineClient,
    private val fileUploadClient: AggregatedModelFileUploadClient,
    private val embeddedAggregationService: EmbeddedAggregationService,
    private val properties: AggregationRuntimeProperties
) : AggregatePlainModelUpdatesService {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun supports(input: AggregatePlainModelUpdatesInput): Boolean = properties.enabled

    override fun execute(input: AggregatePlainModelUpdatesInput): AggregatePlainModelUpdatesResult {
        require(input.modelUpdateArtifactRefs.isNotEmpty()) {
            "At least one plaintext model update artifact is required for aggregation."
        }
        if (properties.usesEmbedded()) {
            val aggregated = embeddedAggregationService.aggregate(
                aggregatedModelId = input.aggregatedModelId,
                trainingJobId = input.trainingJobId,
                roundNumber = input.roundNumber,
                artifactRefs = input.modelUpdateArtifactRefs,
                aggregationAlgorithm = input.aggregationAlgorithm,
                secureAggregation = false
            )
            return AggregatePlainModelUpdatesResult.Succeeded(
                aggregatedModelName = "training-${input.trainingJobId}",
                aggregatedModelVersion = "round-${input.roundNumber}",
                aggregatedModelDescription =
                    "Federated global model produced by training job ${input.trainingJobId}, round ${input.roundNumber}.",
                modelSourceType = "FEDERATED_TRAINING",
                aggregatedModelArtifactUri = aggregated.artifactUri,
                aggregatedModelRegistryRef = aggregated.registryRef,
                modelFormat = modelFormat(input.aggregationAlgorithm),
                modelArtifactDigest = aggregated.digest,
                aggregatedModelSignatureUri = null,
                aggregatedModelSizeBytes = aggregated.sizeBytes
            )
        }

        val jobId = "aggregate-plain-${input.trainingJobId}-round-${input.roundNumber}"
        val runtimeEngineEndpoint = properties.requireRuntimeEngineEndpoint()
        log.info(
            "Submitting plaintext aggregation runtime job. jobId={}, roundId={}, updateCount={}, algorithm={}",
            jobId,
            input.roundId,
            input.modelUpdateArtifactRefs.size,
            input.aggregationAlgorithm
        )
        runtimeEngineClient.submit(
            runtimeEngineEndpoint,
            AggregationRuntimeJobRequest(
                jobId = jobId,
                roundId = input.roundNumber,
                nodeName = properties.nodeName,
                role = "aggregator",
                operation = "aggregate",
                input = mapOf("updates" to input.modelUpdateArtifactRefs),
                modelParameter = mapOf(
                    "aggregationAlgorithm" to input.aggregationAlgorithm,
                    "engine" to "python"
                )
            )
        )
        awaitCompletion(jobId, runtimeEngineEndpoint)

        val artifactName = aggregatedArtifactOutputName(input.aggregationAlgorithm)
        val modelBytes = runtimeEngineClient.downloadArtifact(
            runtimeEngineEndpoint,
            jobId,
            artifactName
        )
        val fileName = aggregatedArtifactFileName(input.aggregatedModelId.toString(), input.aggregationAlgorithm)
        val uploadedFile = fileUploadClient.upload(
            supportEndpoint = properties.supportEndpoint,
            internalToken = properties.internalToken,
            fileId = input.aggregatedModelId,
            fileName = fileName,
            contentType = aggregatedArtifactContentType(input.aggregationAlgorithm),
            content = modelBytes
        )

        log.info(
            "Stored plaintext aggregated model. jobId={}, aggregatedModelId={}, artifactUri={}, digest={}, sizeBytes={}",
            jobId,
            input.aggregatedModelId,
            uploadedFile.artifactUri,
            uploadedFile.digest,
            uploadedFile.sizeBytes
        )
        return AggregatePlainModelUpdatesResult.Succeeded(
            aggregatedModelName = "training-${input.trainingJobId}",
            aggregatedModelVersion = "round-${input.roundNumber}",
            aggregatedModelDescription =
                "Federated global model produced by training job ${input.trainingJobId}, round ${input.roundNumber}.",
            modelSourceType = "FEDERATED_TRAINING",
            aggregatedModelArtifactUri = uploadedFile.artifactUri,
            aggregatedModelRegistryRef = uploadedFile.registryRef,
            modelFormat = modelFormat(input.aggregationAlgorithm),
            modelArtifactDigest = uploadedFile.digest,
            aggregatedModelSignatureUri = null,
            aggregatedModelSizeBytes = uploadedFile.sizeBytes?.let(Math::toIntExact)
        )
    }

    private fun awaitCompletion(jobId: String, runtimeEngineEndpoint: String) {
        val deadline = Instant.now().plus(properties.jobTimeout)
        while (Instant.now().isBefore(deadline)) {
            val job = runtimeEngineClient.getJob(runtimeEngineEndpoint, jobId)
            when (job.status.trim().uppercase()) {
                "COMPLETED", "SUCCEEDED", "SUCCESS" -> return
                "FAILED", "ERROR", "CANCELLED", "CANCELED" ->
                    error("Plain aggregation runtime job $jobId failed with exitCode=${job.exitCode}.")
            }
            Thread.sleep(properties.pollInterval.toMillis().coerceAtLeast(100))
        }
        error("Plain aggregation runtime job $jobId did not complete within ${properties.jobTimeout}.")
    }

    private fun modelFormat(aggregationAlgorithm: String): String =
        if (aggregationAlgorithm.contains("PYTORCH", ignoreCase = true)) {
            "PYTORCH_STATE_DICT"
        } else {
            "JSON"
        }

    private fun aggregatedArtifactOutputName(aggregationAlgorithm: String): String =
        if (isPytorchStateDictAggregation(aggregationAlgorithm)) {
            WEIGHT_ARTIFACT_OUTPUT
        } else {
            GLOBAL_MODEL_OUTPUT
        }

    private fun aggregatedArtifactFileName(modelId: String, aggregationAlgorithm: String): String =
        if (isPytorchStateDictAggregation(aggregationAlgorithm)) {
            "$modelId.global_model_state_dict.pt"
        } else {
            "$modelId.global_model.json"
        }

    private fun aggregatedArtifactContentType(aggregationAlgorithm: String): String =
        if (isPytorchStateDictAggregation(aggregationAlgorithm)) {
            "application/octet-stream"
        } else {
            "application/json"
        }

    private fun isPytorchStateDictAggregation(aggregationAlgorithm: String): Boolean =
        aggregationAlgorithm.contains("PYTORCH", ignoreCase = true) ||
            aggregationAlgorithm.contains("TORCH", ignoreCase = true)

    private companion object {
        private const val GLOBAL_MODEL_OUTPUT = "globalModel"
        private const val WEIGHT_ARTIFACT_OUTPUT = "weightArtifact"
    }
}
