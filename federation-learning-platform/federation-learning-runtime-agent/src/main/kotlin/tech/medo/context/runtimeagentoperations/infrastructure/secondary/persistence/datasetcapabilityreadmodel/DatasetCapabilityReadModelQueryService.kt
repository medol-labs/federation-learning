package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.datasetcapabilityreadmodel

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
import tech.medo.runtimeagentoperations.domain.types.FeatureDefinition;
import tech.medo.runtimeagentoperations.domain.types.LabelDefinition;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.runtimeagentoperations.datasetcapability.DatasetCapabilityReadModel
import tech.medo.runtimeagentoperations.datasetcapability.DatasetCapabilityReadModelCriteria
import tech.medo.runtimeagentoperations.datasetcapability.DatasetCapabilityReadModelProjection
import tech.medo.runtimeagentoperations.datasetcapability.toReadModel

@Service
class DatasetCapabilityReadModelQueryService(
    private val repository: SpringDataDatasetCapabilityReadModelRepository,
    private val objectMapper: com.fasterxml.jackson.databind.ObjectMapper
) : QueryService<DatasetCapabilityReadModelEntity>() {
    fun findByCriteria(criteria: DatasetCapabilityReadModelCriteria?, pageable: Pageable): Page<DatasetCapabilityReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: DatasetCapabilityReadModelCriteria?): Specification<DatasetCapabilityReadModelEntity> {
        var specification = Specification.where<DatasetCapabilityReadModelEntity>(null)
        if (criteria != null) {
            criteria.datasetId?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("datasetId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationId?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("organizationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeId?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.featureSchemaId?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("featureSchemaId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationName?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<String>> { root -> root.get("organizationName") })) }
            criteria.featureDomain?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<String>> { root -> root.get("featureDomain") })) }
            criteria.featureSchemaVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<String>> { root -> root.get("featureSchemaVersion") })) }
            criteria.datasetName?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<String>> { root -> root.get("datasetName") })) }
            criteria.datasetUsage?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<String>> { root -> root.get("datasetUsage") })) }
            criteria.sampleCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<Int>> { root -> root.get("sampleCount") })) }
            criteria.featureCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<Int>> { root -> root.get("featureCount") })) }
            criteria.schemaCompatible?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<Boolean>> { root -> root.get("schemaCompatible") })) }
            criteria.labelCompatible?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<Boolean>> { root -> root.get("labelCompatible") })) }
            criteria.qualityScore?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<BigDecimal>> { root -> root.get("qualityScore") })) }
            criteria.nonIidScore?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<BigDecimal>> { root -> root.get("nonIidScore") })) }
            criteria.metadataReportId?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("metadataReportId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.metadataStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<String>> { root -> root.get("metadataStatus") })) }
            criteria.contractStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<String>> { root -> root.get("contractStatus") })) }
            criteria.approvalStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<String>> { root -> root.get("approvalStatus") })) }
            criteria.approved?.let { specification = specification.and(buildSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<Boolean>> { root -> root.get("approved") })) }
            criteria.lastProfiledAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<DatasetCapabilityReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("lastProfiledAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<DatasetCapabilityReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<DatasetCapabilityReadModelEntity> =
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
        field: Function<Root<DatasetCapabilityReadModelEntity>, Expression<X>>
    ): Specification<DatasetCapabilityReadModelEntity> =
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

    private fun DatasetCapabilityReadModelEntity.toProjection(): DatasetCapabilityReadModelProjection =
        DatasetCapabilityReadModelProjection().also {
            it.datasetId = this@toProjection.datasetId
            it.organizationId = this@toProjection.organizationId
            it.runtimeId = this@toProjection.runtimeId
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.features = this@toProjection.features?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<FeatureDefinition>>() {}) } ?: emptyList()
            it.labels = this@toProjection.labels?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<LabelDefinition>>() {}) } ?: emptyList()
            it.organizationName = this@toProjection.organizationName
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.datasetName = this@toProjection.datasetName
            it.datasetUsage = this@toProjection.datasetUsage
            it.sampleCount = this@toProjection.sampleCount
            it.featureCount = this@toProjection.featureCount
            it.schemaCompatible = this@toProjection.schemaCompatible
            it.labelCompatible = this@toProjection.labelCompatible
            it.qualityScore = this@toProjection.qualityScore
            it.nonIidScore = this@toProjection.nonIidScore
            it.metadataReportId = this@toProjection.metadataReportId
            it.metadataStatus = this@toProjection.metadataStatus
            it.contractStatus = this@toProjection.contractStatus
            it.approvalStatus = this@toProjection.approvalStatus
            it.approved = this@toProjection.approved
            it.lastProfiledAt = this@toProjection.lastProfiledAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
