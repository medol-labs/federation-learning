package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.datasetreadinessreadmodel

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
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.runtimeagentoperations.datasetreadiness.DatasetReadinessReadModel
import tech.medo.runtimeagentoperations.datasetreadiness.DatasetReadinessReadModelCriteria
import tech.medo.runtimeagentoperations.datasetreadiness.DatasetReadinessReadModelProjection
import tech.medo.runtimeagentoperations.datasetreadiness.toReadModel

@Service
class DatasetReadinessReadModelQueryService(
    private val repository: SpringDataDatasetReadinessReadModelRepository,
    private val objectMapper: com.fasterxml.jackson.databind.ObjectMapper
) : QueryService<DatasetReadinessReadModelEntity>() {
    fun findByCriteria(criteria: DatasetReadinessReadModelCriteria?, pageable: Pageable): Page<DatasetReadinessReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: DatasetReadinessReadModelCriteria?): Specification<DatasetReadinessReadModelEntity> {
        var specification = Specification.where<DatasetReadinessReadModelEntity>(null)
        if (criteria != null) {
            criteria.datasetId?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("datasetId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationId?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("organizationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeId?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.featureSchemaId?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("featureSchemaId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.datasetName?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<String>> { root -> root.get("datasetName") })) }
            criteria.organizationName?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<String>> { root -> root.get("organizationName") })) }
            criteria.featureDomain?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<String>> { root -> root.get("featureDomain") })) }
            criteria.featureSchemaVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<String>> { root -> root.get("featureSchemaVersion") })) }
            criteria.datasetUsage?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<String>> { root -> root.get("datasetUsage") })) }
            criteria.metadataStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<String>> { root -> root.get("metadataStatus") })) }
            criteria.contractStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<String>> { root -> root.get("contractStatus") })) }
            criteria.approvalStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<String>> { root -> root.get("approvalStatus") })) }
            criteria.accessStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<String>> { root -> root.get("accessStatus") })) }
            criteria.runtimeStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<String>> { root -> root.get("runtimeStatus") })) }
            criteria.overallReadiness?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<String>> { root -> root.get("overallReadiness") })) }
            criteria.readyForTraining?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<Boolean>> { root -> root.get("readyForTraining") })) }
            criteria.canBeSelectedForTraining?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<Boolean>> { root -> root.get("canBeSelectedForTraining") })) }
            criteria.readinessScore?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<Int>> { root -> root.get("readinessScore") })) }
            criteria.sampleCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<Int>> { root -> root.get("sampleCount") })) }
            criteria.featureCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<Int>> { root -> root.get("featureCount") })) }
            criteria.schemaCompatible?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<Boolean>> { root -> root.get("schemaCompatible") })) }
            criteria.labelCompatible?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<Boolean>> { root -> root.get("labelCompatible") })) }
            criteria.qualityScore?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<BigDecimal>> { root -> root.get("qualityScore") })) }
            criteria.nonIidScore?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<BigDecimal>> { root -> root.get("nonIidScore") })) }
            criteria.classBalanceScore?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<BigDecimal>> { root -> root.get("classBalanceScore") })) }
            criteria.metadataReportId?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("metadataReportId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.datasetAccessValidationId?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("datasetAccessValidationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.readable?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<Boolean>> { root -> root.get("readable") })) }
            criteria.schemaReadable?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<Boolean>> { root -> root.get("schemaReadable") })) }
            criteria.sampleBatchReadable?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<Boolean>> { root -> root.get("sampleBatchReadable") })) }
            criteria.lastProfiledAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("lastProfiledAt") })) }
            criteria.lastAccessValidatedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("lastAccessValidatedAt") })) }
            criteria.lastRuntimeHeartbeatAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("lastRuntimeHeartbeatAt") })) }
            criteria.lastUpdatedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<DatasetReadinessReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("lastUpdatedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<DatasetReadinessReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<DatasetReadinessReadModelEntity> =
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
        field: Function<Root<DatasetReadinessReadModelEntity>, Expression<X>>
    ): Specification<DatasetReadinessReadModelEntity> =
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

    private fun DatasetReadinessReadModelEntity.toProjection(): DatasetReadinessReadModelProjection =
        DatasetReadinessReadModelProjection().also {
            it.datasetId = this@toProjection.datasetId
            it.organizationId = this@toProjection.organizationId
            it.runtimeId = this@toProjection.runtimeId
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.datasetName = this@toProjection.datasetName
            it.organizationName = this@toProjection.organizationName
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.datasetUsage = this@toProjection.datasetUsage
            it.metadataStatus = this@toProjection.metadataStatus
            it.contractStatus = this@toProjection.contractStatus
            it.approvalStatus = this@toProjection.approvalStatus
            it.accessStatus = this@toProjection.accessStatus
            it.runtimeStatus = this@toProjection.runtimeStatus
            it.overallReadiness = this@toProjection.overallReadiness
            it.readyForTraining = this@toProjection.readyForTraining
            it.canBeSelectedForTraining = this@toProjection.canBeSelectedForTraining
            it.readinessScore = this@toProjection.readinessScore
            it.missingRequirements = this@toProjection.missingRequirements?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) } ?: emptyList()
            it.blockingReasons = this@toProjection.blockingReasons?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) } ?: emptyList()
            it.warnings = this@toProjection.warnings?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) } ?: emptyList()
            it.sampleCount = this@toProjection.sampleCount
            it.featureCount = this@toProjection.featureCount
            it.schemaCompatible = this@toProjection.schemaCompatible
            it.labelCompatible = this@toProjection.labelCompatible
            it.qualityScore = this@toProjection.qualityScore
            it.nonIidScore = this@toProjection.nonIidScore
            it.classBalanceScore = this@toProjection.classBalanceScore
            it.metadataReportId = this@toProjection.metadataReportId
            it.datasetAccessValidationId = this@toProjection.datasetAccessValidationId
            it.readable = this@toProjection.readable
            it.schemaReadable = this@toProjection.schemaReadable
            it.sampleBatchReadable = this@toProjection.sampleBatchReadable
            it.lastProfiledAt = this@toProjection.lastProfiledAt
            it.lastAccessValidatedAt = this@toProjection.lastAccessValidatedAt
            it.lastRuntimeHeartbeatAt = this@toProjection.lastRuntimeHeartbeatAt
            it.lastUpdatedAt = this@toProjection.lastUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
