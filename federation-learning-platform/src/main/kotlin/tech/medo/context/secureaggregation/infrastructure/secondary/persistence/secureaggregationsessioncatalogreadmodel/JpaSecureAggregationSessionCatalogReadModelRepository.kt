package tech.medo.secureaggregation.infrastructure.secondary.persistence.secureaggregationsessioncatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

import java.util.UUID;
import tech.medo.secureaggregation.domain.states.SecureAggregationSessionStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.secureaggregation.secureaggregationsessioncatalog.SecureAggregationSessionCatalogReadModel
import tech.medo.secureaggregation.secureaggregationsessioncatalog.SecureAggregationSessionCatalogReadModelCriteria
import tech.medo.secureaggregation.secureaggregationsessioncatalog.SecureAggregationSessionCatalogReadModelProjection
import tech.medo.secureaggregation.secureaggregationsessioncatalog.SecureAggregationSessionCatalogReadModelRepository
import tech.medo.secureaggregation.secureaggregationsessioncatalog.toReadModel

@Repository
class JpaSecureAggregationSessionCatalogReadModelRepository(
    private val jpaRepository: SpringDataSecureAggregationSessionCatalogReadModelRepository,
    private val queryService: SecureAggregationSessionCatalogReadModelQueryService,
    private val objectMapper: ObjectMapper
) : SecureAggregationSessionCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<SecureAggregationSessionCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: SecureAggregationSessionCatalogReadModelCriteria?, pageable: Pageable): Page<SecureAggregationSessionCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): SecureAggregationSessionCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): SecureAggregationSessionCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: SecureAggregationSessionCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun SecureAggregationSessionCatalogReadModelEntity.toProjection(): SecureAggregationSessionCatalogReadModelProjection =
        SecureAggregationSessionCatalogReadModelProjection().also {
            it.secureAggregationSessionId = this@toProjection.secureAggregationSessionId
            it.trainingJobId = this@toProjection.trainingJobId
            it.trainingRunConfigurationId = this@toProjection.trainingRunConfigurationId
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.roundId = this@toProjection.roundId
            it.requiredParticipantCount = this@toProjection.requiredParticipantCount
            it.acceptedRuntimeIds = this@toProjection.acceptedRuntimeIds?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<UUID>>() {}) } ?: emptyList()
            it.selectedRuntimeIds = this@toProjection.selectedRuntimeIds?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<UUID>>() {}) } ?: emptyList()
            it.selectedParticipantCount = this@toProjection.selectedParticipantCount
            it.encryptionContextPrepared = this@toProjection.encryptionContextPrepared
            it.receivedEncryptedUpdateCount = this@toProjection.receivedEncryptedUpdateCount
            it.encryptionScheme = this@toProjection.encryptionScheme
            it.publicKeyVersion = this@toProjection.publicKeyVersion
            it.encryptedParameterScale = this@toProjection.encryptedParameterScale
            it.aggregatedModelId = this@toProjection.aggregatedModelId
            it.modelFormat = this@toProjection.modelFormat
            it.modelArtifactDigest = this@toProjection.modelArtifactDigest
            it.state = this@toProjection.state
            it.failureReason = this@toProjection.failureReason
            it.createdAt = this@toProjection.createdAt
            it.selectedAt = this@toProjection.selectedAt
            it.encryptionContextPreparedAt = this@toProjection.encryptionContextPreparedAt
            it.decryptedAt = this@toProjection.decryptedAt
            it.completedAt = this@toProjection.completedAt
            it.failedAt = this@toProjection.failedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun SecureAggregationSessionCatalogReadModelProjection.toEntity(): SecureAggregationSessionCatalogReadModelEntity =
        SecureAggregationSessionCatalogReadModelEntity().also {
            it.secureAggregationSessionId = this@toEntity.secureAggregationSessionId
            it.trainingJobId = this@toEntity.trainingJobId
            it.trainingRunConfigurationId = this@toEntity.trainingRunConfigurationId
            it.featureSchemaId = this@toEntity.featureSchemaId
            it.roundId = this@toEntity.roundId
            it.requiredParticipantCount = this@toEntity.requiredParticipantCount
            it.acceptedRuntimeIds = objectMapper.writeValueAsString(this@toEntity.acceptedRuntimeIds)
            it.selectedRuntimeIds = objectMapper.writeValueAsString(this@toEntity.selectedRuntimeIds)
            it.selectedParticipantCount = this@toEntity.selectedParticipantCount
            it.encryptionContextPrepared = this@toEntity.encryptionContextPrepared
            it.receivedEncryptedUpdateCount = this@toEntity.receivedEncryptedUpdateCount
            it.encryptionScheme = this@toEntity.encryptionScheme
            it.publicKeyVersion = this@toEntity.publicKeyVersion
            it.encryptedParameterScale = this@toEntity.encryptedParameterScale
            it.aggregatedModelId = this@toEntity.aggregatedModelId
            it.modelFormat = this@toEntity.modelFormat
            it.modelArtifactDigest = this@toEntity.modelArtifactDigest
            it.state = this@toEntity.state
            it.failureReason = this@toEntity.failureReason
            it.createdAt = this@toEntity.createdAt
            it.selectedAt = this@toEntity.selectedAt
            it.encryptionContextPreparedAt = this@toEntity.encryptionContextPreparedAt
            it.decryptedAt = this@toEntity.decryptedAt
            it.completedAt = this@toEntity.completedAt
            it.failedAt = this@toEntity.failedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
