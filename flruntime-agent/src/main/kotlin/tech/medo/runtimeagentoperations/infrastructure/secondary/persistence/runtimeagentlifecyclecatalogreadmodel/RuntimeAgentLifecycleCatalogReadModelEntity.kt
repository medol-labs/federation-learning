package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.runtimeagentlifecyclecatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
class RuntimeAgentLifecycleCatalogReadModelEntity : MetadataProjection {
    @Id
    var runtimeAgentId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var agentVersion: String? = null
    var lifecycleStatus: String? = null
    var runtimeAgentSelfCheckPassed: Boolean? = null
    var configurationLoaded: Boolean? = null
    var secretStoreAccessible: Boolean? = null
    var runtimeEngineAdapterReady: Boolean? = null
    var modelRepositoryClientReady: Boolean? = null
    var localDatasetBindingStoreReady: Boolean? = null
    var workingDirectoryWritable: Boolean? = null
    var startedAt: LocalDateTime? = null
    var readyAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
