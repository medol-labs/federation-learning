package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinstallationguidereadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum;


@Entity
@Table(name = "runtime_installation_guide")
class RuntimeInstallationGuideReadModelEntity : MetadataProjection {
    @Id
    var runtimeInstallationPlanId: UUID? = null
    var organizationId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var runtimeAgentId: UUID? = null
    @Enumerated(EnumType.STRING)
    var runtimeInfrastructureState: RuntimeInfrastructureStateEnum? = null
    var runtimeInfrastructurePackageId: UUID? = null
    var runtimeInfrastructurePackageName: String? = null
    var runtimeInfrastructurePackageVersion: String? = null
    var organizationName: String? = null
    var runtimeName: String? = null
    @Column(columnDefinition = "text")
    var bootstrapCommand: String? = null
    @Column(columnDefinition = "text")
    var nodeLabelCommand: String? = null
    @Column(columnDefinition = "text")
    var nodeTaintCommand: String? = null
    @Column(columnDefinition = "text")
    var runtimeAgentNodeSelectorYaml: String? = null
    @Column(columnDefinition = "text")
    var runtimeAgentTolerationsYaml: String? = null
    @Column(columnDefinition = "text")
    var bootstrapConfigYaml: String? = null
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
