package tech.medo.infrastructure.secondary.secureaggregation.secureaggregationsession.completehomomorphicaggregationsession

import com.fasterxml.jackson.databind.ObjectMapper
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionInput
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionResult
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionService

@Component
class LocalCompleteHomomorphicAggregationSessionAdapter(
    private val objectMapper: ObjectMapper,
    private val storageRoot: Path = Path.of(
        System.getenv("FL_GLOBAL_MODEL_STORAGE_ROOT")
            ?: "/tmp/medo/federation-learning/global-models"
    )
) : CompleteHomomorphicAggregationSessionService {
    private val log = LoggerFactory.getLogger(LocalCompleteHomomorphicAggregationSessionAdapter::class.java)

    override fun supports(input: CompleteHomomorphicAggregationSessionInput): Boolean = true

    override fun execute(
        input: CompleteHomomorphicAggregationSessionInput
    ): CompleteHomomorphicAggregationSessionResult {
        val artifactDir = storageRoot
            .resolve(input.trainingJobId.toString())
            .resolve(input.roundId.toString())
        Files.createDirectories(artifactDir)

        val artifactPath = artifactDir.resolve("${input.aggregatedModelId}.global_model.json")
        val artifact = mapOf(
            "secureAggregationSessionId" to input.secureAggregationSessionId.toString(),
            "trainingJobId" to input.trainingJobId.toString(),
            "trainingRunConfigurationId" to input.trainingRunConfigurationId.toString(),
            "featureSchemaId" to input.featureSchemaId.toString(),
            "roundId" to input.roundId.toString(),
            "roundNumber" to input.roundNumber,
            "aggregatedModelId" to input.aggregatedModelId.toString(),
            "aggregationMode" to "LOCAL_DEV_PLACEHOLDER"
        )

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(artifactPath.toFile(), artifact)
        val digest = sha256(artifactPath)
        val artifactUri = artifactPath.toAbsolutePath().toUri().toString()

        log.info(
            "Completed local homomorphic aggregation. secureAggregationSessionId={}, roundId={}, artifactUri={}, digest={}",
            input.secureAggregationSessionId,
            input.roundId,
            artifactUri,
            digest
        )

        return CompleteHomomorphicAggregationSessionResult.Succeeded(
            aggregatedModelArtifactUri = artifactUri,
            aggregatedModelRegistryRef = "local://federation-learning/global-models",
            modelFormat = "JSON",
            modelArtifactDigest = digest,
            aggregatedModelSignatureUri = null
        )
    }

    private fun sha256(path: Path): String {
        val digest = MessageDigest.getInstance("SHA-256")
        Files.newInputStream(path).use { input ->
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            while (true) {
                val read = input.read(buffer)
                if (read < 0) {
                    break
                }
                digest.update(buffer, 0, read)
            }
        }
        return digest.digest().joinToString("") { "%02x".format(it) }
    }

    private companion object {
        private const val DEFAULT_BUFFER_SIZE = 8192
    }
}
