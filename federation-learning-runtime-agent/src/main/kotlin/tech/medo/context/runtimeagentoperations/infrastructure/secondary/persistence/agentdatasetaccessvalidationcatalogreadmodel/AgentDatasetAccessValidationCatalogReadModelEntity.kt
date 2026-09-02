package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentdatasetaccessvalidationcatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "agent_dataset_access_validation_catalog")
class AgentDatasetAccessValidationCatalogReadModelEntity : MetadataProjection {
    @Id
    var datasetAccessValidationId: UUID? = null
    var runtimeDatasetBindingId: UUID? = null
    var datasetId: UUID? = null
    var organizationId: UUID? = null
    var featureSchemaId: UUID? = null
    var runtimeId: UUID? = null
    var datasetName: String? = null
    var readable: Boolean? = null
    var schemaReadable: Boolean? = null
    var sampleBatchReadable: Boolean? = null
    var validationStatus: String? = null
    @Column(columnDefinition = "text")
    var failureReason: String? = null
    var validatedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
