package tech.medo.datasetgovernance.infrastructure.secondary.persistence.runtimedatasetmetadatacatalogreadmodel

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

import tech.medo.datasetgovernance.runtimedatasetmetadatacatalog.RuntimeDatasetMetadataCatalogReadModel
import tech.medo.datasetgovernance.runtimedatasetmetadatacatalog.RuntimeDatasetMetadataCatalogReadModelCriteria
import tech.medo.datasetgovernance.runtimedatasetmetadatacatalog.RuntimeDatasetMetadataCatalogReadModelProjection
import tech.medo.datasetgovernance.runtimedatasetmetadatacatalog.toReadModel

@Service
class RuntimeDatasetMetadataCatalogReadModelQueryService(
    private val repository: SpringDataRuntimeDatasetMetadataCatalogReadModelRepository
) : QueryService<RuntimeDatasetMetadataCatalogReadModelEntity>() {
    fun findByCriteria(criteria: RuntimeDatasetMetadataCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeDatasetMetadataCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: RuntimeDatasetMetadataCatalogReadModelCriteria?): Specification<RuntimeDatasetMetadataCatalogReadModelEntity> {
        var specification = Specification.where<RuntimeDatasetMetadataCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.metadataReportId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("metadataReportId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.datasetId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("datasetId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.organizationId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("organizationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.runtimeId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("runtimeId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.featureSchemaId?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("featureSchemaId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.datasetName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<String>> { root -> root.get("datasetName") })) }
            criteria.organizationName?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<String>> { root -> root.get("organizationName") })) }
            criteria.featureDomain?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<String>> { root -> root.get("featureDomain") })) }
            criteria.featureSchemaVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<String>> { root -> root.get("featureSchemaVersion") })) }
            criteria.sampleCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<Int>> { root -> root.get("sampleCount") })) }
            criteria.featureCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<Int>> { root -> root.get("featureCount") })) }
            criteria.schemaCompatible?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("schemaCompatible") })) }
            criteria.labelCompatible?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("labelCompatible") })) }
            criteria.missingValueRate?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<BigDecimal>> { root -> root.get("missingValueRate") })) }
            criteria.duplicateRate?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<BigDecimal>> { root -> root.get("duplicateRate") })) }
            criteria.qualityScore?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<BigDecimal>> { root -> root.get("qualityScore") })) }
            criteria.nonIidScore?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<BigDecimal>> { root -> root.get("nonIidScore") })) }
            criteria.classBalanceScore?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<BigDecimal>> { root -> root.get("classBalanceScore") })) }
            criteria.profilingStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<String>> { root -> root.get("profilingStatus") })) }
            criteria.failureReason?.let { specification = specification.and(buildSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<String>> { root -> root.get("failureReason") })) }
            criteria.profiledAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("profiledAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<RuntimeDatasetMetadataCatalogReadModelEntity> =
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
        field: Function<Root<RuntimeDatasetMetadataCatalogReadModelEntity>, Expression<X>>
    ): Specification<RuntimeDatasetMetadataCatalogReadModelEntity> =
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

    private fun RuntimeDatasetMetadataCatalogReadModelEntity.toProjection(): RuntimeDatasetMetadataCatalogReadModelProjection =
        RuntimeDatasetMetadataCatalogReadModelProjection().also {
            it.metadataReportId = this@toProjection.metadataReportId
            it.datasetId = this@toProjection.datasetId
            it.organizationId = this@toProjection.organizationId
            it.runtimeId = this@toProjection.runtimeId
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.datasetName = this@toProjection.datasetName
            it.organizationName = this@toProjection.organizationName
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.sampleCount = this@toProjection.sampleCount
            it.featureCount = this@toProjection.featureCount
            it.schemaCompatible = this@toProjection.schemaCompatible
            it.labelCompatible = this@toProjection.labelCompatible
            it.missingValueRate = this@toProjection.missingValueRate
            it.duplicateRate = this@toProjection.duplicateRate
            it.qualityScore = this@toProjection.qualityScore
            it.nonIidScore = this@toProjection.nonIidScore
            it.classBalanceScore = this@toProjection.classBalanceScore
            it.profilingStatus = this@toProjection.profilingStatus
            it.failureReason = this@toProjection.failureReason
            it.profiledAt = this@toProjection.profiledAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
