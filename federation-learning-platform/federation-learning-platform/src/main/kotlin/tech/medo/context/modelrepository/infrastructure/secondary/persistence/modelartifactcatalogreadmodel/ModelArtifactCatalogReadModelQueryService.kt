package tech.medo.modelrepository.infrastructure.secondary.persistence.modelartifactcatalogreadmodel

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
import tech.medo.modelrepository.domain.states.ModelArtifactStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModel
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelCriteria
import tech.medo.modelrepository.modelartifactcatalog.ModelArtifactCatalogReadModelProjection
import tech.medo.modelrepository.modelartifactcatalog.toReadModel

@Service
class ModelArtifactCatalogReadModelQueryService(
    private val repository: SpringDataModelArtifactCatalogReadModelRepository
) : QueryService<ModelArtifactCatalogReadModelEntity>() {
    fun findByCriteria(criteria: ModelArtifactCatalogReadModelCriteria?, pageable: Pageable): Page<ModelArtifactCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: ModelArtifactCatalogReadModelCriteria?): Specification<ModelArtifactCatalogReadModelEntity> {
        var specification = Specification.where<ModelArtifactCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.modelId?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelArtifactCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("modelId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.modelName?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelArtifactCatalogReadModelEntity>, Expression<String>> { root -> root.get("modelName") })) }
            criteria.modelVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelArtifactCatalogReadModelEntity>, Expression<String>> { root -> root.get("modelVersion") })) }
            criteria.modelDescription?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelArtifactCatalogReadModelEntity>, Expression<String>> { root -> root.get("modelDescription") })) }
            criteria.sourceType?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelArtifactCatalogReadModelEntity>, Expression<String>> { root -> root.get("sourceType") })) }
            criteria.modelArtifactUri?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelArtifactCatalogReadModelEntity>, Expression<String>> { root -> root.get("modelArtifactUri") })) }
            criteria.modelRegistryRef?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelArtifactCatalogReadModelEntity>, Expression<String>> { root -> root.get("modelRegistryRef") })) }
            criteria.modelFormat?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelArtifactCatalogReadModelEntity>, Expression<String>> { root -> root.get("modelFormat") })) }
            criteria.modelArtifactDigest?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelArtifactCatalogReadModelEntity>, Expression<String>> { root -> root.get("modelArtifactDigest") })) }
            criteria.modelSignatureUri?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelArtifactCatalogReadModelEntity>, Expression<String>> { root -> root.get("modelSignatureUri") })) }
            criteria.modelSizeBytes?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<ModelArtifactCatalogReadModelEntity>, Expression<Int>> { root -> root.get("modelSizeBytes") })) }
            criteria.trainingJobId?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelArtifactCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("trainingJobId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.roundId?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelArtifactCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("roundId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.trainingJobObjective?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelArtifactCatalogReadModelEntity>, Expression<String>> { root -> root.get("trainingJobObjective") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<ModelArtifactCatalogReadModelEntity>, Expression<ModelArtifactStateEnum>> { root -> root.get("state") })) }
            criteria.registeredAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<ModelArtifactCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("registeredAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<ModelArtifactCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<ModelArtifactCatalogReadModelEntity> =
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
        field: Function<Root<ModelArtifactCatalogReadModelEntity>, Expression<X>>
    ): Specification<ModelArtifactCatalogReadModelEntity> =
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

    private fun ModelArtifactCatalogReadModelEntity.toProjection(): ModelArtifactCatalogReadModelProjection =
        ModelArtifactCatalogReadModelProjection().also {
            it.modelId = this@toProjection.modelId
            it.modelName = this@toProjection.modelName
            it.modelVersion = this@toProjection.modelVersion
            it.modelDescription = this@toProjection.modelDescription
            it.sourceType = this@toProjection.sourceType
            it.modelArtifactUri = this@toProjection.modelArtifactUri
            it.modelRegistryRef = this@toProjection.modelRegistryRef
            it.modelFormat = this@toProjection.modelFormat
            it.modelArtifactDigest = this@toProjection.modelArtifactDigest
            it.modelSignatureUri = this@toProjection.modelSignatureUri
            it.modelSizeBytes = this@toProjection.modelSizeBytes
            it.trainingJobId = this@toProjection.trainingJobId
            it.roundId = this@toProjection.roundId
            it.trainingJobObjective = this@toProjection.trainingJobObjective
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
