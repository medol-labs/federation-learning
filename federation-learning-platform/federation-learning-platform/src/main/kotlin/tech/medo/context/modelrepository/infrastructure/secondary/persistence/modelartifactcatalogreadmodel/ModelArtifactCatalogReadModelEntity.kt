package tech.medo.modelrepository.infrastructure.secondary.persistence.modelartifactcatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import tech.medo.modelrepository.domain.states.ModelArtifactStateEnum
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

@Entity
@Table(name = "model_artifact_catalog")
class ModelArtifactCatalogReadModelEntity : MetadataProjection {
    @Id
    var modelId: UUID? = null
    var modelName: String? = null
    var modelPlugin: String? = null
    var modelVersion: String? = null
    @Column(columnDefinition = "text")
    var modelDescription: String? = null
    var sourceType: String? = null
    var modelScope: String? = null
    var modelArtifactUri: String? = null
    var modelRegistryRef: String? = null
    var modelFormat: String? = null
    var modelArtifactDigest: String? = null
    var modelSignatureUri: String? = null
    var modelSizeBytes: Int? = null
    var personalizedModelStorageLocation: String? = null
    var platformManagedArtifactRef: String? = null
    var trainingJobId: UUID? = null
    var roundId: UUID? = null
    var trainingJobObjective: String? = null
    var personalizationMode: String? = null
    var personalizationTargetType: String? = null
    var personalizationTargetId: UUID? = null
    var personalizationCohortId: UUID? = null
    var baseGlobalModelId: UUID? = null
    @Enumerated(EnumType.STRING)
    var state: ModelArtifactStateEnum? = null
    var registeredAt: LocalDateTime? = null
    var projectionUpdatedAt: LocalDateTime? = null
    var userId: String? = null
    var sessionId: String? = null
    var correlationId: String? = null
    var causationId: String? = null
    var traceId: String? = null
    var tenantId: String? = null

    override fun assignMetadata(metadata: tech.medo.shared.application.metadata.MetadataSnapshot, updateTimestamp: Boolean) {
        if (updateTimestamp) this.projectionUpdatedAt = metadata.projectionUpdatedAt
        this.userId = metadata.userId
        this.sessionId = metadata.sessionId
        this.correlationId = metadata.correlationId
        this.causationId = metadata.causationId
        this.traceId = metadata.traceId
        this.tenantId = metadata.tenantId
    }
}
