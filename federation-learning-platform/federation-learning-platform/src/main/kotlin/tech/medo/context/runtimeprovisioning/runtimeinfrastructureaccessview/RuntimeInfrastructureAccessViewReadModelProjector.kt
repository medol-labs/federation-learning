package tech.medo.runtimeprovisioning.runtimeinfrastructureaccessview

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum
import java.time.LocalDateTime
import java.time.ZoneOffset


interface RuntimeInfrastructureAccessViewReadModelProjectionUpdater {
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

open class DefaultRuntimeInfrastructureAccessViewReadModelProjectionUpdater(
    private val repository: RuntimeInfrastructureAccessViewReadModelRepository
) : RuntimeInfrastructureAccessViewReadModelProjectionUpdater {
    open override fun update(
        event: OrganizationRegisteredEvent,
        message: EventMessage
    ) {
        // Skipped: OrganizationRegisteredEvent does not provide enough key fields to locate RuntimeInfrastructureAccessViewReadModelProjection.
    }

    open override fun update(
        event: RuntimeInfrastructurePackageRegisteredEvent,
        message: EventMessage
    ) {
        // Skipped: RuntimeInfrastructurePackageRegisteredEvent does not provide enough key fields to locate RuntimeInfrastructureAccessViewReadModelProjection.
    }

    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

    @Transactional
    open override fun update(
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

@Configuration(proxyBeanMethods = false)
class RuntimeInfrastructureAccessViewReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(RuntimeInfrastructureAccessViewReadModelProjectionUpdater::class)
    fun defaultRuntimeInfrastructureAccessViewReadModelProjectionUpdater(
        repository: RuntimeInfrastructureAccessViewReadModelRepository
    ): RuntimeInfrastructureAccessViewReadModelProjectionUpdater =
        DefaultRuntimeInfrastructureAccessViewReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-runtime-infrastructure-access-view")
@Component
class RuntimeInfrastructureAccessViewReadModelProjector(
    private val updater: RuntimeInfrastructureAccessViewReadModelProjectionUpdater
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
