package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinstallationguidereadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;


@Entity
class RuntimeInstallationGuideReadModelEntity : MetadataProjection {
    @Id
    var runtimeInstallationPlanId: UUID? = null
    var organizationId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var runtimeInfrastructurePackageId: UUID? = null
    var runtimeInfrastructurePackageName: String? = null
    var runtimeInfrastructurePackageVersion: String? = null
    var organizationName: String? = null
    var runtimeName: String? = null
    var bootstrapCommand: String? = null
    var runtimeEnvironmentType: String? = null
    var agentInstallMode: String? = null
    var expectedNodeCount: Int? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
