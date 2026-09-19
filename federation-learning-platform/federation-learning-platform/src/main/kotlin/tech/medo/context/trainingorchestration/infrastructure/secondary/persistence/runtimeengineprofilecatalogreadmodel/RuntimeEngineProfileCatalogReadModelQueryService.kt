package tech.medo.trainingorchestration.infrastructure.secondary.persistence.runtimeengineprofilecatalogreadmodel

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
import tech.medo.trainingorchestration.domain.states.RuntimeEngineProfileStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.trainingorchestration.runtimeengineprofilecatalog.RuntimeEngineProfileCatalogReadModel
import tech.medo.trainingorchestration.runtimeengineprofilecatalog.RuntimeEngineProfileCatalogReadModelCriteria
import tech.medo.trainingorchestration.runtimeengineprofilecatalog.RuntimeEngineProfileCatalogReadModelProjection
import tech.medo.trainingorchestration.runtimeengineprofilecatalog.toReadModel

@Service
class RuntimeEngineProfileCatalogReadModelQueryService(
    private val repository: SpringDataRuntimeEngineProfileCatalogReadModelRepository
) : QueryService<RuntimeEngineProfileCatalogReadModelEntity>() {
    fun findByCriteria(criteria: RuntimeEngineProfileCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeEngineProfileCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: RuntimeEngineProfileCatalogReadModelCriteria?): Specification<RuntimeEngineProfileCatalogReadModelEntity> {
        var specification = Specification.where<RuntimeEngineProfileCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.runtimeEngineProfileId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeEngineProfileCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeEngineProfileId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.profileName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeEngineProfileCatalogReadModelEntity>, Expression<String>> { root -> root.get("profileName") })) }
            criteria.pluginProfile?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeEngineProfileCatalogReadModelEntity>, Expression<String>> { root -> root.get("pluginProfile") })) }
            criteria.runtimeEngineImage?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeEngineProfileCatalogReadModelEntity>, Expression<String>> { root -> root.get("runtimeEngineImage") })) }
            criteria.imageDigest?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeEngineProfileCatalogReadModelEntity>, Expression<String>> { root -> root.get("imageDigest") })) }
            criteria.supportedModelPluginsDescription?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeEngineProfileCatalogReadModelEntity>, Expression<String>> { root -> root.get("supportedModelPluginsDescription") })) }
            criteria.supportedAggregationAlgorithmsDescription?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeEngineProfileCatalogReadModelEntity>, Expression<String>> { root -> root.get("supportedAggregationAlgorithmsDescription") })) }
            criteria.active?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeEngineProfileCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("active") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeEngineProfileCatalogReadModelEntity>, Expression<RuntimeEngineProfileStateEnum>> { root -> root.get("state") })) }
            criteria.registeredAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeEngineProfileCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("registeredAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<RuntimeEngineProfileCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<RuntimeEngineProfileCatalogReadModelEntity> =
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
        field: Function<Root<RuntimeEngineProfileCatalogReadModelEntity>, Expression<X>>
    ): Specification<RuntimeEngineProfileCatalogReadModelEntity> =
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

    private fun RuntimeEngineProfileCatalogReadModelEntity.toProjection(): RuntimeEngineProfileCatalogReadModelProjection =
        RuntimeEngineProfileCatalogReadModelProjection().also {
            it.runtimeEngineProfileId = this@toProjection.runtimeEngineProfileId
            it.profileName = this@toProjection.profileName
            it.pluginProfile = this@toProjection.pluginProfile
            it.runtimeEngineImage = this@toProjection.runtimeEngineImage
            it.imageDigest = this@toProjection.imageDigest
            it.supportedModelPluginsDescription = this@toProjection.supportedModelPluginsDescription
            it.supportedAggregationAlgorithmsDescription = this@toProjection.supportedAggregationAlgorithmsDescription
            it.active = this@toProjection.active
            it.state = this@toProjection.state
            it.registeredAt = this@toProjection.registeredAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
