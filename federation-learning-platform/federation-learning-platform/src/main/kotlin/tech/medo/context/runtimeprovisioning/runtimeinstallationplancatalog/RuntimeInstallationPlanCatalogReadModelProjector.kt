package tech.medo.runtimeprovisioning.runtimeinstallationplancatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
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

import java.time.LocalDateTime
import java.time.ZoneOffset


interface RuntimeInstallationPlanCatalogReadModelProjectionUpdater {
    fun update(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeInfrastructurePackageRegisteredEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeInstallationPlanCreatedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeInfrastructurePlannedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeInfrastructureRegisteredEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeInfrastructurePreparedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeInfrastructureVerifiedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeInfrastructureVerificationFailedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeAgentInstallationSucceededEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeAgentInstallationFailedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeInfrastructureVerificationRetrySucceededEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeInfrastructureVerificationRetryFailedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeAgentDeploymentRetrySucceededEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeAgentDeploymentRetryFailedEvent,
        message: EventMessage
    )

    fun update(
        event: RuntimeConnectionEstablishedEvent,
        message: EventMessage
    )
}

@Component
@ConditionalOnMissingBean(RuntimeInstallationPlanCatalogReadModelProjectionUpdater::class)
class DefaultRuntimeInstallationPlanCatalogReadModelProjectionUpdater(
    private val repository: RuntimeInstallationPlanCatalogReadModelRepository
) : RuntimeInstallationPlanCatalogReadModelProjectionUpdater {
    override fun update(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    ) {
        // Skipped: OrganizationRegisteredEvent does not provide enough key fields to locate RuntimeInstallationPlanCatalogReadModelProjection.
    }

    override fun update(
        event: RuntimeInfrastructurePackageRegisteredEvent,
        message: EventMessage
    ) {
        // Skipped: RuntimeInfrastructurePackageRegisteredEvent does not provide enough key fields to locate RuntimeInstallationPlanCatalogReadModelProjection.
    }

    @Transactional
    override fun update(
        event: RuntimeInstallationPlanCreatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationPlanCatalogReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
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

    @Transactional
    override fun update(
        event: RuntimeInfrastructurePlannedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationPlanCatalogReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.runtimeName = event.runtimeName
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.plannedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: RuntimeInfrastructureRegisteredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationPlanCatalogReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.runtimeName = event.runtimeName
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: RuntimeInfrastructurePreparedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationPlanCatalogReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.runtimeName = event.runtimeName
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.preparedNodeCount = event.preparedNodeCount
            entity.runtimeAgentId = event.runtimeAgentId
            entity.preparedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: RuntimeInfrastructureVerifiedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationPlanCatalogReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.runtimeName = event.runtimeName
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.observedNodeCount = event.observedNodeCount
            entity.runtimeAgentId = event.runtimeAgentId
            entity.verifiedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: RuntimeInfrastructureVerificationFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationPlanCatalogReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.runtimeName = event.runtimeName
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.observedNodeCount = event.observedNodeCount
            entity.runtimeAgentId = event.runtimeAgentId
            entity.verificationFailedAt = eventTime(message)
            entity.verificationFailureReason = event.failureReason
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: RuntimeAgentInstallationSucceededEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationPlanCatalogReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.runtimeName = event.runtimeName
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.runtimeAgentVersion = event.agentVersion
            entity.agentReadyAt = eventTime(message)
            entity.agentDeploymentFailedAt = null
            entity.agentDeploymentFailureReason = null
            entity.agentDeploymentRetryFailedAt = null
            entity.agentDeploymentRetryFailureReason = null
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: RuntimeAgentInstallationFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationPlanCatalogReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.runtimeName = event.runtimeName
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.agentDeploymentFailedAt = eventTime(message)
            entity.agentDeploymentFailureReason = event.failureReason
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: RuntimeInfrastructureVerificationRetrySucceededEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationPlanCatalogReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.runtimeName = event.runtimeName
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.observedNodeCount = event.observedNodeCount
            entity.runtimeAgentId = event.runtimeAgentId
            entity.verifiedAt = eventTime(message)
            entity.verificationFailedAt = null
            entity.verificationFailureReason = null
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: RuntimeInfrastructureVerificationRetryFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationPlanCatalogReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.runtimeName = event.runtimeName
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.observedNodeCount = event.observedNodeCount
            entity.runtimeAgentId = event.runtimeAgentId
            entity.verificationFailedAt = eventTime(message)
            entity.verificationFailureReason = event.failureReason
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: RuntimeAgentDeploymentRetrySucceededEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationPlanCatalogReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.runtimeName = event.runtimeName
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.runtimeAgentVersion = event.agentVersion
            entity.agentReadyAt = eventTime(message)
            entity.agentDeploymentFailedAt = null
            entity.agentDeploymentFailureReason = null
            entity.agentDeploymentRetryFailedAt = null
            entity.agentDeploymentRetryFailureReason = null
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: RuntimeAgentDeploymentRetryFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInstallationPlanId) ?: RuntimeInstallationPlanCatalogReadModelProjection().apply {
                this.runtimeInstallationPlanId = event.runtimeInstallationPlanId
        }
            entity.runtimeInstallationPlanId = event.runtimeInstallationPlanId
            entity.organizationId = event.organizationId
            entity.organizationName = event.organizationName
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.runtimeInfrastructurePackageName = event.runtimeInfrastructurePackageName
            entity.runtimeInfrastructurePackageVersion = event.runtimeInfrastructurePackageVersion
            entity.runtimeName = event.runtimeName
            entity.agentInstallMode = event.agentInstallMode
            entity.expectedNodeCount = event.expectedNodeCount
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.agentDeploymentRetryFailedAt = eventTime(message)
            entity.agentDeploymentRetryFailureReason = event.failureReason
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    override fun update(
        event: RuntimeConnectionEstablishedEvent,
        message: EventMessage
    ) {
        // Skipped: RuntimeConnectionEstablishedEvent does not provide enough key fields to locate RuntimeInstallationPlanCatalogReadModelProjection.
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}

@Namespace("readmodel-runtime-installation-plan-catalog")
@Component
class RuntimeInstallationPlanCatalogReadModelProjector(
    private val updater: RuntimeInstallationPlanCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructurePackageRegisteredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeInstallationPlanCreatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructurePlannedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureRegisteredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructurePreparedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureVerifiedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureVerificationFailedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeAgentInstallationSucceededEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeAgentInstallationFailedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureVerificationRetrySucceededEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeInfrastructureVerificationRetryFailedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeAgentDeploymentRetrySucceededEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeAgentDeploymentRetryFailedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: RuntimeConnectionEstablishedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
