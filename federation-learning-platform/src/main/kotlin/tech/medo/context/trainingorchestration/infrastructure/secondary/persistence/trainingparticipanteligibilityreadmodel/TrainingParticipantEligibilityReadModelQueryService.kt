package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingparticipanteligibilityreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Root
import org.hibernate.query.criteria.JpaExpression
import tech.jhipster.service.QueryService
import tech.jhipster.service.filter.RangeFilter
import java.util.function.Function
import java.util.UUID;
import java.math.BigDecimal;

import tech.medo.trainingorchestration.trainingparticipanteligibility.TrainingParticipantEligibilityReadModel
import tech.medo.trainingorchestration.trainingparticipanteligibility.TrainingParticipantEligibilityReadModelCriteria
import tech.medo.trainingorchestration.trainingparticipanteligibility.TrainingParticipantEligibilityReadModelProjection
import tech.medo.trainingorchestration.trainingparticipanteligibility.toReadModel

@Service
class TrainingParticipantEligibilityReadModelQueryService(
    private val repository: SpringDataTrainingParticipantEligibilityReadModelRepository,
    private val objectMapper: com.fasterxml.jackson.databind.ObjectMapper
) : QueryService<TrainingParticipantEligibilityReadModelEntity>() {
    fun findByCriteria(criteria: TrainingParticipantEligibilityReadModelCriteria?, pageable: Pageable): Page<TrainingParticipantEligibilityReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: TrainingParticipantEligibilityReadModelCriteria?): Specification<TrainingParticipantEligibilityReadModelEntity> {
        var specification = Specification.where<TrainingParticipantEligibilityReadModelEntity>(null)
        if (criteria != null) {
            criteria.trainingJobId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("trainingJobId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.federationId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("federationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("organizationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.featureSchemaId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("featureSchemaId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.federationName?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<String>> { root -> root.get("federationName") })) }
            criteria.organizationName?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<String>> { root -> root.get("organizationName") })) }
            criteria.featureDomain?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<String>> { root -> root.get("featureDomain") })) }
            criteria.featureSchemaVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<String>> { root -> root.get("featureSchemaVersion") })) }
            criteria.participantStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<String>> { root -> root.get("participantStatus") })) }
            criteria.readinessStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<String>> { root -> root.get("readinessStatus") })) }
            criteria.readinessStage?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<String>> { root -> root.get("readinessStage") })) }
            criteria.eligibilityScore?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<Int>> { root -> root.get("eligibilityScore") })) }
            criteria.runtimeIdentityActive?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<Boolean>> { root -> root.get("runtimeIdentityActive") })) }
            criteria.runtimeCapabilitySatisfied?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<Boolean>> { root -> root.get("runtimeCapabilitySatisfied") })) }
            criteria.runtimeConnectionEstablished?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<Boolean>> { root -> root.get("runtimeConnectionEstablished") })) }
            criteria.runtimeHealthy?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<Boolean>> { root -> root.get("runtimeHealthy") })) }
            criteria.datasetId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("datasetId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.datasetName?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<String>> { root -> root.get("datasetName") })) }
            criteria.datasetReady?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<Boolean>> { root -> root.get("datasetReady") })) }
            criteria.datasetReadinessStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<String>> { root -> root.get("datasetReadinessStatus") })) }
            criteria.matchedDatasetMetadataReady?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<Boolean>> { root -> root.get("matchedDatasetMetadataReady") })) }
            criteria.datasetAccessValidated?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<Boolean>> { root -> root.get("datasetAccessValidated") })) }
            criteria.datasetApprovedForTraining?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<Boolean>> { root -> root.get("datasetApprovedForTraining") })) }
            criteria.schemaCompatible?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<Boolean>> { root -> root.get("schemaCompatible") })) }
            criteria.labelCompatible?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<Boolean>> { root -> root.get("labelCompatible") })) }
            criteria.qualityScore?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<BigDecimal>> { root -> root.get("qualityScore") })) }
            criteria.securityReady?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<Boolean>> { root -> root.get("securityReady") })) }
            criteria.eligible?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<Boolean>> { root -> root.get("eligible") })) }
            criteria.eligibleRuntimeCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<Int>> { root -> root.get("eligibleRuntimeCount") })) }
            criteria.minimumNodesPerRound?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<Int>> { root -> root.get("minimumNodesPerRound") })) }
            criteria.selectionReady?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<Boolean>> { root -> root.get("selectionReady") })) }
            criteria.eligibilityReason?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<String>> { root -> root.get("eligibilityReason") })) }
            criteria.nextRequiredAction?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<String>> { root -> root.get("nextRequiredAction") })) }
        }
        return specification
    }

    private fun <X : Comparable<in X>> buildExpressionRangeSpecification(
        filter: RangeFilter<X>,
        field: Function<Root<TrainingParticipantEligibilityReadModelEntity>, Expression<X>>
    ): Specification<TrainingParticipantEligibilityReadModelEntity> =
        Specification { root, _, builder ->
            val expression = field.apply(root)
            val predicates = mutableListOf<jakarta.persistence.criteria.Predicate>()
            filter.getEquals()?.let { predicates.add(builder.equal(expression, it)) }
            filter.getNotEquals()?.let { predicates.add(builder.notEqual(expression, it)) }
            filter.getSpecified()?.let { predicates.add(if (it) builder.isNotNull(expression) else builder.isNull(expression)) }
            filter.getIn()?.takeIf { it.isNotEmpty() }?.let { predicates.add(expression.`in`(it)) }
            filter.getNotIn()?.takeIf { it.isNotEmpty() }?.let { predicates.add(builder.not(expression.`in`(it))) }
            filter.getGreaterThan()?.let { predicates.add(builder.greaterThan(expression, it)) }
            filter.getGreaterThanOrEqual()?.let { predicates.add(builder.greaterThanOrEqualTo(expression, it)) }
            filter.getLessThan()?.let { predicates.add(builder.lessThan(expression, it)) }
            filter.getLessThanOrEqual()?.let { predicates.add(builder.lessThanOrEqualTo(expression, it)) }
            builder.and(*predicates.toTypedArray())
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
}
