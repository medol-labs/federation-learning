package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinfrastructurepackagecatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructurePackageStateEnum
import java.time.LocalDateTime

@Entity
@Table(name = "runtime_infrastructure_package_catalog")
class RuntimeInfrastructurePackageCatalogReadModelEntity : MetadataProjection {
    @Id
    var runtimeInfrastructurePackageId: UUID? = null
    var packageName: String? = null
    var packageVersion: String? = null
    var runtimeEnvironmentType: String? = null
    @Enumerated(EnumType.STRING)
    var state: RuntimeInfrastructurePackageStateEnum? = null
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
