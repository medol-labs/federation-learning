package tech.medo.runtimeprovisioning.runtimeinstallationguide

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructurePackageRegisteredEvent
import tech.medo.runtimeprovisioning.events.RuntimeInstallationPlanCreatedEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructurePlannedEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureRegisteredEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructurePreparedEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerifiedEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerificationFailedEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerificationRetrySucceededEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerificationRetryFailedEvent
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum


@Namespace("readmodel-runtime-installation-guide")
@Component
class RuntimeInstallationGuideReadModelProjector(private val repository: RuntimeInstallationGuideReadModelRepository) {
    @EventHandler
    fun on(event: OrganizationRegisteredEvent) {
        // Skipped: OrganizationRegisteredEvent does not provide enough key fields to locate RuntimeInstallationGuideReadModelProjection.
    }

    @EventHandler
    fun on(event: RuntimeInfrastructurePackageRegisteredEvent) {
        // Skipped: RuntimeInfrastructurePackageRegisteredEvent does not provide enough key fields to locate RuntimeInstallationGuideReadModelProjection.
    }

    @EventHandler
    fun on(
        event: RuntimeInstallationPlanCreatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationGuideReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.bootstrapCommand = event.bootstrapCommand
            entity.nodeLabelCommand = event.nodeLabelCommand
            entity.nodeTaintCommand = event.nodeTaintCommand
            entity.runtimeAgentNodeSelectorYaml = event.runtimeAgentNodeSelectorYaml
            entity.runtimeAgentTolerationsYaml = event.runtimeAgentTolerationsYaml
            entity.bootstrapConfigYaml = event.bootstrapConfigYaml
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructurePlannedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationGuideReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureState = RuntimeInfrastructureStateEnum.PLANNED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureRegisteredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationGuideReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureState = RuntimeInfrastructureStateEnum.REGISTERED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructurePreparedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationGuideReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureState = RuntimeInfrastructureStateEnum.PREPARED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureVerifiedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationGuideReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureState = RuntimeInfrastructureStateEnum.VERIFIED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureVerificationFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationGuideReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureState = RuntimeInfrastructureStateEnum.VERIFICATION_FAILED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureVerificationRetrySucceededEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationGuideReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureState = RuntimeInfrastructureStateEnum.VERIFIED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureVerificationRetryFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationGuideReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureState = RuntimeInfrastructureStateEnum.VERIFICATION_FAILED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
