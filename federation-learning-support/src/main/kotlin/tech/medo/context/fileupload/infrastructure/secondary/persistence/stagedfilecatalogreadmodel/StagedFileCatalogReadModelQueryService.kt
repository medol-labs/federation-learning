package tech.medo.fileupload.infrastructure.secondary.persistence.stagedfilecatalogreadmodel

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
import tech.medo.fileupload.domain.states.StagedFileStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.fileupload.stagedfilecatalog.StagedFileCatalogReadModel
import tech.medo.fileupload.stagedfilecatalog.StagedFileCatalogReadModelCriteria
import tech.medo.fileupload.stagedfilecatalog.StagedFileCatalogReadModelProjection
import tech.medo.fileupload.stagedfilecatalog.toReadModel

@Service
class StagedFileCatalogReadModelQueryService(
    private val repository: SpringDataStagedFileCatalogReadModelRepository
) : QueryService<StagedFileCatalogReadModelEntity>() {
    fun findByCriteria(criteria: StagedFileCatalogReadModelCriteria?, pageable: Pageable): Page<StagedFileCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: StagedFileCatalogReadModelCriteria?): Specification<StagedFileCatalogReadModelEntity> {
        var specification = Specification.where<StagedFileCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.stagedFileId?.let { specification = specification.and(buildSpecification(it, Function<Root<StagedFileCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("stagedFileId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.originalFileName?.let { specification = specification.and(buildSpecification(it, Function<Root<StagedFileCatalogReadModelEntity>, Expression<String>> { root -> root.get("originalFileName") })) }
            criteria.contentType?.let { specification = specification.and(buildSpecification(it, Function<Root<StagedFileCatalogReadModelEntity>, Expression<String>> { root -> root.get("contentType") })) }
            criteria.sizeBytes?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<StagedFileCatalogReadModelEntity>, Expression<Long>> { root -> root.get("sizeBytes") })) }
            criteria.purpose?.let { specification = specification.and(buildSpecification(it, Function<Root<StagedFileCatalogReadModelEntity>, Expression<String>> { root -> root.get("purpose") })) }
            criteria.stagedFileLocation?.let { specification = specification.and(buildSpecification(it, Function<Root<StagedFileCatalogReadModelEntity>, Expression<String>> { root -> root.get("stagedFileLocation") })) }
            criteria.checksum?.let { specification = specification.and(buildSpecification(it, Function<Root<StagedFileCatalogReadModelEntity>, Expression<String>> { root -> root.get("checksum") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<StagedFileCatalogReadModelEntity>, Expression<StagedFileStateEnum>> { root -> root.get("state") })) }
            criteria.stagedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<StagedFileCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("stagedAt") })) }
            criteria.consumedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<StagedFileCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("consumedAt") })) }
            criteria.consumedByContext?.let { specification = specification.and(buildSpecification(it, Function<Root<StagedFileCatalogReadModelEntity>, Expression<String>> { root -> root.get("consumedByContext") })) }
            criteria.consumedByCommand?.let { specification = specification.and(buildSpecification(it, Function<Root<StagedFileCatalogReadModelEntity>, Expression<String>> { root -> root.get("consumedByCommand") })) }
            criteria.consumedByCommandId?.let { specification = specification.and(buildSpecification(it, Function<Root<StagedFileCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("consumedByCommandId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.discardedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<StagedFileCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("discardedAt") })) }
            criteria.discardReason?.let { specification = specification.and(buildSpecification(it, Function<Root<StagedFileCatalogReadModelEntity>, Expression<String>> { root -> root.get("discardReason") })) }
            criteria.expiresAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<StagedFileCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("expiresAt") })) }
            criteria.expiredAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<StagedFileCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("expiredAt") })) }
            criteria.expirationReason?.let { specification = specification.and(buildSpecification(it, Function<Root<StagedFileCatalogReadModelEntity>, Expression<String>> { root -> root.get("expirationReason") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<StagedFileCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<StagedFileCatalogReadModelEntity> =
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
        field: Function<Root<StagedFileCatalogReadModelEntity>, Expression<X>>
    ): Specification<StagedFileCatalogReadModelEntity> =
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

    private fun StagedFileCatalogReadModelEntity.toProjection(): StagedFileCatalogReadModelProjection =
        StagedFileCatalogReadModelProjection().also {
            it.stagedFileId = this@toProjection.stagedFileId
            it.originalFileName = this@toProjection.originalFileName
            it.contentType = this@toProjection.contentType
            it.sizeBytes = this@toProjection.sizeBytes
            it.purpose = this@toProjection.purpose
            it.stagedFileLocation = this@toProjection.stagedFileLocation
            it.checksum = this@toProjection.checksum
            it.state = this@toProjection.state
            it.stagedAt = this@toProjection.stagedAt
            it.consumedAt = this@toProjection.consumedAt
            it.consumedByContext = this@toProjection.consumedByContext
            it.consumedByCommand = this@toProjection.consumedByCommand
            it.consumedByCommandId = this@toProjection.consumedByCommandId
            it.discardedAt = this@toProjection.discardedAt
            it.discardReason = this@toProjection.discardReason
            it.expiresAt = this@toProjection.expiresAt
            it.expiredAt = this@toProjection.expiredAt
            it.expirationReason = this@toProjection.expirationReason
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
