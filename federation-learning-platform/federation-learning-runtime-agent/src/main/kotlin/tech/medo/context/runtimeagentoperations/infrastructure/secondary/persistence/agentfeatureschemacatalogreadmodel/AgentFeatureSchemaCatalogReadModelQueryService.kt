package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentfeatureschemacatalogreadmodel

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

import tech.medo.runtimeagentoperations.agentfeatureschemacatalog.AgentFeatureSchemaCatalogReadModel
import tech.medo.runtimeagentoperations.agentfeatureschemacatalog.AgentFeatureSchemaCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.agentfeatureschemacatalog.AgentFeatureSchemaCatalogReadModelProjection
import tech.medo.runtimeagentoperations.agentfeatureschemacatalog.toReadModel

@Service
class AgentFeatureSchemaCatalogReadModelQueryService(
    private val repository: SpringDataAgentFeatureSchemaCatalogReadModelRepository
) : QueryService<AgentFeatureSchemaCatalogReadModelEntity>() {
    fun findByCriteria(criteria: AgentFeatureSchemaCatalogReadModelCriteria?, pageable: Pageable): Page<AgentFeatureSchemaCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: AgentFeatureSchemaCatalogReadModelCriteria?): Specification<AgentFeatureSchemaCatalogReadModelEntity> {
        var specification = Specification.where<AgentFeatureSchemaCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.featureSchemaId?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentFeatureSchemaCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("featureSchemaId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.featureDomain?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentFeatureSchemaCatalogReadModelEntity>, Expression<String>> { root -> root.get("featureDomain") })) }
            criteria.featureSchemaVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentFeatureSchemaCatalogReadModelEntity>, Expression<String>> { root -> root.get("featureSchemaVersion") })) }
            criteria.schemaStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<AgentFeatureSchemaCatalogReadModelEntity>, Expression<String>> { root -> root.get("schemaStatus") })) }
            criteria.syncedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<AgentFeatureSchemaCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("syncedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<AgentFeatureSchemaCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<AgentFeatureSchemaCatalogReadModelEntity> =
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
        field: Function<Root<AgentFeatureSchemaCatalogReadModelEntity>, Expression<X>>
    ): Specification<AgentFeatureSchemaCatalogReadModelEntity> =
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

    private fun AgentFeatureSchemaCatalogReadModelEntity.toProjection(): AgentFeatureSchemaCatalogReadModelProjection =
        AgentFeatureSchemaCatalogReadModelProjection().also {
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.schemaStatus = this@toProjection.schemaStatus
            it.syncedAt = this@toProjection.syncedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
