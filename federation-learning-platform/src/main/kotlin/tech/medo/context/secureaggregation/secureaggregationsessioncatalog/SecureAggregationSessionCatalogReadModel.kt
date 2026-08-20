package tech.medo.secureaggregation.secureaggregationsessioncatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.secureaggregation.domain.states.SecureAggregationSessionStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.jhipster.service.filter.BooleanFilter
import tech.jhipster.service.filter.Filter
import tech.jhipster.service.filter.IntegerFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class SecureAggregationSessionCatalogReadModelQuery

class SecureAggregationSessionCatalogReadModelCriteria {
    var secureAggregationSessionId: StringFilter? = null
    var trainingJobId: StringFilter? = null
    var trainingRunConfigurationId: StringFilter? = null
    var featureSchemaId: StringFilter? = null
    var roundId: StringFilter? = null
    var requiredParticipantCount: IntegerFilter? = null
    var selectedParticipantCount: IntegerFilter? = null
    var encryptionContextPrepared: BooleanFilter? = null
    var receivedEncryptedUpdateCount: IntegerFilter? = null
    var encryptionScheme: StringFilter? = null
    var publicKeyVersion: StringFilter? = null
    var encryptedParameterScale: IntegerFilter? = null
    var aggregatedModelId: StringFilter? = null
    var modelFormat: StringFilter? = null
    var modelArtifactDigest: StringFilter? = null
    var state: Filter<SecureAggregationSessionStateEnum>? = null
    var failureReason: StringFilter? = null
    var createdAt: RangeFilter<LocalDateTime>? = null
    var selectedAt: RangeFilter<LocalDateTime>? = null
    var encryptionContextPreparedAt: RangeFilter<LocalDateTime>? = null
    var decryptedAt: RangeFilter<LocalDateTime>? = null
    var completedAt: RangeFilter<LocalDateTime>? = null
    var failedAt: RangeFilter<LocalDateTime>? = null
}


class SecureAggregationSessionCatalogReadModelProjection : MetadataProjection {
    var secureAggregationSessionId: UUID? = null
    var trainingJobId: UUID? = null
    var trainingRunConfigurationId: UUID? = null
    var featureSchemaId: UUID? = null
    var roundId: UUID? = null
    var requiredParticipantCount: Int? = null
    var acceptedRuntimeIds: List<UUID> = emptyList()
    var selectedRuntimeIds: List<UUID> = emptyList()
    var selectedParticipantCount: Int? = null
    var encryptionContextPrepared: Boolean? = null
    var receivedEncryptedUpdateCount: Int? = null
    var encryptionScheme: String? = null
    var publicKeyVersion: String? = null
    var encryptedParameterScale: Int? = null
    var aggregatedModelId: UUID? = null
    var modelFormat: String? = null
    var modelArtifactDigest: String? = null
    var state: SecureAggregationSessionStateEnum? = null
    var failureReason: String? = null
    var createdAt: LocalDateTime? = null
    var selectedAt: LocalDateTime? = null
    var encryptionContextPreparedAt: LocalDateTime? = null
    var decryptedAt: LocalDateTime? = null
    var completedAt: LocalDateTime? = null
    var failedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun SecureAggregationSessionCatalogReadModelProjection.toReadModel(): SecureAggregationSessionCatalogReadModel =
    SecureAggregationSessionCatalogReadModel(
    secureAggregationSessionId = secureAggregationSessionId,
    trainingJobId = trainingJobId,
    trainingRunConfigurationId = trainingRunConfigurationId,
    featureSchemaId = featureSchemaId,
    roundId = roundId,
    requiredParticipantCount = requiredParticipantCount,
    acceptedRuntimeIds = acceptedRuntimeIds,
    selectedRuntimeIds = selectedRuntimeIds,
    selectedParticipantCount = selectedParticipantCount,
    encryptionContextPrepared = encryptionContextPrepared,
    receivedEncryptedUpdateCount = receivedEncryptedUpdateCount,
    encryptionScheme = encryptionScheme,
    publicKeyVersion = publicKeyVersion,
    encryptedParameterScale = encryptedParameterScale,
    aggregatedModelId = aggregatedModelId,
    modelFormat = modelFormat,
    modelArtifactDigest = modelArtifactDigest,
    state = state,
    failureReason = failureReason,
    createdAt = createdAt,
    selectedAt = selectedAt,
    encryptionContextPreparedAt = encryptionContextPreparedAt,
    decryptedAt = decryptedAt,
    completedAt = completedAt,
    failedAt = failedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface SecureAggregationSessionCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<SecureAggregationSessionCatalogReadModel>
    fun findAllByCriteria(criteria: SecureAggregationSessionCatalogReadModelCriteria?, pageable: Pageable): Page<SecureAggregationSessionCatalogReadModel>
    fun findById(id: UUID): SecureAggregationSessionCatalogReadModel?
    fun findProjectionById(id: UUID): SecureAggregationSessionCatalogReadModelProjection?
    fun save(projection: SecureAggregationSessionCatalogReadModelProjection)
}

data class SecureAggregationSessionCatalogReadModel(
    val secureAggregationSessionId: UUID?,
    val trainingJobId: UUID?,
    val trainingRunConfigurationId: UUID?,
    val featureSchemaId: UUID?,
    val roundId: UUID?,
    val requiredParticipantCount: Int?,
    val acceptedRuntimeIds: List<UUID>,
    val selectedRuntimeIds: List<UUID>,
    val selectedParticipantCount: Int?,
    val encryptionContextPrepared: Boolean?,
    val receivedEncryptedUpdateCount: Int?,
    val encryptionScheme: String?,
    val publicKeyVersion: String?,
    val encryptedParameterScale: Int?,
    val aggregatedModelId: UUID?,
    val modelFormat: String?,
    val modelArtifactDigest: String?,
    val state: SecureAggregationSessionStateEnum?,
    val failureReason: String?,
    val createdAt: LocalDateTime?,
    val selectedAt: LocalDateTime?,
    val encryptionContextPreparedAt: LocalDateTime?,
    val decryptedAt: LocalDateTime?,
    val completedAt: LocalDateTime?,
    val failedAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
