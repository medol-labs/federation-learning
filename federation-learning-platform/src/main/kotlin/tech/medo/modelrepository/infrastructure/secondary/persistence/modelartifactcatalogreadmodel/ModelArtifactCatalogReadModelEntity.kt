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
    var modelVersionId: UUID? = null
    var modelArtifactRef: String? = null
    var modelRepositoryRef: String? = null
    var modelFormat: String? = null
    var modelHash: String? = null
    var modelSignatureRef: String? = null
    var modelSizeBytes: Int? = null
    var sourceType: String? = null
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
