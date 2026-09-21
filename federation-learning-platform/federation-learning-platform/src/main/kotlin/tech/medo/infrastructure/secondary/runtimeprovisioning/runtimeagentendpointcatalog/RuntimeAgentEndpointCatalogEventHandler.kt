package tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeagentendpointcatalog

import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.stereotype.Component
import tech.medo.runtimemonitoring.events.RuntimeAgentOfflineDetectedEvent
import tech.medo.runtimemonitoring.events.RuntimeAgentRecoveredEvent
import tech.medo.runtimegovernance.events.RuntimeIdentityActivatedEvent
import tech.medo.runtimegovernance.events.RuntimeIdentityRevokedEvent
import tech.medo.runtimeprovisioning.events.RuntimeConnectionEstablishedEvent
import java.time.LocalDateTime
import java.time.ZoneOffset

@Component
class RuntimeAgentEndpointCatalogEventHandler(
    private val repository: RuntimeAgentEndpointCatalogRepository
) {
    @EventHandler
    fun on(
        event: RuntimeConnectionEstablishedEvent,
        message: EventMessage
    ) {
        val entity = repository.findByRuntimeAgentId(event.runtimeAgentId)
            ?: repository.findByRuntimeInfrastructureId(event.runtimeInfrastructureId)
            ?: RuntimeAgentEndpointCatalogEntity().apply {
                runtimeAgentId = event.runtimeAgentId
            }
        entity.runtimeAgentId = event.runtimeAgentId
        entity.runtimeInfrastructureId = event.runtimeInfrastructureId
        entity.organizationId = event.organizationId
        entity.runtimeName = event.runtimeName
        entity.runtimeAgentEndpoint = event.runtimeAgentEndpoint
        entity.endpointScope = event.endpointScope
        entity.connectionStatus = STATUS_CONNECTED
        entity.connectedAt = eventTime(message)
        repository.save(entity)
    }

    @EventHandler
    fun on(
        event: RuntimeIdentityActivatedEvent,
        message: EventMessage
    ) {
        val entity = repository.findByRuntimeAgentId(event.runtimeAgentId)
            ?: repository.findByRuntimeInfrastructureId(event.runtimeInfrastructureId)
            ?: RuntimeAgentEndpointCatalogEntity().apply {
                runtimeAgentId = event.runtimeAgentId
                runtimeInfrastructureId = event.runtimeInfrastructureId
            }
        entity.runtimeId = event.runtimeId
        entity.runtimeAgentId = event.runtimeAgentId
        entity.runtimeInfrastructureId = event.runtimeInfrastructureId
        entity.organizationId = event.organizationId
        entity.runtimeName = event.runtimeName
        entity.connectionStatus = entity.connectionStatus ?: STATUS_CONNECTED
        entity.activatedAt = eventTime(message)
        repository.save(entity)
    }

    @EventHandler
    fun on(event: RuntimeIdentityRevokedEvent) {
        val entity = repository.findFirstByRuntimeIdAndConnectionStatusOrderByConnectedAtDesc(
            runtimeId = event.runtimeId,
            connectionStatus = STATUS_CONNECTED
        ) ?: return
        entity.connectionStatus = STATUS_REVOKED
        repository.save(entity)
    }

    @EventHandler
    fun on(event: RuntimeAgentOfflineDetectedEvent) {
        val entity = repository.findByRuntimeAgentId(event.runtimeAgentId) ?: return
        entity.connectionStatus = STATUS_OFFLINE
        repository.save(entity)
    }

    @EventHandler
    fun on(event: RuntimeAgentRecoveredEvent) {
        val entity = repository.findByRuntimeAgentId(event.runtimeAgentId) ?: return
        entity.connectionStatus = STATUS_CONNECTED
        repository.save(entity)
    }

    private fun eventTime(message: EventMessage): LocalDateTime =
        message.timestamp().atOffset(ZoneOffset.UTC).toLocalDateTime()

    private companion object {
        private const val STATUS_CONNECTED = "CONNECTED"
        private const val STATUS_OFFLINE = "OFFLINE"
        private const val STATUS_REVOKED = "REVOKED"
    }
}
