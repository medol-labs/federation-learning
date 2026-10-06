package tech.medo.dataexchange.infrastructure.secondary.persistence.dataexportjobcatalogreadmodel

import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Root
import org.hibernate.query.criteria.JpaExpression
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import tech.jhipster.service.QueryService
import tech.medo.dataexchange.dataexportjob.DataExportJobCatalogReadModel
import tech.medo.dataexchange.dataexportjob.DataExportJobCatalogReadModelCriteria
import tech.medo.dataexchange.dataexportjob.DataExportJobCatalogReadModelProjection
import tech.medo.dataexchange.dataexportjob.toReadModel
import java.time.Instant
import java.util.UUID
import java.util.function.Function

@Service
class DataExportJobCatalogReadModelQueryService(
    private val repository: SpringDataDataExportJobCatalogReadModelRepository
) : QueryService<DataExportJobCatalogReadModelEntity>() {
    fun findByCriteria(criteria: DataExportJobCatalogReadModelCriteria?, pageable: Pageable): Page<DataExportJobCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: DataExportJobCatalogReadModelCriteria?): Specification<DataExportJobCatalogReadModelEntity> {
        var specification = Specification.where<DataExportJobCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.dataExportJobId?.let { specification = specification.and(buildSpecification(it, Function<Root<DataExportJobCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("dataExportJobId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.resourceName?.let { specification = specification.and(buildSpecification(it, Function<Root<DataExportJobCatalogReadModelEntity>, Expression<String>> { root -> root.get("resourceName") })) }
            criteria.requestedLocale?.let { specification = specification.and(buildSpecification(it, Function<Root<DataExportJobCatalogReadModelEntity>, Expression<String>> { root -> root.get("requestedLocale") })) }
            criteria.requestedAt?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<DataExportJobCatalogReadModelEntity>, Expression<Instant>> { root -> root.get("requestedAt") })) }
            criteria.snapshotUpperBound?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<DataExportJobCatalogReadModelEntity>, Expression<Instant>> { root -> root.get("snapshotUpperBound") })) }
            criteria.requestHash?.let { specification = specification.and(buildSpecification(it, Function<Root<DataExportJobCatalogReadModelEntity>, Expression<String>> { root -> root.get("requestHash") })) }
            criteria.fileName?.let { specification = specification.and(buildSpecification(it, Function<Root<DataExportJobCatalogReadModelEntity>, Expression<String>> { root -> root.get("fileName") })) }
            criteria.filePath?.let { specification = specification.and(buildSpecification(it, Function<Root<DataExportJobCatalogReadModelEntity>, Expression<String>> { root -> root.get("filePath") })) }
            criteria.rowCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<DataExportJobCatalogReadModelEntity>, Expression<Long>> { root -> root.get("rowCount") })) }
            criteria.errorMessage?.let { specification = specification.and(buildSpecification(it, Function<Root<DataExportJobCatalogReadModelEntity>, Expression<String>> { root -> root.get("errorMessage") })) }
            criteria.status?.let { specification = specification.and(buildSpecification(it, Function<Root<DataExportJobCatalogReadModelEntity>, Expression<String>> { root -> root.get("status") })) }
        }
        return specification
    }

    private fun <X : Comparable<in X>> buildExpressionRangeSpecification(
        filter: tech.jhipster.service.filter.RangeFilter<X>,
        field: Function<Root<DataExportJobCatalogReadModelEntity>, Expression<X>>
    ): Specification<DataExportJobCatalogReadModelEntity> =
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

    private fun DataExportJobCatalogReadModelEntity.toProjection(): DataExportJobCatalogReadModelProjection =
        DataExportJobCatalogReadModelProjection().also {
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.dataExportJobId = this@toProjection.dataExportJobId
            it.resourceName = this@toProjection.resourceName
            it.criteriaJson = this@toProjection.criteriaJson
            it.sortJson = this@toProjection.sortJson
            it.columnsJson = this@toProjection.columnsJson
            it.requestedLocale = this@toProjection.requestedLocale
            it.requestedAt = this@toProjection.requestedAt
            it.snapshotUpperBound = this@toProjection.snapshotUpperBound
            it.requestHash = this@toProjection.requestHash
            it.fileName = this@toProjection.fileName
            it.filePath = this@toProjection.filePath
            it.rowCount = this@toProjection.rowCount
            it.errorMessage = this@toProjection.errorMessage
            it.status = this@toProjection.status
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
