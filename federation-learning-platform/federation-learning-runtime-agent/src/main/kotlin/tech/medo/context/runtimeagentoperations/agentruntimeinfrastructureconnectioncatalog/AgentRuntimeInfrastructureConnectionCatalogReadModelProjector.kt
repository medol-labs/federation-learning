package tech.medo.runtimeagentoperations.agentruntimeinfrastructureconnectioncatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.runtimeagentoperations.events.AgentRuntimeConnectionReportFailedEvent
import tech.medo.runtimeagentoperations.events.AgentRuntimeConnectionEstablishedEvent

import java.time.LocalDateTime
import java.time.ZoneOffset


interface AgentRuntimeInfrastructureConnectionCatalogReadModelProjectionUpdater {
    fun update(
        event: AgentRuntimeConnectionReportFailedEvent,
        message: EventMessage
    )

    fun update(
        event: AgentRuntimeConnectionEstablishedEvent,
        message: EventMessage
    )
}

open class DefaultAgentRuntimeInfrastructureConnectionCatalogReadModelProjectionUpdater(
    private val repository: AgentRuntimeInfrastructureConnectionCatalogReadModelRepository
) : AgentRuntimeInfrastructureConnectionCatalogReadModelProjectionUpdater {
    @Transactional
    open override fun update(
        event: AgentRuntimeConnectionReportFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInfrastructureId) ?: AgentRuntimeInfrastructureConnectionCatalogReadModelProjection().apply {
                this.runtimeInfrastructureId = event.runtimeInfrastructureId
        }
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.runtimePlatformConnectionReady = event.runtimePlatformConnectionReady
            entity.platformApiReachable = event.platformApiReachable
            entity.agentAuthenticationSucceeded = event.agentAuthenticationSucceeded
            entity.controlChannelEstablished = event.controlChannelEstablished
            entity.heartbeatAccepted = event.heartbeatAccepted
            entity.connectionReportRetryable = event.retryable
            entity.connectedAt = eventTime(message)
            entity.connectionReportFailedAt = null
            entity.connectionReportFailureReason = null
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    open override fun update(
        event: AgentRuntimeConnectionEstablishedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInfrastructureId) ?: AgentRuntimeInfrastructureConnectionCatalogReadModelProjection().apply {
                this.runtimeInfrastructureId = event.runtimeInfrastructureId
        }
            entity.runtimeInfrastructureId = event.runtimeInfrastructureId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.runtimePlatformConnectionReady = event.runtimePlatformConnectionReady
            entity.platformApiReachable = event.platformApiReachable
            entity.agentAuthenticationSucceeded = event.agentAuthenticationSucceeded
            entity.controlChannelEstablished = event.controlChannelEstablished
            entity.heartbeatAccepted = event.heartbeatAccepted
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}

@Configuration(proxyBeanMethods = false)
class AgentRuntimeInfrastructureConnectionCatalogReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(AgentRuntimeInfrastructureConnectionCatalogReadModelProjectionUpdater::class)
    fun defaultAgentRuntimeInfrastructureConnectionCatalogReadModelProjectionUpdater(
        repository: AgentRuntimeInfrastructureConnectionCatalogReadModelRepository
    ): AgentRuntimeInfrastructureConnectionCatalogReadModelProjectionUpdater =
        DefaultAgentRuntimeInfrastructureConnectionCatalogReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-agent-runtime-infrastructure-connection-catalog")
@Component
class AgentRuntimeInfrastructureConnectionCatalogReadModelProjector(
    private val updater: AgentRuntimeInfrastructureConnectionCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: AgentRuntimeConnectionReportFailedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: AgentRuntimeConnectionEstablishedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
