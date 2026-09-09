package tech.medo.runtimeprovisioning.runtimeinstallationplancatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
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
import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationSucceededEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationFailedEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentDeploymentRetrySucceededEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentDeploymentRetryFailedEvent
import tech.medo.runtimeprovisioning.events.RuntimeConnectionEstablishedEvent

import java.time.LocalDateTime
import java.time.ZoneOffset


@Component
class RuntimeInstallationPlanCatalogReadModelProjector(private val repository: RuntimeInstallationPlanCatalogReadModelRepository) {
    @EventHandler
    fun on(event: OrganizationRegisteredEvent) {
        // Skipped: OrganizationRegisteredEvent does not provide enough key fields to locate RuntimeInstallationPlanCatalogReadModelProjection.
    }

    @EventHandler
    fun on(event: RuntimeInfrastructurePackageRegisteredEvent) {
        // Skipped: RuntimeInfrastructurePackageRegisteredEvent does not provide enough key fields to locate RuntimeInstallationPlanCatalogReadModelProjection.
    }

    @EventHandler
    fun on(
        event: RuntimeInstallationPlanCreatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationPlanCatalogReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeName = event.runtimeName
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.planStatus = "Planned"
            entity.plannedAt = eventTime(message)
            entity.agentDeploymentFailedAt = null
            entity.agentDeploymentFailureReason = null
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructurePlannedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationPlanCatalogReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.plannedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureRegisteredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationPlanCatalogReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructurePreparedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationPlanCatalogReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.preparedNodeCount = event.preparedNodeCount
            entity.runtimeAgentId = event.runtimeAgentId
            entity.preparedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureVerifiedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationPlanCatalogReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.agentInstallMode = event.agentInstallMode
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.observedNodeCount = event.observedNodeCount
            entity.runtimeAgentId = event.runtimeAgentId
            entity.verifiedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureVerificationFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationPlanCatalogReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.observedNodeCount = event.observedNodeCount
            entity.verificationFailedAt = eventTime(message)
            entity.verificationFailureReason = event.failureReason
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(event: RuntimeAgentInstallationSucceededEvent) {
        // Skipped: RuntimeAgentInstallationSucceededEvent does not provide enough key fields to locate RuntimeInstallationPlanCatalogReadModelProjection.
    }

    @EventHandler
    fun on(event: RuntimeAgentInstallationFailedEvent) {
        // Skipped: RuntimeAgentInstallationFailedEvent does not provide enough key fields to locate RuntimeInstallationPlanCatalogReadModelProjection.
    }

    @EventHandler
    fun on(event: RuntimeAgentDeploymentRetrySucceededEvent) {
        // Skipped: RuntimeAgentDeploymentRetrySucceededEvent does not provide enough key fields to locate RuntimeInstallationPlanCatalogReadModelProjection.
    }

    @EventHandler
    fun on(event: RuntimeAgentDeploymentRetryFailedEvent) {
        // Skipped: RuntimeAgentDeploymentRetryFailedEvent does not provide enough key fields to locate RuntimeInstallationPlanCatalogReadModelProjection.
    }

    @EventHandler
    fun on(event: RuntimeConnectionEstablishedEvent) {
        // Skipped: RuntimeConnectionEstablishedEvent does not provide enough key fields to locate RuntimeInstallationPlanCatalogReadModelProjection.
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}
