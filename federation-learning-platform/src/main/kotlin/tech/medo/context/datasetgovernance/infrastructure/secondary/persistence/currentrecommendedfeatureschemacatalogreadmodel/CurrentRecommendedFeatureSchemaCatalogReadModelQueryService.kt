package tech.medo.datasetgovernance.infrastructure.secondary.persistence.currentrecommendedfeatureschemacatalogreadmodel

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
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.datasetgovernance.currentrecommendedfeatureschemacatalog.CurrentRecommendedFeatureSchemaCatalogReadModel
import tech.medo.datasetgovernance.currentrecommendedfeatureschemacatalog.CurrentRecommendedFeatureSchemaCatalogReadModelCriteria
import tech.medo.datasetgovernance.currentrecommendedfeatureschemacatalog.CurrentRecommendedFeatureSchemaCatalogReadModelProjection
import tech.medo.datasetgovernance.currentrecommendedfeatureschemacatalog.toReadModel

@Service
class CurrentRecommendedFeatureSchemaCatalogReadModelQueryService(
    private val repository: SpringDataCurrentRecommendedFeatureSchemaCatalogReadModelRepository
) : QueryService<CurrentRecommendedFeatureSchemaCatalogReadModelEntity>() {
    fun findByCriteria(criteria: CurrentRecommendedFeatureSchemaCatalogReadModelCriteria?, pageable: Pageable): Page<CurrentRecommendedFeatureSchemaCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: CurrentRecommendedFeatureSchemaCatalogReadModelCriteria?): Specification<CurrentRecommendedFeatureSchemaCatalogReadModelEntity> {
        var specification = Specification.where<CurrentRecommendedFeatureSchemaCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.featureDomain?.let { specification = specification.and(buildSpecification(it, Function<Root<CurrentRecommendedFeatureSchemaCatalogReadModelEntity>, Expression<String>> { root -> root.get("featureDomain") })) }
            criteria.recommendedFeatureSchemaId?.let { specification = specification.and(buildSpecification(it, Function<Root<CurrentRecommendedFeatureSchemaCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("recommendedFeatureSchemaId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.recommendedVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<CurrentRecommendedFeatureSchemaCatalogReadModelEntity>, Expression<String>> { root -> root.get("recommendedVersion") })) }
            criteria.recommendedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<CurrentRecommendedFeatureSchemaCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("recommendedAt") })) }
            criteria.recommendationNote?.let { specification = specification.and(buildSpecification(it, Function<Root<CurrentRecommendedFeatureSchemaCatalogReadModelEntity>, Expression<String>> { root -> root.get("recommendationNote") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<CurrentRecommendedFeatureSchemaCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<CurrentRecommendedFeatureSchemaCatalogReadModelEntity> =
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
        field: Function<Root<CurrentRecommendedFeatureSchemaCatalogReadModelEntity>, Expression<X>>
    ): Specification<CurrentRecommendedFeatureSchemaCatalogReadModelEntity> =
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

    private fun CurrentRecommendedFeatureSchemaCatalogReadModelEntity.toProjection(): CurrentRecommendedFeatureSchemaCatalogReadModelProjection =
        CurrentRecommendedFeatureSchemaCatalogReadModelProjection().also {
            it.featureDomain = this@toProjection.featureDomain
            it.recommendedFeatureSchemaId = this@toProjection.recommendedFeatureSchemaId
            it.recommendedVersion = this@toProjection.recommendedVersion
            it.recommendedAt = this@toProjection.recommendedAt
            it.recommendationNote = this@toProjection.recommendationNote
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
