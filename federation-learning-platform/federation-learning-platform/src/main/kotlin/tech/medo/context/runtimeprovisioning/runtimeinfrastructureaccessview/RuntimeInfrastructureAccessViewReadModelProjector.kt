package tech.medo.runtimeprovisioning.runtimeinfrastructureaccessview

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
import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationSucceededEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationFailedEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerificationRetrySucceededEvent
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureVerificationRetryFailedEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentDeploymentRetrySucceededEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentDeploymentRetryFailedEvent
import tech.medo.runtimeprovisioning.events.RuntimeConnectionEstablishedEvent
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum
import java.time.LocalDateTime
import java.time.ZoneOffset


@Namespace("readmodel-runtime-infrastructure-access-view")
@Component
class RuntimeInfrastructureAccessViewReadModelProjector(private val repository: RuntimeInfrastructureAccessViewReadModelRepository) {
    @EventHandler
    fun on(event: OrganizationRegisteredEvent) {
        // Skipped: OrganizationRegisteredEvent does not provide enough key fields to locate RuntimeInfrastructureAccessViewReadModelProjection.
    }

    @EventHandler
    fun on(event: RuntimeInfrastructurePackageRegisteredEvent) {
        // Skipped: RuntimeInfrastructurePackageRegisteredEvent does not provide enough key fields to locate RuntimeInfrastructureAccessViewReadModelProjection.
    }

    @EventHandler
    fun on(
        event: RuntimeInstallationPlanCreatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInfrastructureId) ?: RuntimeInfrastructureAccessViewReadModelProjection().apply {
                this.runtimeInfrastructureId = event.runtimeInfrastructureId
        }
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.organizationId = event.organizationId
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
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

