package tech.medo.modelrepository.infrastructure.secondary.persistence.modelartifactcatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.modelrepository.domain.states.ModelArtifactStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
class ModelArtifactCatalogReadModelEntity : MetadataProjection {
    @Id
    var modelId: UUID? = null
    var modelName: String? = null
    var modelVersion: String? = null
    @Column(columnDefinition = "text")
    var modelDescription: String? = null
    var sourceType: String? = null
    var modelArtifactUri: String? = null
    var modelRegistryRef: String? = null
    var modelFormat: String? = null
    var modelArtifactDigest: String? = null
    var modelSignatureUri: String? = null
    var modelSizeBytes: Int? = null
    var trainingJobId: UUID? = null
    var roundId: UUID? = null
    var trainingJobObjective: String? = null
    @Enumerated(EnumType.STRING)
    var state: ModelArtifactStateEnum? = null
    var registeredAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
