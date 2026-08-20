package tech.medo.secureaggregation.infrastructure.secondary.persistence.secureaggregationsessioncatalogreadmodel

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
import tech.medo.secureaggregation.domain.states.SecureAggregationSessionStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.secureaggregation.secureaggregationsessioncatalog.SecureAggregationSessionCatalogReadModel
import tech.medo.secureaggregation.secureaggregationsessioncatalog.SecureAggregationSessionCatalogReadModelCriteria
import tech.medo.secureaggregation.secureaggregationsessioncatalog.SecureAggregationSessionCatalogReadModelProjection
import tech.medo.secureaggregation.secureaggregationsessioncatalog.toReadModel

@Service
class SecureAggregationSessionCatalogReadModelQueryService(
    private val repository: SpringDataSecureAggregationSessionCatalogReadModelRepository,
    private val objectMapper: com.fasterxml.jackson.databind.ObjectMapper
) : QueryService<SecureAggregationSessionCatalogReadModelEntity>() {
    fun findByCriteria(criteria: SecureAggregationSessionCatalogReadModelCriteria?, pageable: Pageable): Page<SecureAggregationSessionCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: SecureAggregationSessionCatalogReadModelCriteria?): Specification<SecureAggregationSessionCatalogReadModelEntity> {
        var specification = Specification.where<SecureAggregationSessionCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.secureAggregationSessionId?.let { specification = specification.and(buildSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("secureAggregationSessionId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.trainingJobId?.let { specification = specification.and(buildSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("trainingJobId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.trainingRunConfigurationId?.let { specification = specification.and(buildSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("trainingRunConfigurationId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.featureSchemaId?.let { specification = specification.and(buildSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("featureSchemaId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.roundId?.let { specification = specification.and(buildSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("roundId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.requiredParticipantCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<Int>> { root -> root.get("requiredParticipantCount") })) }
            criteria.selectedParticipantCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<Int>> { root -> root.get("selectedParticipantCount") })) }
            criteria.encryptionContextPrepared?.let { specification = specification.and(buildSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<Boolean>> { root -> root.get("encryptionContextPrepared") })) }
            criteria.receivedEncryptedUpdateCount?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<Int>> { root -> root.get("receivedEncryptedUpdateCount") })) }
            criteria.encryptionScheme?.let { specification = specification.and(buildSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<String>> { root -> root.get("encryptionScheme") })) }
            criteria.publicKeyVersion?.let { specification = specification.and(buildSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<String>> { root -> root.get("publicKeyVersion") })) }
            criteria.encryptedParameterScale?.let { specification = specification.and(buildExpressionRangeSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<Int>> { root -> root.get("encryptedParameterScale") })) }
            criteria.aggregatedModelId?.let { specification = specification.and(buildSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("aggregatedModelId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.modelFormat?.let { specification = specification.and(buildSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<String>> { root -> root.get("modelFormat") })) }
            criteria.modelArtifactDigest?.let { specification = specification.and(buildSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<String>> { root -> root.get("modelArtifactDigest") })) }
            criteria.state?.let { specification = specification.and(buildSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<SecureAggregationSessionStateEnum>> { root -> root.get("state") })) }
            criteria.failureReason?.let { specification = specification.and(buildSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<String>> { root -> root.get("failureReason") })) }
            criteria.createdAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("createdAt") })) }
            criteria.selectedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("selectedAt") })) }
            criteria.encryptionContextPreparedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("encryptionContextPreparedAt") })) }
            criteria.decryptedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("decryptedAt") })) }
            criteria.completedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("completedAt") })) }
            criteria.failedAt?.let { specification = specification.and(buildLocalDateTimeRangeSpecification(it, Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<LocalDateTime>> { root -> root.get("failedAt") })) }
        }
        return specification
    }

    private fun buildLocalDateTimeRangeSpecification(
        filter: RangeFilter<*>,
        field: Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<LocalDateTime>>
    ): Specification<SecureAggregationSessionCatalogReadModelEntity> =
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
        field: Function<Root<SecureAggregationSessionCatalogReadModelEntity>, Expression<X>>
    ): Specification<SecureAggregationSessionCatalogReadModelEntity> =
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

    private fun SecureAggregationSessionCatalogReadModelEntity.toProjection(): SecureAggregationSessionCatalogReadModelProjection =
        SecureAggregationSessionCatalogReadModelProjection().also {
            it.secureAggregationSessionId = this@toProjection.secureAggregationSessionId
            it.trainingJobId = this@toProjection.trainingJobId
            it.trainingRunConfigurationId = this@toProjection.trainingRunConfigurationId
            it.featureSchemaId = this@toProjection.featureSchemaId
            it.roundId = this@toProjection.roundId
            it.requiredParticipantCount = this@toProjection.requiredParticipantCount
            it.acceptedRuntimeIds = this@toProjection.acceptedRuntimeIds?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<UUID>>() {}) } ?: emptyList()
            it.selectedRuntimeIds = this@toProjection.selectedRuntimeIds?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<UUID>>() {}) } ?: emptyList()
            it.selectedParticipantCount = this@toProjection.selectedParticipantCount
            it.encryptionContextPrepared = this@toProjection.encryptionContextPrepared
            it.receivedEncryptedUpdateCount = this@toProjection.receivedEncryptedUpdateCount
            it.encryptionScheme = this@toProjection.encryptionScheme
            it.publicKeyVersion = this@toProjection.publicKeyVersion
            it.encryptedParameterScale = this@toProjection.encryptedParameterScale
            it.aggregatedModelId = this@toProjection.aggregatedModelId
            it.modelFormat = this@toProjection.modelFormat
            it.modelArtifactDigest = this@toProjection.modelArtifactDigest
            it.state = this@toProjection.state
            it.failureReason = this@toProjection.failureReason
            it.createdAt = this@toProjection.createdAt
            it.selectedAt = this@toProjection.selectedAt
            it.encryptionContextPreparedAt = this@toProjection.encryptionContextPreparedAt
            it.decryptedAt = this@toProjection.decryptedAt
            it.completedAt = this@toProjection.completedAt
            it.failedAt = this@toProjection.failedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
