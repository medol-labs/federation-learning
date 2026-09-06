package tech.medo.runtimeprovisioning.runtimeinstallationguide

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.organizationmanagement.events.OrganizationRegisteredEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructurePackageRegisteredEvent
import tech.medo.runtimeprovisioning.events.RuntimeInstallationPlanCreatedEvent



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
            entity.runtimeName = event.runtimeName
            entity.bootstrapCommand = event.bootstrapCommand
            entity.nodeLabelCommand = event.nodeLabelCommand
            entity.nodeTaintCommand = event.nodeTaintCommand
            entity.runtimeAgentNodeSelectorYaml = event.runtimeAgentNodeSelectorYaml
            entity.runtimeAgentTolerationsYaml = event.runtimeAgentTolerationsYaml
            entity.bootstrapConfigYaml = event.bootstrapConfigYaml
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
