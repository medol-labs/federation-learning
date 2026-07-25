package tech.medo.trainingorchestration.trainingparticipanteligibility

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.math.BigDecimal;


class TrainingParticipantEligibilityReadModelQuery

class TrainingParticipantEligibilityReadModelProjection : MetadataProjection {
    var trainingJobId: UUID? = null
    var federationId: UUID? = null
    var organizationId: UUID? = null
    var runtimeId: UUID? = null
    var featureSchemaId: UUID? = null
    var federationName: String? = null
    var organizationName: String? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var participantStatus: String? = null
    var readinessStatus: String? = null
    var readinessStage: String? = null
    var eligibilityScore: Int? = null
    var runtimeIdentityActive: Boolean? = null
    var runtimeCapabilitySatisfied: Boolean? = null
    var runtimeConnectionEstablished: Boolean? = null
    var runtimeHealthy: Boolean? = null
    var datasetId: UUID? = null
    var datasetName: String? = null
    var datasetReady: Boolean? = null
    var datasetReadinessStatus: String? = null
    var matchedDatasetMetadataReady: Boolean? = null
    var datasetAccessValidated: Boolean? = null
    var datasetApprovedForTraining: Boolean? = null
    var schemaCompatible: Boolean? = null
    var labelCompatible: Boolean? = null
    var qualityScore: BigDecimal? = null
    var securityReady: Boolean? = null
    var eligible: Boolean? = null
    var eligibleRuntimeCount: Int? = null
    var minimumNodesPerRound: Int? = null
    var selectionReady: Boolean? = null
    var eligibilityReason: String? = null
    var ineligibleReasons: List<String> = emptyList()
    var warningReasons: List<String> = emptyList()
    var nextRequiredAction: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun TrainingParticipantEligibilityReadModelProjection.toReadModel(): TrainingParticipantEligibilityReadModel =
    TrainingParticipantEligibilityReadModel(
    trainingJobId = trainingJobId,
    federationId = federationId,
    organizationId = organizationId,
    runtimeId = runtimeId,
    featureSchemaId = featureSchemaId,
    federationName = federationName,
    organizationName = organizationName,
    featureDomain = featureDomain,
    featureSchemaVersion = featureSchemaVersion,
    participantStatus = participantStatus,
    readinessStatus = readinessStatus,
    readinessStage = readinessStage,
    eligibilityScore = eligibilityScore,
    runtimeIdentityActive = runtimeIdentityActive,
    runtimeCapabilitySatisfied = runtimeCapabilitySatisfied,
    runtimeConnectionEstablished = runtimeConnectionEstablished,
    runtimeHealthy = runtimeHealthy,
    datasetId = datasetId,
    datasetName = datasetName,
    datasetReady = datasetReady,
    datasetReadinessStatus = datasetReadinessStatus,
    matchedDatasetMetadataReady = matchedDatasetMetadataReady,
    datasetAccessValidated = datasetAccessValidated,
    datasetApprovedForTraining = datasetApprovedForTraining,
    schemaCompatible = schemaCompatible,
    labelCompatible = labelCompatible,
    qualityScore = qualityScore,
    securityReady = securityReady,
    eligible = eligible,
    eligibleRuntimeCount = eligibleRuntimeCount,
    minimumNodesPerRound = minimumNodesPerRound,
    selectionReady = selectionReady,
    eligibilityReason = eligibilityReason,
    ineligibleReasons = ineligibleReasons,
    warningReasons = warningReasons,
    nextRequiredAction = nextRequiredAction,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface TrainingParticipantEligibilityReadModelRepository {
    fun findAll(pageable: Pageable): Page<TrainingParticipantEligibilityReadModel>
    fun findById(id: UUID): TrainingParticipantEligibilityReadModel?
    fun findProjectionById(id: UUID): TrainingParticipantEligibilityReadModelProjection?
    fun save(projection: TrainingParticipantEligibilityReadModelProjection)
}

data class TrainingParticipantEligibilityReadModel(
    val trainingJobId: UUID?,
    val federationId: UUID?,
    val organizationId: UUID?,
    val runtimeId: UUID?,
    val featureSchemaId: UUID?,
    val federationName: String?,
    val organizationName: String?,
    val featureDomain: String?,
    val featureSchemaVersion: String?,
    val participantStatus: String?,
    val readinessStatus: String?,
    val readinessStage: String?,
    val eligibilityScore: Int?,
    val runtimeIdentityActive: Boolean?,
    val runtimeCapabilitySatisfied: Boolean?,
    val runtimeConnectionEstablished: Boolean?,
    val runtimeHealthy: Boolean?,
    val datasetId: UUID?,
    val datasetName: String?,
    val datasetReady: Boolean?,
    val datasetReadinessStatus: String?,
    val matchedDatasetMetadataReady: Boolean?,
    val datasetAccessValidated: Boolean?,
    val datasetApprovedForTraining: Boolean?,
    val schemaCompatible: Boolean?,
    val labelCompatible: Boolean?,
    val qualityScore: BigDecimal?,
    val securityReady: Boolean?,
    val eligible: Boolean?,
    val eligibleRuntimeCount: Int?,
    val minimumNodesPerRound: Int?,
    val selectionReady: Boolean?,
    val eligibilityReason: String?,
    val ineligibleReasons: List<String>,
    val warningReasons: List<String>,
    val nextRequiredAction: String?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
