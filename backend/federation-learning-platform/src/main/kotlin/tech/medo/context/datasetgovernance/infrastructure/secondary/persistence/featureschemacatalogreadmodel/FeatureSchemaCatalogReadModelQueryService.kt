package tech.medo.datasetgovernance.infrastructure.secondary.persistence.featureschemacatalogreadmodel

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
import tech.medo.datasetgovernance.domain.types.FeatureDefinition;
import tech.medo.datasetgovernance.domain.types.LabelDefinition;

import tech.medo.datasetgovernance.featureschemacatalog.FeatureSchemaCatalogReadModel
import tech.medo.datasetgovernance.featureschemacatalog.FeatureSchemaCatalogReadModelCriteria
import tech.medo.datasetgovernance.featureschemacatalog.FeatureSchemaCatalogReadModelProjection
import tech.medo.datasetgovernance.featureschemacatalog.toReadModel

@Service
class FeatureSchemaCatalogReadModelQueryService(
    private val repository: SpringDataFeatureSchemaCatalogReadModelRepository,
    private val objectMapper: com.fasterxml.jackson.databind.ObjectMapper
) : QueryService<FeatureSchemaCatalogReadModelEntity>() {
    fun findByCriteria(criteria: FeatureSchemaCatalogReadModelCriteria?, pageable: Pageable): Page<FeatureSchemaCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: FeatureSchemaCatalogReadModelCriteria?): Specification<FeatureSchemaCatalogReadModelEntity> {
        var specification = Specification.where<FeatureSchemaCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.featureSchemaId?.let { specification = specification.and(buildSpecification(it, Function<Root<FeatureSchemaCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("featureSchemaId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.featureDomain?.let { specification = specification.and(buildSpecification(it, Function<Root<FeatureSchemaCatalogReadModelEntity>, Expression<String>> { root -> root.get("featureDomain") })) }
            criteria.version?.let { specification = specification.and(buildSpecification(it, Function<Root<FeatureSchemaCatalogReadModelEntity>, Expression<String>> { root -> root.get("version") })) }
            criteria.dataModality?.let { specification = specification.and(buildSpecification(it, Function<Root<FeatureSchemaCatalogReadModelEntity>, Expression<String>> { root -> root.get("dataModality") })) }
            criteria.featureCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<FeatureSchemaCatalogReadModelEntity>, Expression<Int>> { root -> root.get("featureCount") })) }
            criteria.schemaStatus?.let { specification = specification.and(buildSpecification(it, Function<Root<FeatureSchemaCatalogReadModelEntity>, Expression<String>> { root -> root.get("schemaStatus") })) }
            criteria.supersededByFeatureSchemaId?.let { specification = specification.and(buildSpecification(it, Function<Root<FeatureSchemaCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("supersededByFeatureSchemaId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.recommendedForDomain?.let { specification = specification.and(buildSpecification(it, Function<Root<FeatureSchemaCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("recommendedForDomain") })) }
        }
        return specification
    }

    private fun <X : Comparable<in X>> buildExpressionRangeSpecification(
        filter: RangeFilter<X>,
        field: Function<Root<FeatureSchemaCatalogReadModelEntity>, Expression<X>>
    ): Specification<FeatureSchemaCatalogReadModelEntity> =
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

    private fun FeatureSchemaCatalogReadModelEntity.toProjection(): FeatureSchemaCatalogReadModelProjection =
        FeatureSchemaCatalogReadModelProjection().also {
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.featureDomain = this@toProjection.featureDomain
            it.version = this@toProjection.version
            it.dataModality = this@toProjection.dataModality
            it.features = this@toProjection.features?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<FeatureDefinition>>() {}) } ?: emptyList()
            it.labels = this@toProjection.labels?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<LabelDefinition>>() {}) } ?: emptyList()
            it.featureCount = this@toProjection.featureCount
            it.schemaStatus = this@toProjection.schemaStatus
            it.supersededByFeatureSchemaId = this@toProjection.supersededByFeatureSchemaId
            it.recommendedForDomain = this@toProjection.recommendedForDomain
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
