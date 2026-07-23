package tech.medo.secureaggregation.infrastructure.secondary.persistence.secureaggregationsessioncatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.secureaggregation.domain.states.SecureAggregationSessionStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
class SecureAggregationSessionCatalogReadModelEntity : MetadataProjection {
    @Id
    var secureAggregationSessionId: UUID? = null
    var trainingJobId: UUID? = null
    var trainingRunConfigurationId: UUID? = null
    var featureSchemaId: UUID? = null
    var roundId: UUID? = null
    var requiredParticipantCount: Int? = null
    @Column(columnDefinition = "text")
    var acceptedRuntimeIds: String? = null
    @Column(columnDefinition = "text")
    var selectedRuntimeIds: String? = null
    var selectedParticipantCount: Int? = null
    var encryptionContextPrepared: Boolean? = null
    var receivedEncryptedUpdateCount: Int? = null
    var encryptionScheme: String? = null
    var publicKeyVersion: String? = null
    var encryptedParameterScale: Int? = null
    var aggregatedModelVersionId: UUID? = null
    var modelFormat: String? = null
    var modelHash: String? = null
    @Enumerated(EnumType.STRING)
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
