package tech.medo.runtimeagentoperations.agentruntimeinfrastructureconnectioncatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.runtimeagentoperations.events.AgentRuntimeConnectionEstablishedEvent

import java.time.LocalDateTime
import java.time.ZoneOffset


@Component
class AgentRuntimeInfrastructureConnectionCatalogReadModelProjector(private val repository: AgentRuntimeInfrastructureConnectionCatalogReadModelRepository) {
    @EventHandler
    fun on(
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
            entity.connectedAt = eventTime(message)
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)

}
