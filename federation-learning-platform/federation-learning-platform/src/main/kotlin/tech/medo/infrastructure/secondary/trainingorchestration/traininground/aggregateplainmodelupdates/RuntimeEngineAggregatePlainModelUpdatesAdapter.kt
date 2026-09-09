package tech.medo.infrastructure.secondary.trainingorchestration.traininground.aggregateplainmodelupdates

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregatedModelFileUploadClient
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregationRuntimeEngineClient
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregationRuntimeJobRequest
import tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession.AggregationRuntimeProperties
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesInput
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesResult
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesService
import java.time.Instant

@Component
class RuntimeEngineAggregatePlainModelUpdatesAdapter(
    private val runtimeEngineClient: AggregationRuntimeEngineClient,
    private val fileUploadClient: AggregatedModelFileUploadClient,
    private val properties: AggregationRuntimeProperties
) : AggregatePlainModelUpdatesService {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun supports(input: AggregatePlainModelUpdatesInput): Boolean = properties.enabled

    override fun execute(input: AggregatePlainModelUpdatesInput): AggregatePlainModelUpdatesResult {
        require(input.modelUpdateArtifactRefs.isNotEmpty()) {
            "At least one plaintext model update artifact is required for aggregation."
        }

        val jobId = "aggregate-plain-${input.trainingJobId}-round-${input.roundNumber}"
        log.info(
            "Submitting plaintext aggregation runtime job. jobId={}, roundId={}, updateCount={}, algorithm={}",
            jobId,
            input.roundId,
            input.modelUpdateArtifactRefs.size,
            input.aggregationAlgorithm
        )
        runtimeEngineClient.submit(
            properties.runtimeEngineEndpoint,
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
        awaitCompletion(jobId)

        val modelBytes = runtimeEngineClient.downloadArtifact(
            properties.runtimeEngineEndpoint,
            jobId,
            GLOBAL_MODEL_OUTPUT
        )
        val uploadedFile = fileUploadClient.upload(
            supportEndpoint = properties.supportEndpoint,
            internalToken = properties.internalToken,
            fileId = input.aggregatedModelId,
            fileName = "${input.aggregatedModelId}.global_model.json",
            contentType = "application/json",
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

    private fun awaitCompletion(jobId: String) {
        val deadline = Instant.now().plus(properties.jobTimeout)
        while (Instant.now().isBefore(deadline)) {
            val job = runtimeEngineClient.getJob(properties.runtimeEngineEndpoint, jobId)
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

    private companion object {
        private const val GLOBAL_MODEL_OUTPUT = "globalModel"
    }
}
