package tech.medo.fileupload.infrastructure.secondary.persistence.uploadedfilecatalogreadmodel

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
import tech.medo.fileupload.domain.states.UploadedFileStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.fileupload.uploadedfilecatalog.UploadedFileCatalogReadModel
import tech.medo.fileupload.uploadedfilecatalog.UploadedFileCatalogReadModelCriteria
import tech.medo.fileupload.uploadedfilecatalog.UploadedFileCatalogReadModelProjection
import tech.medo.fileupload.uploadedfilecatalog.toReadModel

@Service
class UploadedFileCatalogReadModelQueryService(
    private val repository: SpringDataUploadedFileCatalogReadModelRepository
) : QueryService<UploadedFileCatalogReadModelEntity>() {
    fun findByCriteria(criteria: UploadedFileCatalogReadModelCriteria?, pageable: Pageable): Page<UploadedFileCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: UploadedFileCatalogReadModelCriteria?): Specification<UploadedFileCatalogReadModelEntity> {
        var specification = Specification.where<UploadedFileCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.fileId?.let { specification = specification.and(buildSpecification(it, Function<Root<UploadedFileCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("fileId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.originalFileName?.let { specification = specification.and(buildSpecification(it, Function<Root<UploadedFileCatalogReadModelEntity>, Expression<String>> { root -> root.get("originalFileName") })) }
            criteria.contentType?.let { specification = specification.and(buildSpecification(it, Function<Root<UploadedFileCatalogReadModelEntity>, Expression<String>> { root -> root.get("contentType") })) }
            criteria.sizeBytes?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<UploadedFileCatalogReadModelEntity>, Expression<Long>> { root -> root.get("sizeBytes") })) }
            criteria.purpose?.let { specification = specification.and(buildSpecification(it, Function<Root<UploadedFileCatalogReadModelEntity>, Expression<String>> { root -> root.get("purpose") })) }
            criteria.fileLocation?.let { specification = specification.and(buildSpecification(it, Function<Root<UploadedFileCatalogReadModelEntity>, Expression<String>> { root -> root.get("fileLocation") })) }
            criteria.checksum?.let { specification = specification.and(buildSpecification(it, Function<Root<UploadedFileCatalogReadModelEntity>, Expression<String>> { root -> root.get("checksum") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<UploadedFileCatalogReadModelEntity>, Expression<UploadedFileStateEnum>> { root -> root.get("state") })) }
            criteria.uploadedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<UploadedFileCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("uploadedAt") })) }
            criteria.referencedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<UploadedFileCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("referencedAt") })) }
            criteria.referencedByContext?.let { specification = specification.and(buildSpecification(it, Function<Root<UploadedFileCatalogReadModelEntity>, Expression<String>> { root -> root.get("referencedByContext") })) }
            criteria.referencedByCommand?.let { specification = specification.and(buildSpecification(it, Function<Root<UploadedFileCatalogReadModelEntity>, Expression<String>> { root -> root.get("referencedByCommand") })) }
            criteria.referencedByCommandId?.let { specification = specification.and(buildSpecification(it, Function<Root<UploadedFileCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("referencedByCommandId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.discardedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<UploadedFileCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("discardedAt") })) }
            criteria.discardReason?.let { specification = specification.and(buildSpecification(it, Function<Root<UploadedFileCatalogReadModelEntity>, Expression<String>> { root -> root.get("discardReason") })) }
            criteria.expiresAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<UploadedFileCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("expiresAt") })) }
            criteria.expiredAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<UploadedFileCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("expiredAt") })) }
            criteria.expirationReason?.let { specification = specification.and(buildSpecification(it, Function<Root<UploadedFileCatalogReadModelEntity>, Expression<String>> { root -> root.get("expirationReason") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<UploadedFileCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<UploadedFileCatalogReadModelEntity> =
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
        field: Function<Root<UploadedFileCatalogReadModelEntity>, Expression<X>>
    ): Specification<UploadedFileCatalogReadModelEntity> =
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

    private fun UploadedFileCatalogReadModelEntity.toProjection(): UploadedFileCatalogReadModelProjection =
        UploadedFileCatalogReadModelProjection().also {
            it.fileId = this@toProjection.fileId
            it.originalFileName = this@toProjection.originalFileName
            it.contentType = this@toProjection.contentType
            it.sizeBytes = this@toProjection.sizeBytes
            it.purpose = this@toProjection.purpose
            it.fileLocation = this@toProjection.fileLocation
            it.checksum = this@toProjection.checksum
            it.state = this@toProjection.state
            it.uploadedAt = this@toProjection.uploadedAt
            it.referencedAt = this@toProjection.referencedAt
            it.referencedByContext = this@toProjection.referencedByContext
            it.referencedByCommand = this@toProjection.referencedByCommand
            it.referencedByCommandId = this@toProjection.referencedByCommandId
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
