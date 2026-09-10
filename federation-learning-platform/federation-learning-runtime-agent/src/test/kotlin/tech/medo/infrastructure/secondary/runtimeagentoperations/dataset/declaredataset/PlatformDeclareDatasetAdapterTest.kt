package tech.medo.infrastructure.secondary.runtimeagentoperations.dataset.declaredataset

import feign.FeignException
import feign.Request
import feign.RequestTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetInput
import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetResult
import java.nio.charset.StandardCharsets
import java.util.UUID

class PlatformDeclareDatasetAdapterTest {
    @Test
    fun loadsFeatureSchemaSnapshotForDatasetDeclaration() {
        val client = FakeFeatureSchemaCatalogClient(
            response = PlatformFeatureSchemaCatalogResponse(
                featureSchemaId = FEATURE_SCHEMA_ID,
                features = listOf(
                    PlatformFeatureDefinition(
                        featureName = "age",
                        dataType = "INTEGER",
                        required = true,
                        nullable = false,
                        description = "Patient age",
                        validationRules = listOf("min:0"),
                        defaultValue = null,
                        isIdentifier = false,
                        isSensitive = true,
                        encodingStrategy = "STANDARDIZE",
                        featureTags = listOf("demographic")
                    )
                ),
                labels = listOf(
                    PlatformLabelDefinition(
                        labelName = "risk",
                        dataType = "STRING",
                        cardinality = 2,
                        classLabels = listOf("low", "high"),
                        isMultilabel = false,
                        description = "Risk label",
                        validationRules = emptyList(),
                        defaultValue = null
                    )
                )
            )
        )
        val adapter = PlatformDeclareDatasetAdapter(client, properties())

        val result = adapter.execute(input())

        assertTrue(result is DeclareDatasetResult.Succeeded)
        result as DeclareDatasetResult.Succeeded
        assertEquals("age", result.features.single().featureName)
        assertEquals("risk", result.labels.single().labelName)
        assertEquals(FEATURE_SCHEMA_ID, client.requestedId)
    }

    @Test
    fun disabledAdapterDoesNotSupportInput() {
        val adapter = PlatformDeclareDatasetAdapter(
            FakeFeatureSchemaCatalogClient(response = featureSchema()),
            properties(enabled = false)
        )

        assertFalse(adapter.supports(input()))
    }

    @Test
    fun throwsWhenFeatureSchemaCannotBeFound() {
        val adapter = PlatformDeclareDatasetAdapter(
            FakeFeatureSchemaCatalogClient(exception = notFound()),
            properties()
        )

        val thrown = assertThrows(IllegalStateException::class.java) {
            adapter.execute(input())
        }

        assertTrue(thrown.message!!.contains("Feature schema was not found"))
    }

    @Test
    fun throwsWhenFeatureSchemaHasNoFeatures() {
        val adapter = PlatformDeclareDatasetAdapter(
            FakeFeatureSchemaCatalogClient(response = featureSchema(features = emptyList())),
            properties()
        )

        val thrown = assertThrows(IllegalStateException::class.java) {
            adapter.execute(input())
        }

        assertTrue(thrown.message!!.contains("has no features"))
    }

    private fun input(): DeclareDatasetInput =
        DeclareDatasetInput(
            datasetId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            organizationId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
            organizationName = "Test Organization",
            featureSchemaId = FEATURE_SCHEMA_ID,
            featureDomain = "credit-risk",
            featureSchemaVersion = "v1",
            datasetName = "credit-risk",
            datasetUsage = "TRAINING"
        )

    private fun properties(enabled: Boolean = true): RuntimeFeatureSchemaLookupProperties =
        RuntimeFeatureSchemaLookupProperties(
            enabled = enabled,
            platformUrl = "http://platform"
        )

    private fun featureSchema(
        features: List<PlatformFeatureDefinition> = listOf(PlatformFeatureDefinition(featureName = "age"))
    ): PlatformFeatureSchemaCatalogResponse =
        PlatformFeatureSchemaCatalogResponse(
            featureSchemaId = FEATURE_SCHEMA_ID,
            features = features
        )

    private fun notFound(): FeignException =
        FeignException.errorStatus(
            "FeatureSchemaCatalogClient#findFeatureSchema(UUID)",
            feign.Response.builder()
                .status(404)
                .reason("Not Found")
                .request(
                    Request.create(
                        Request.HttpMethod.GET,
                        "/featureschema/featureschemacatalog/$FEATURE_SCHEMA_ID",
                        emptyMap(),
                        null,
                        StandardCharsets.UTF_8,
                        RequestTemplate()
                    )
                )
                .build()
        )

    private class FakeFeatureSchemaCatalogClient(
        private val response: PlatformFeatureSchemaCatalogResponse? = null,
        private val exception: RuntimeException? = null
    ) : PlatformFeatureSchemaCatalogClient {
        var requestedId: UUID? = null

        override fun findFeatureSchema(id: UUID): PlatformFeatureSchemaCatalogResponse {
            requestedId = id
            exception?.let { throw it }
            return requireNotNull(response)
        }
    }

    private companion object {
        val FEATURE_SCHEMA_ID: UUID = UUID.fromString("33333333-3333-4333-8333-333333333333")
    }
}
