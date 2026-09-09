package tech.medo.infrastructure.secondary.runtimeagentoperations.dataset.declaredataset

import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetInput
import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetResult
import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetService

@Component
class PlatformDeclareDatasetAdapter(
    private val client: PlatformFeatureSchemaCatalogClient,
    private val properties: RuntimeFeatureSchemaLookupProperties
) : DeclareDatasetService {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun supports(input: DeclareDatasetInput): Boolean = properties.enabled

    override fun execute(input: DeclareDatasetInput): DeclareDatasetResult {
        val schema = try {
            logger.debug(
                "Loading feature schema for dataset declaration. datasetId={}, featureSchemaId={}",
                input.datasetId,
                input.featureSchemaId
            )
            client.findFeatureSchema(input.featureSchemaId)
        } catch (ex: FeignException.NotFound) {
            throw IllegalStateException("Feature schema was not found: ${input.featureSchemaId}", ex)
        } catch (ex: FeignException) {
            throw IllegalStateException(
                "Feature schema lookup failed with status ${ex.status()}: ${ex.message}",
                ex
            )
        } catch (ex: Exception) {
            throw IllegalStateException(
                "Feature schema lookup unavailable: ${ex.message ?: ex.javaClass.name}",
                ex
            )
        }

        if (schema.featureSchemaId != null && schema.featureSchemaId != input.featureSchemaId) {
            throw IllegalStateException(
                "Feature schema lookup returned ${schema.featureSchemaId}, expected ${input.featureSchemaId}."
            )
        }
        if (schema.features.isEmpty()) {
            throw IllegalStateException("Feature schema ${input.featureSchemaId} has no features.")
        }

        return DeclareDatasetResult.Succeeded(
            features = schema.features.map { it.toRuntimeFeatureDefinition() },
            labels = schema.labels.map { it.toRuntimeLabelDefinition() }
        )
    }
}
