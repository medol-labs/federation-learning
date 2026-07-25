package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingparticipanteligibilityreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.math.BigDecimal;


@Entity
class TrainingParticipantEligibilityReadModelEntity : MetadataProjection {
    @Id
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
    @Column(columnDefinition = "text")
    var eligibilityReason: String? = null
    @Column(columnDefinition = "text")
    var ineligibleReasons: String? = null
    @Column(columnDefinition = "text")
    var warningReasons: String? = null
    var nextRequiredAction: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
