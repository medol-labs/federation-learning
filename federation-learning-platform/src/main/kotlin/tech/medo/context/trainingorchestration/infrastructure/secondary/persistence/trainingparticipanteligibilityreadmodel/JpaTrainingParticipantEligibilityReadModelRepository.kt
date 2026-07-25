package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingparticipanteligibilityreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

import java.util.UUID;

import tech.medo.trainingorchestration.trainingparticipanteligibility.TrainingParticipantEligibilityReadModel
import tech.medo.trainingorchestration.trainingparticipanteligibility.TrainingParticipantEligibilityReadModelProjection
import tech.medo.trainingorchestration.trainingparticipanteligibility.TrainingParticipantEligibilityReadModelRepository
import tech.medo.trainingorchestration.trainingparticipanteligibility.toReadModel

@Repository
class JpaTrainingParticipantEligibilityReadModelRepository(private val jpaRepository: SpringDataTrainingParticipantEligibilityReadModelRepository, private val objectMapper: ObjectMapper) : TrainingParticipantEligibilityReadModelRepository {
    override fun findAll(pageable: Pageable): Page<TrainingParticipantEligibilityReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): TrainingParticipantEligibilityReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): TrainingParticipantEligibilityReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: TrainingParticipantEligibilityReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun TrainingParticipantEligibilityReadModelEntity.toProjection(): TrainingParticipantEligibilityReadModelProjection =
        TrainingParticipantEligibilityReadModelProjection().also {
            it.trainingJobId = this@toProjection.trainingJobId
            it.federationId = this@toProjection.federationId
            it.organizationId = this@toProjection.organizationId
            it.runtimeId = this@toProjection.runtimeId
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.federationName = this@toProjection.federationName
            it.organizationName = this@toProjection.organizationName
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.participantStatus = this@toProjection.participantStatus
            it.readinessStatus = this@toProjection.readinessStatus
            it.readinessStage = this@toProjection.readinessStage
            it.eligibilityScore = this@toProjection.eligibilityScore
            it.runtimeIdentityActive = this@toProjection.runtimeIdentityActive
            it.runtimeCapabilitySatisfied = this@toProjection.runtimeCapabilitySatisfied
            it.runtimeConnectionEstablished = this@toProjection.runtimeConnectionEstablished
            it.runtimeHealthy = this@toProjection.runtimeHealthy
            it.datasetId = this@toProjection.datasetId
            it.datasetName = this@toProjection.datasetName
            it.datasetReady = this@toProjection.datasetReady
            it.datasetReadinessStatus = this@toProjection.datasetReadinessStatus
            it.matchedDatasetMetadataReady = this@toProjection.matchedDatasetMetadataReady
            it.datasetAccessValidated = this@toProjection.datasetAccessValidated
            it.datasetApprovedForTraining = this@toProjection.datasetApprovedForTraining
            it.schemaCompatible = this@toProjection.schemaCompatible
            it.labelCompatible = this@toProjection.labelCompatible
            it.qualityScore = this@toProjection.qualityScore
            it.securityReady = this@toProjection.securityReady
            it.eligible = this@toProjection.eligible
            it.eligibleRuntimeCount = this@toProjection.eligibleRuntimeCount
            it.minimumNodesPerRound = this@toProjection.minimumNodesPerRound
            it.selectionReady = this@toProjection.selectionReady
            it.eligibilityReason = this@toProjection.eligibilityReason
            it.ineligibleReasons = this@toProjection.ineligibleReasons?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) } ?: emptyList()
            it.warningReasons = this@toProjection.warningReasons?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) } ?: emptyList()
            it.nextRequiredAction = this@toProjection.nextRequiredAction
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun TrainingParticipantEligibilityReadModelProjection.toEntity(): TrainingParticipantEligibilityReadModelEntity =
        TrainingParticipantEligibilityReadModelEntity().also {
            it.trainingJobId = this@toEntity.trainingJobId
            it.federationId = this@toEntity.federationId
            it.organizationId = this@toEntity.organizationId
            it.runtimeId = this@toEntity.runtimeId
            it.featureSchemaId = this@toEntity.featureSchemaId
            it.federationName = this@toEntity.federationName
            it.organizationName = this@toEntity.organizationName
            it.featureDomain = this@toEntity.featureDomain
            it.featureSchemaVersion = this@toEntity.featureSchemaVersion
            it.participantStatus = this@toEntity.participantStatus
            it.readinessStatus = this@toEntity.readinessStatus
            it.readinessStage = this@toEntity.readinessStage
            it.eligibilityScore = this@toEntity.eligibilityScore
            it.runtimeIdentityActive = this@toEntity.runtimeIdentityActive
            it.runtimeCapabilitySatisfied = this@toEntity.runtimeCapabilitySatisfied
            it.runtimeConnectionEstablished = this@toEntity.runtimeConnectionEstablished
            it.runtimeHealthy = this@toEntity.runtimeHealthy
            it.datasetId = this@toEntity.datasetId
            it.datasetName = this@toEntity.datasetName
            it.datasetReady = this@toEntity.datasetReady
            it.datasetReadinessStatus = this@toEntity.datasetReadinessStatus
            it.matchedDatasetMetadataReady = this@toEntity.matchedDatasetMetadataReady
            it.datasetAccessValidated = this@toEntity.datasetAccessValidated
            it.datasetApprovedForTraining = this@toEntity.datasetApprovedForTraining
            it.schemaCompatible = this@toEntity.schemaCompatible
            it.labelCompatible = this@toEntity.labelCompatible
            it.qualityScore = this@toEntity.qualityScore
            it.securityReady = this@toEntity.securityReady
            it.eligible = this@toEntity.eligible
            it.eligibleRuntimeCount = this@toEntity.eligibleRuntimeCount
            it.minimumNodesPerRound = this@toEntity.minimumNodesPerRound
            it.selectionReady = this@toEntity.selectionReady
            it.eligibilityReason = this@toEntity.eligibilityReason
            it.ineligibleReasons = objectMapper.writeValueAsString(this@toEntity.ineligibleReasons)
            it.warningReasons = objectMapper.writeValueAsString(this@toEntity.warningReasons)
            it.nextRequiredAction = this@toEntity.nextRequiredAction
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}
