package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingrunconfigurationcatalogreadmodel

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
import tech.medo.trainingorchestration.domain.states.TrainingRunConfigurationStateEnum;

import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModel
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelCriteria
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelProjection
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.toReadModel

@Service
class TrainingRunConfigurationCatalogReadModelQueryService(
    private val repository: SpringDataTrainingRunConfigurationCatalogReadModelRepository
) : QueryService<TrainingRunConfigurationCatalogReadModelEntity>() {
    fun findByCriteria(criteria: TrainingRunConfigurationCatalogReadModelCriteria?, pageable: Pageable): Page<TrainingRunConfigurationCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: TrainingRunConfigurationCatalogReadModelCriteria?): Specification<TrainingRunConfigurationCatalogReadModelEntity> {
        var specification = Specification.where<TrainingRunConfigurationCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.trainingRunConfigurationId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("trainingRunConfigurationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.federationId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("federationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.featureSchemaId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("featureSchemaId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.initialModelId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("initialModelId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.initialModelName?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> root.get("initialModelName") })) }
            criteria.initialModelVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> root.get("initialModelVersion") })) }
            criteria.federationName?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> root.get("federationName") })) }
            criteria.featureDomain?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> root.get("featureDomain") })) }
            criteria.featureSchemaVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> root.get("featureSchemaVersion") })) }
            criteria.initialModelArtifactUri?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> root.get("initialModelArtifactUri") })) }
            criteria.initialModelRegistryRef?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> root.get("initialModelRegistryRef") })) }
            criteria.initialModelFormat?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> root.get("initialModelFormat") })) }
            criteria.initialModelArtifactDigest?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> root.get("initialModelArtifactDigest") })) }
            criteria.initialModelSignatureUri?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> root.get("initialModelSignatureUri") })) }
            criteria.strategyName?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> root.get("strategyName") })) }
            criteria.aggregationAlgorithm?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> root.get("aggregationAlgorithm") })) }
            criteria.maxRounds?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<Int>> { root -> root.get("maxRounds") })) }
            criteria.minimumNodesPerRound?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<Int>> { root -> root.get("minimumNodesPerRound") })) }
            criteria.roundTimeoutSeconds?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<Int>> { root -> root.get("roundTimeoutSeconds") })) }
            criteria.nodeResponseTimeoutSeconds?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<Int>> { root -> root.get("nodeResponseTimeoutSeconds") })) }
            criteria.localEpochs?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<Int>> { root -> root.get("localEpochs") })) }
            criteria.batchSize?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<Int>> { root -> root.get("batchSize") })) }
            criteria.learningRate?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<BigDecimal>> { root -> root.get("learningRate") })) }
            criteria.optimizer?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> root.get("optimizer") })) }
            criteria.lossFunction?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> root.get("lossFunction") })) }
            criteria.gradientClippingNorm?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<BigDecimal>> { root -> root.get("gradientClippingNorm") })) }
            criteria.secureAggregationRequired?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("secureAggregationRequired") })) }
            criteria.minimumAccuracy?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<BigDecimal>> { root -> root.get("minimumAccuracy") })) }
            criteria.minimumFairnessScore?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<BigDecimal>> { root -> root.get("minimumFairnessScore") })) }
            criteria.updateReason?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> root.get("updateReason") })) }
            criteria.lockedByTrainingJobId?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("lockedByTrainingJobId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<TrainingRunConfigurationStateEnum>> { root -> root.get("state") })) }
        }
        return specification
    }

    private fun <X : Comparable<in X>> buildExpressionRangeSpecification(
        filter: RangeFilter<X>,
        field: Function<Root<TrainingRunConfigurationCatalogReadModelEntity>, Expression<X>>
    ): Specification<TrainingRunConfigurationCatalogReadModelEntity> =
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

    private fun TrainingRunConfigurationCatalogReadModelEntity.toProjection(): TrainingRunConfigurationCatalogReadModelProjection =
        TrainingRunConfigurationCatalogReadModelProjection().also {
            it.trainingRunConfigurationId = this@toProjection.trainingRunConfigurationId
            it.federationId = this@toProjection.federationId
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.initialModelId = this@toProjection.initialModelId
            it.initialModelName = this@toProjection.initialModelName
            it.initialModelVersion = this@toProjection.initialModelVersion
            it.federationName = this@toProjection.federationName
            it.featureDomain = this@toProjection.featureDomain
            it.featureSchemaVersion = this@toProjection.featureSchemaVersion
            it.initialModelArtifactUri = this@toProjection.initialModelArtifactUri
            it.initialModelRegistryRef = this@toProjection.initialModelRegistryRef
            it.initialModelFormat = this@toProjection.initialModelFormat
            it.initialModelArtifactDigest = this@toProjection.initialModelArtifactDigest
            it.initialModelSignatureUri = this@toProjection.initialModelSignatureUri
            it.strategyName = this@toProjection.strategyName
            it.aggregationAlgorithm = this@toProjection.aggregationAlgorithm
            it.maxRounds = this@toProjection.maxRounds
            it.minimumNodesPerRound = this@toProjection.minimumNodesPerRound
            it.roundTimeoutSeconds = this@toProjection.roundTimeoutSeconds
            it.nodeResponseTimeoutSeconds = this@toProjection.nodeResponseTimeoutSeconds
            it.localEpochs = this@toProjection.localEpochs
            it.batchSize = this@toProjection.batchSize
            it.learningRate = this@toProjection.learningRate
            it.optimizer = this@toProjection.optimizer
            it.lossFunction = this@toProjection.lossFunction
            it.gradientClippingNorm = this@toProjection.gradientClippingNorm
            it.secureAggregationRequired = this@toProjection.secureAggregationRequired
            it.minimumAccuracy = this@toProjection.minimumAccuracy
            it.minimumFairnessScore = this@toProjection.minimumFairnessScore
            it.updateReason = this@toProjection.updateReason
            it.lockedByTrainingJobId = this@toProjection.lockedByTrainingJobId
            it.state = this@toProjection.state
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
