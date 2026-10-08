package tech.medo.dataexchange.infrastructure.secondary.persistence.dataimportjobcatalogreadmodel

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
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat
import tech.medo.dataexchange.dataimportjob.DataImportJobCatalogReadModel
import tech.medo.dataexchange.dataimportjob.DataImportJobCatalogReadModelCriteria
import tech.medo.dataexchange.dataimportjob.DataImportJobCatalogReadModelProjection
import tech.medo.dataexchange.dataimportjob.toReadModel

@Service
class DataImportJobCatalogReadModelQueryService(
    private val repository: SpringDataDataImportJobCatalogReadModelRepository
) : QueryService<DataImportJobCatalogReadModelEntity>() {
    fun findByCriteria(criteria: DataImportJobCatalogReadModelCriteria?, pageable: Pageable): Page<DataImportJobCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: DataImportJobCatalogReadModelCriteria?): Specification<DataImportJobCatalogReadModelEntity> {
        var specification = Specification.where<DataImportJobCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.dataImportJobId?.let { specification = specification.and(buildSpecification(it, Function<Root<DataImportJobCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("dataImportJobId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.commandName?.let { specification = specification.and(buildSpecification(it, Function<Root<DataImportJobCatalogReadModelEntity>, Expression<String>> { root -> root.get("commandName") })) }
            criteria.resourceName?.let { specification = specification.and(buildSpecification(it, Function<Root<DataImportJobCatalogReadModelEntity>, Expression<String>> { root -> root.get("resourceName") })) }
            criteria.originalFileName?.let { specification = specification.and(buildSpecification(it, Function<Root<DataImportJobCatalogReadModelEntity>, Expression<String>> { root -> root.get("originalFileName") })) }
            criteria.filePath?.let { specification = specification.and(buildSpecification(it, Function<Root<DataImportJobCatalogReadModelEntity>, Expression<String>> { root -> root.get("filePath") })) }
            criteria.requestedLocale?.let { specification = specification.and(buildSpecification(it, Function<Root<DataImportJobCatalogReadModelEntity>, Expression<String>> { root -> root.get("requestedLocale") })) }
            criteria.requestedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<DataImportJobCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("requestedAt") })) }
            criteria.requestHash?.let { specification = specification.and(buildSpecification(it, Function<Root<DataImportJobCatalogReadModelEntity>, Expression<String>> { root -> root.get("requestHash") })) }
            criteria.status?.let { specification = specification.and(buildSpecification(it, Function<Root<DataImportJobCatalogReadModelEntity>, Expression<String>> { root -> root.get("status") })) }
            criteria.totalRows?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<DataImportJobCatalogReadModelEntity>, Expression<Long>> { root -> root.get("totalRows") })) }
            criteria.successRows?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<DataImportJobCatalogReadModelEntity>, Expression<Long>> { root -> root.get("successRows") })) }
            criteria.failedRows?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<DataImportJobCatalogReadModelEntity>, Expression<Long>> { root -> root.get("failedRows") })) }
            criteria.failureIndexPath?.let { specification = specification.and(buildSpecification(it, Function<Root<DataImportJobCatalogReadModelEntity>, Expression<String>> { root -> root.get("failureIndexPath") })) }
            criteria.errorMessage?.let { specification = specification.and(buildSpecification(it, Function<Root<DataImportJobCatalogReadModelEntity>, Expression<String>> { root -> root.get("errorMessage") })) }
            criteria.projectionUpdatedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<DataImportJobCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("projectionUpdatedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<DataImportJobCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<DataImportJobCatalogReadModelEntity> =
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
        field: Function<Root<DataImportJobCatalogReadModelEntity>, Expression<X>>
    ): Specification<DataImportJobCatalogReadModelEntity> =
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

    private fun DataImportJobCatalogReadModelEntity.toProjection(): DataImportJobCatalogReadModelProjection =
        DataImportJobCatalogReadModelProjection().also {
            it.dataImportJobId = this@toProjection.dataImportJobId
            it.commandName = this@toProjection.commandName
            it.resourceName = this@toProjection.resourceName
            it.originalFileName = this@toProjection.originalFileName
            it.filePath = this@toProjection.filePath
            it.requestedLocale = this@toProjection.requestedLocale
            it.requestedAt = this@toProjection.requestedAt
            it.requestHash = this@toProjection.requestHash
            it.status = this@toProjection.status
            it.totalRows = this@toProjection.totalRows
            it.successRows = this@toProjection.successRows
            it.failedRows = this@toProjection.failedRows
            it.failureIndexPath = this@toProjection.failureIndexPath
            it.errorMessage = this@toProjection.errorMessage
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
