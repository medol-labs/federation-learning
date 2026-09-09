package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.acceptexecutionplan

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanInput
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanResult
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModel
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelProjection
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelRepository
import tech.medo.runtimeagentoperations.roundexecutioncatalog.RoundExecutionCatalogReadModel
import tech.medo.runtimeagentoperations.roundexecutioncatalog.RoundExecutionCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.roundexecutioncatalog.RoundExecutionCatalogReadModelProjection
import tech.medo.runtimeagentoperations.roundexecutioncatalog.RoundExecutionCatalogReadModelRepository
import java.time.LocalDateTime
import java.util.UUID

class LocalAcceptExecutionPlanAdapterTest {
    private val runtimeId = UUID.fromString("11111111-1111-4111-8111-111111111111")
    private val organizationId = UUID.fromString("22222222-2222-4222-8222-222222222222")

    @Test
    fun acceptsWhenLocalRequirementsAreSatisfied() {
        val adapter = LocalAcceptExecutionPlanAdapter(
            bindingRepository = bindingRepository(
                binding(runtimeId = runtimeId, organizationId = organizationId)
            ),
            roundExecutionRepository = roundExecutionRepository()
        )

        val result = adapter.execute(input())

        assertTrue(result is AcceptExecutionPlanResult.Succeeded)
        result as AcceptExecutionPlanResult.Succeeded
        assertEquals(true, result.localExecutionRequirementsSatisfied)
        assertEquals(true, result.runtimeIdentityMatched)
        assertEquals(true, result.runtimeDatasetBindingAvailable)
        assertEquals(true, result.datasetAccessValidated)
        assertEquals(true, result.runtimeAgentIdle)
    }

    @Test
    fun rejectsWhenRuntimeDatasetBindingIsMissing() {
        val adapter = LocalAcceptExecutionPlanAdapter(
            bindingRepository = bindingRepository(),
            roundExecutionRepository = roundExecutionRepository()
        )

        val result = adapter.execute(input())

        assertTrue(result is AcceptExecutionPlanResult.Rejected)
        result as AcceptExecutionPlanResult.Rejected
        assertEquals(false, result.localExecutionRequirementsSatisfied)
        assertEquals(false, result.runtimeDatasetBindingAvailable)
        assertTrue(result.rejectionReasons.any { it.contains("No runtime dataset binding") })
    }

    private fun input(): AcceptExecutionPlanInput =
        AcceptExecutionPlanInput(
            executionPlanId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            roundExecutionId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            executionSessionId = UUID.fromString("44444444-4444-4444-8444-444444444444"),
            trainingJobId = UUID.fromString("55555555-5555-4555-8555-555555555555"),
            trainingRunConfigurationId = UUID.fromString("66666666-6666-4666-8666-666666666666"),
            featureSchemaId = UUID.fromString("77777777-7777-4777-8777-777777777777"),
            roundId = UUID.fromString("88888888-8888-4888-8888-888888888888"),
            roundNumber = 1,
            runtimeId = runtimeId,
            organizationId = organizationId,
            baseModelId = UUID.fromString("99999999-9999-4999-8999-999999999999"),
            baseModelArtifactUri = "oci://registry.example.com/fl/model@sha256:abc",
            baseModelRegistryRef = "oci://registry.example.com/fl",
            baseModelFormat = "ONNX",
            baseModelArtifactDigest = "sha256:abc",
            baseModelSignatureUri = "oci://registry.example.com/fl/model.sig",
            secureAggregationRequired = true,
            secureAggregationSessionId = UUID.fromString("aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa"),
            encryptionScheme = "PAILLIER",
            publicKeyVersion = "local-dev-v1",
            publicKeyRef = "local://secure-aggregation/public-keys/local-dev-v1",
            encryptedParameterScale = 1000000
        )

    private fun binding(
        runtimeId: UUID,
        organizationId: UUID
    ): RuntimeDatasetBindingCatalogReadModel =
        RuntimeDatasetBindingCatalogReadModel(
            runtimeDatasetBindingId = UUID.fromString("aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa"),
            datasetId = UUID.fromString("bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb"),
            organizationId = organizationId,
            runtimeId = runtimeId,
            datasetName = "credit-risk",
            dataSourceType = "FILE",
            host = null,
            port = null,
            url = null,
            databaseName = null,
            schemaName = null,
            tableName = null,
            filePath = "/data/credit-risk.csv",
            objectBucket = null,
            objectPrefix = null,
            dataFormat = "CSV",
            credentialSecretName = null,
            configuredAt = LocalDateTime.now(),
            userId = null,
            sessionId = null,
            correlationId = null,
            causationId = null,
            traceId = null,
            tenantId = null
        )

    private fun bindingRepository(
        vararg bindings: RuntimeDatasetBindingCatalogReadModel
    ): RuntimeDatasetBindingCatalogReadModelRepository =
        object : RuntimeDatasetBindingCatalogReadModelRepository {
            override fun findAll(pageable: Pageable): Page<RuntimeDatasetBindingCatalogReadModel> =
                PageImpl(bindings.toList())

            override fun findAllByCriteria(
                criteria: RuntimeDatasetBindingCatalogReadModelCriteria?,
                pageable: Pageable
            ): Page<RuntimeDatasetBindingCatalogReadModel> =
                findAll(pageable)

            override fun findById(id: UUID): RuntimeDatasetBindingCatalogReadModel? = null
            override fun findProjectionById(id: UUID): RuntimeDatasetBindingCatalogReadModelProjection? = null
            override fun save(projection: RuntimeDatasetBindingCatalogReadModelProjection) = Unit
        }

    private fun roundExecutionRepository(
        vararg executions: RoundExecutionCatalogReadModel
    ): RoundExecutionCatalogReadModelRepository =
        object : RoundExecutionCatalogReadModelRepository {
            override fun findAll(pageable: Pageable): Page<RoundExecutionCatalogReadModel> =
                PageImpl(executions.toList())

            override fun findAllByCriteria(
                criteria: RoundExecutionCatalogReadModelCriteria?,
                pageable: Pageable
            ): Page<RoundExecutionCatalogReadModel> =
                findAll(pageable)

            override fun findById(id: UUID): RoundExecutionCatalogReadModel? = null
            override fun findProjectionById(id: UUID): RoundExecutionCatalogReadModelProjection? = null
            override fun save(projection: RoundExecutionCatalogReadModelProjection) = Unit
        }
}
