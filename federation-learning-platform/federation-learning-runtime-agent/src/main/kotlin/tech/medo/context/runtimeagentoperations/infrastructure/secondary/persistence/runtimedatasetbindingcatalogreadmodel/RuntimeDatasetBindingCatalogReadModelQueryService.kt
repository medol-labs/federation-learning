package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.runtimedatasetbindingcatalogreadmodel

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

import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModel
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelProjection
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.toReadModel

@Service
class RuntimeDatasetBindingCatalogReadModelQueryService(
    private val repository: SpringDataRuntimeDatasetBindingCatalogReadModelRepository
) : QueryService<RuntimeDatasetBindingCatalogReadModelEntity>() {
    fun findByCriteria(criteria: RuntimeDatasetBindingCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeDatasetBindingCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: RuntimeDatasetBindingCatalogReadModelCriteria?): Specification<RuntimeDatasetBindingCatalogReadModelEntity> {
        var specification = Specification.where<RuntimeDatasetBindingCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.runtimeDatasetBindingId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeDatasetBindingId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.datasetId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("datasetId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("organizationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> root.get("organizationName") })) }
            criteria.featureSchemaId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("featureSchemaId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.featureDomain?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> root.get("featureDomain") })) }
            criteria.featureSchemaVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> root.get("featureSchemaVersion") })) }
            criteria.datasetName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> root.get("datasetName") })) }
            criteria.runtimeName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> root.get("runtimeName") })) }
            criteria.dataSourceType?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> root.get("dataSourceType") })) }
            criteria.host?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> root.get("host") })) }
            criteria.port?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<Int>> { root -> root.get("port") })) }
            criteria.url?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> root.get("url") })) }
            criteria.databaseName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> root.get("databaseName") })) }
            criteria.schemaName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> root.get("schemaName") })) }
            criteria.tableName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> root.get("tableName") })) }
            criteria.filePath?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> root.get("filePath") })) }
            criteria.objectBucket?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> root.get("objectBucket") })) }
            criteria.objectPrefix?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> root.get("objectPrefix") })) }
            criteria.dataFormat?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> root.get("dataFormat") })) }
            criteria.credentialSecretName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<String>> { root -> root.get("credentialSecretName") })) }
            criteria.configuredAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("configuredAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<RuntimeDatasetBindingCatalogReadModelEntity> =
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
        field: Function<Root<RuntimeDatasetBindingCatalogReadModelEntity>, Expression<X>>
    ): Specification<RuntimeDatasetBindingCatalogReadModelEntity> =
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

    private fun RuntimeDatasetBindingCatalogReadModelEntity.toProjection(): RuntimeDatasetBindingCatalogReadModelProjection =
        RuntimeDatasetBindingCatalogReadModelProjection().also {
            it.runtimeDatasetBindingId = this@toProjection.runtimeDatasetBindingId
            it.datasetId = this@toProjection.datasetId
            it.organizationId = this@toProjection.organizationId
            it.runtimeId = this@toProjection.runtimeId
            it.organizationName = this@toProjection.organizationName
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.datasetName = this@toProjection.datasetName
            it.runtimeName = this@toProjection.runtimeName
            it.dataSourceType = this@toProjection.dataSourceType
            it.host = this@toProjection.host
            it.port = this@toProjection.port
            it.url = this@toProjection.url
            it.databaseName = this@toProjection.databaseName
            it.schemaName = this@toProjection.schemaName
            it.tableName = this@toProjection.tableName
            it.filePath = this@toProjection.filePath
            it.objectBucket = this@toProjection.objectBucket
            it.objectPrefix = this@toProjection.objectPrefix
            it.dataFormat = this@toProjection.dataFormat
            it.credentialSecretName = this@toProjection.credentialSecretName
            it.configuredAt = this@toProjection.configuredAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
