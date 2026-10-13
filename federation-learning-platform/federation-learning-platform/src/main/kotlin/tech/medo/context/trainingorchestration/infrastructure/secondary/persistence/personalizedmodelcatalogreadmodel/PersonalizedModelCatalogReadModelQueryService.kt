package tech.medo.trainingorchestration.infrastructure.secondary.persistence.personalizedmodelcatalogreadmodel

import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Root
import org.hibernate.query.criteria.JpaExpression
import tech.jhipster.service.QueryService
import tech.jhipster.service.filter.RangeFilter
import java.util.function.Function
import java.time.LocalDateTime
import java.util.UUID
import java.math.BigDecimal
import tech.medo.trainingorchestration.domain.states.PersonalizedModelResultStateEnum
import tech.medo.trainingorchestration.personalizedmodelcatalog.PersonalizedModelCatalogReadModelCriteria
import tech.medo.trainingorchestration.personalizedmodelcatalog.PersonalizedModelCatalogReadModelProjection

@Service
class PersonalizedModelCatalogReadModelQueryService() : QueryService<PersonalizedModelCatalogReadModelEntity>() {
    fun createSpecification(criteria: PersonalizedModelCatalogReadModelCriteria?): Specification<PersonalizedModelCatalogReadModelEntity> {
        var specification = Specification.where<PersonalizedModelCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.personalizedModelId?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("personalizedModelId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.modelUpdateSubmissionId?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("modelUpdateSubmissionId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.trainingJobId?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("trainingJobId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.trainingRunConfigurationId?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("trainingRunConfigurationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.roundId?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("roundId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.roundNumber?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<Int>> { root -> root.get("roundNumber") })) }
            criteria.runtimeId?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.featureSchemaId?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("featureSchemaId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.personalizationMode?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> root.get("personalizationMode") })) }
            criteria.personalizationScope?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> root.get("personalizationScope") })) }
            criteria.personalizedArtifactPolicy?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> root.get("personalizedArtifactPolicy") })) }
            criteria.personalizationMetric?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> root.get("personalizationMetric") })) }
            criteria.minimumPersonalizationLift?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<BigDecimal>> { root -> root.get("minimumPersonalizationLift") })) }
            criteria.personalizationTargetType?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> root.get("personalizationTargetType") })) }
            criteria.personalizationTargetId?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("personalizationTargetId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.personalizationCohortId?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("personalizationCohortId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.personalizationProfileId?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("personalizationProfileId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.personalizedModelArtifactRef?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> root.get("personalizedModelArtifactRef") })) }
            criteria.personalizedModelArtifactDigest?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> root.get("personalizedModelArtifactDigest") })) }
            criteria.personalizedModelStorageLocation?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> root.get("personalizedModelStorageLocation") })) }
            criteria.platformManagedArtifactRef?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> root.get("platformManagedArtifactRef") })) }
            criteria.personalizedMetricsArtifactRef?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<String>> { root -> root.get("personalizedMetricsArtifactRef") })) }
            criteria.personalizedAccuracy?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<BigDecimal>> { root -> root.get("personalizedAccuracy") })) }
            criteria.personalizationLift?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<BigDecimal>> { root -> root.get("personalizationLift") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<PersonalizedModelResultStateEnum>> { root -> root.get("state") })) }
            criteria.projectionUpdatedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("projectionUpdatedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<PersonalizedModelCatalogReadModelEntity> =
        Specification { root, _, builder ->
            val expression = field.apply(root)
            val predicates = mutableListOf<jakarta.persistence.criteria.Predicate>()
            filter.getEquals()?.let { predicates.add(builder.equal(expression, localDateTimeValue(it))) }
            filter.getNotEquals()?.let { predicates.add(builder.notEqual(expression, localDateTimeValue(it))) }
            filter.getSpecified()?.let { predicates.add(if (it) builder.isNotNull(expression) else builder.isNull(expression)) }
            (filter.getIn() as List<*>?)?.takeIf { it.isNotEmpty() }?.let { predicates.add(expression.`in`(it.map { value -> localDateTimeValue(value) })) }
            (filter.getNotIn() as List<*>?)?.takeIf { it.isNotEmpty() }?.let { predicates.add(builder.not(expression.`in`(it.map { value -> localDateTimeValue(value) }))) }
            filter.getGreaterThan()?.let { predicates.add(builder.greaterThan(expression, localDateTimeValue(it))) }
            filter.getGreaterThanOrEqual()?.let { predicates.add(builder.greaterThanOrEqualTo(expression, localDateTimeValue(it))) }
            filter.getLessThan()?.let { predicates.add(builder.lessThan(expression, localDateTimeValue(it))) }
            filter.getLessThanOrEqual()?.let { predicates.add(builder.lessThanOrEqualTo(expression, localDateTimeValue(it))) }
            builder.and(*predicates.toTypedArray())
        }

    private fun localDateTimeValue(value: Any?): LocalDateTime =
        when (value) {
            is LocalDateTime -> value
            null -> throw IllegalArgumentException("LocalDateTime filter value is required.")
            else -> value.toString().let { raw ->
                if (raw.all { it.isDigit() }) {
                    java.time.Instant.ofEpochMilli(raw.toLong()).atZone(java.time.ZoneId.systemDefault()).toLocalDateTime()
                } else {
                    LocalDateTime.parse(raw)
                }
            }
        }

    private fun <X : Comparable<in X>> buildExpressionRangeSpecification(
        filter: RangeFilter<X>,
        field: Function<Root<PersonalizedModelCatalogReadModelEntity>, Expression<X>>
    ): Specification<PersonalizedModelCatalogReadModelEntity> =
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

    private fun PersonalizedModelCatalogReadModelEntity.toProjection(): PersonalizedModelCatalogReadModelProjection =
        PersonalizedModelCatalogReadModelProjection().also {
            it.personalizedModelId = this@toProjection.personalizedModelId
            it.modelUpdateSubmissionId = this@toProjection.modelUpdateSubmissionId
            it.trainingJobId = this@toProjection.trainingJobId
            it.trainingRunConfigurationId = this@toProjection.trainingRunConfigurationId
            it.roundId = this@toProjection.roundId
            it.roundNumber = this@toProjection.roundNumber
            it.runtimeId = this@toProjection.runtimeId
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.personalizationMode = this@toProjection.personalizationMode
            it.personalizationScope = this@toProjection.personalizationScope
            it.personalizedArtifactPolicy = this@toProjection.personalizedArtifactPolicy
            it.personalizationMetric = this@toProjection.personalizationMetric
            it.minimumPersonalizationLift = this@toProjection.minimumPersonalizationLift
            it.personalizationTargetType = this@toProjection.personalizationTargetType
            it.personalizationTargetId = this@toProjection.personalizationTargetId
            it.personalizationCohortId = this@toProjection.personalizationCohortId
            it.personalizationProfileId = this@toProjection.personalizationProfileId
            it.personalizedModelArtifactRef = this@toProjection.personalizedModelArtifactRef
            it.personalizedModelArtifactDigest = this@toProjection.personalizedModelArtifactDigest
            it.personalizedModelStorageLocation = this@toProjection.personalizedModelStorageLocation
            it.platformManagedArtifactRef = this@toProjection.platformManagedArtifactRef
            it.personalizedMetricsArtifactRef = this@toProjection.personalizedMetricsArtifactRef
            it.personalizedAccuracy = this@toProjection.personalizedAccuracy
            it.personalizationLift = this@toProjection.personalizationLift
            it.state = this@toProjection.state
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
