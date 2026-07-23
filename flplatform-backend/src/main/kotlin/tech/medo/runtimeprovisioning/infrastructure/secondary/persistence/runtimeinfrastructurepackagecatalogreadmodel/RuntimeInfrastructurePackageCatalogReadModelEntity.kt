package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinfrastructurepackagecatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructurePackageStateEnum;


@Entity
class RuntimeInfrastructurePackageCatalogReadModelEntity : MetadataProjection {
    @Id
    var runtimeInfrastructurePackageId: UUID? = null
    var packageName: String? = null
    var packageVersion: String? = null
    var runtimeEnvironmentType: String? = null
    var runtimeDeploymentTargetType: String? = null
    var installProfile: String? = null
    var architecture: String? = null
    var installGuide: String? = null
    @Enumerated(EnumType.STRING)
    var state: RuntimeInfrastructurePackageStateEnum? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