        val entity = repository.findProjectionById(event.runtimeInfrastructureId) ?: RuntimeInfrastructureAccessViewReadModelProjection().apply {
                this.runtimeInfrastructureId = event.runtimeInfrastructureId
        }
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.organizationId = event.organizationId
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.state = RuntimeInfrastructureStateEnum.PLANNED
            entity.infrastructureVerificationFailedAt = null
            entity.infrastructureVerificationFailureReason = null
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureRegisteredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInfrastructureId) ?: RuntimeInfrastructureAccessViewReadModelProjection().apply {
                this.runtimeInfrastructureId = event.runtimeInfrastructureId
        }
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.organizationId = event.organizationId
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeAgentId = event.runtimeAgentId
            entity.state = RuntimeInfrastructureStateEnum.REGISTERED
            entity.infrastructureVerificationFailedAt = null
            entity.infrastructureVerificationFailureReason = null
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructurePreparedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInfrastructureId) ?: RuntimeInfrastructureAccessViewReadModelProjection().apply {
                this.runtimeInfrastructureId = event.runtimeInfrastructureId
        }
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.organizationId = event.organizationId
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeAgentId = event.runtimeAgentId
            entity.preparedNodeCount = event.preparedNodeCount
            entity.state = RuntimeInfrastructureStateEnum.PREPARED
            entity.infrastructurePreparedAt = eventTime(message)
            entity.infrastructureVerificationFailedAt = null
            entity.infrastructureVerificationFailureReason = null
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureVerifiedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInfrastructureId) ?: RuntimeInfrastructureAccessViewReadModelProjection().apply {
                this.runtimeInfrastructureId = event.runtimeInfrastructureId
        }
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.organizationId = event.organizationId
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeAgentId = event.runtimeAgentId
            entity.state = RuntimeInfrastructureStateEnum.VERIFIED
            entity.infrastructureVerifiedAt = eventTime(message)
            entity.infrastructureVerificationFailedAt = null
            entity.infrastructureVerificationFailureReason = null
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureVerificationFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInfrastructureId) ?: RuntimeInfrastructureAccessViewReadModelProjection().apply {
                this.runtimeInfrastructureId = event.runtimeInfrastructureId
        }
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.organizationId = event.organizationId
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeAgentId = event.runtimeAgentId
            entity.infrastructureVerificationFailedAt = eventTime(message)
            entity.infrastructureVerificationFailureReason = event.failureReason
            entity.state = RuntimeInfrastructureStateEnum.VERIFICATION_FAILED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeAgentInstallationSucceededEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInfrastructureId) ?: RuntimeInfrastructureAccessViewReadModelProjection().apply {
                this.runtimeInfrastructureId = event.runtimeInfrastructureId
        }
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.organizationId = event.organizationId
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeAgentId = event.runtimeAgentId
            entity.state = RuntimeInfrastructureStateEnum.AGENT_READY
            entity.runtimeAgentVersion = event.agentVersion
            entity.agentReadyAt = eventTime(message)
            entity.agentDeploymentFailedAt = null
            entity.agentDeploymentFailureReason = null
            entity.agentDeploymentRetryFailedAt = null
            entity.agentDeploymentRetryFailureReason = null
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeAgentInstallationFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInfrastructureId) ?: RuntimeInfrastructureAccessViewReadModelProjection().apply {
                this.runtimeInfrastructureId = event.runtimeInfrastructureId
        }
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.organizationId = event.organizationId
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeAgentId = event.runtimeAgentId
            entity.agentDeploymentFailedAt = eventTime(message)
            entity.agentDeploymentFailureReason = event.failureReason
            entity.state = RuntimeInfrastructureStateEnum.RUNTIME_AGENT_FAILED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureVerificationRetrySucceededEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInfrastructureId) ?: RuntimeInfrastructureAccessViewReadModelProjection().apply {
                this.runtimeInfrastructureId = event.runtimeInfrastructureId
        }
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.organizationId = event.organizationId
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeAgentId = event.runtimeAgentId
            entity.state = RuntimeInfrastructureStateEnum.VERIFIED
            entity.infrastructureVerifiedAt = eventTime(message)
            entity.infrastructureVerificationFailedAt = null
            entity.infrastructureVerificationFailureReason = null
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureVerificationRetryFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInfrastructureId) ?: RuntimeInfrastructureAccessViewReadModelProjection().apply {
                this.runtimeInfrastructureId = event.runtimeInfrastructureId
        }
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.organizationId = event.organizationId
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeAgentId = event.runtimeAgentId
            entity.infrastructureVerificationFailedAt = eventTime(message)
            entity.infrastructureVerificationFailureReason = event.failureReason
            entity.state = RuntimeInfrastructureStateEnum.VERIFICATION_FAILED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeAgentDeploymentRetrySucceededEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInfrastructureId) ?: RuntimeInfrastructureAccessViewReadModelProjection().apply {
                this.runtimeInfrastructureId = event.runtimeInfrastructureId
        }
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.organizationId = event.organizationId
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeAgentId = event.runtimeAgentId
            entity.state = RuntimeInfrastructureStateEnum.AGENT_READY
            entity.runtimeAgentVersion = event.agentVersion
            entity.agentReadyAt = eventTime(message)
            entity.agentDeploymentFailedAt = null
            entity.agentDeploymentFailureReason = null
            entity.agentDeploymentRetryFailedAt = null
            entity.agentDeploymentRetryFailureReason = null
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeAgentDeploymentRetryFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInfrastructureId) ?: RuntimeInfrastructureAccessViewReadModelProjection().apply {
                this.runtimeInfrastructureId = event.runtimeInfrastructureId
        }
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.organizationId = event.organizationId
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeAgentId = event.runtimeAgentId
            entity.agentDeploymentRetryFailedAt = eventTime(message)
            entity.agentDeploymentRetryFailureReason = event.failureReason
            entity.state = RuntimeInfrastructureStateEnum.RUNTIME_AGENT_FAILED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeConnectionEstablishedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInfrastructureId) ?: RuntimeInfrastructureAccessViewReadModelProjection().apply {
                this.runtimeInfrastructureId = event.runtimeInfrastructureId
        }
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.runtimeName = event.runtimeName
            entity.agentInstallMode = event.agentInstallMode
            entity.runtimeAgentId = event.runtimeAgentId
            entity.state = RuntimeInfrastructureStateEnum.CONNECTED
            entity.connectedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}
