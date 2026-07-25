package tech.medo.runtimeprovisioning.runtimeinfrastructureaccessview

import org.axonframework.messaging.core.MessageType
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.eventhandling.GenericEventMessage
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum
import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationFailedEvent
import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationSucceededEvent
import java.time.Instant
import java.time.LocalDateTime
import java.util.UUID

class RuntimeInfrastructureAccessViewReadModelProjectorTest {
    @Test
    fun RuntimeAgentInstallationFailedUpdatesFailureStatus() {
        val repository = InMemoryRuntimeInfrastructureAccessViewReadModelRepository()
        val projector = RuntimeInfrastructureAccessViewReadModelProjector(repository)
        val runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-failed".toByteArray())
        val event = RuntimeAgentInstallationFailedEvent(
            runtimeInfrastructureId = runtimeInfrastructureId,
            failureReason = "docker compose up failed"
        )

        projector.on(
            event,
            eventMessage(event, Instant.parse("2026-07-25T06:09:12Z"))
        )

        val projection = repository.findProjectionById(runtimeInfrastructureId)!!
        assertEquals(RuntimeInfrastructureStateEnum.RUNTIME_AGENT_FAILED, projection.state)
        assertEquals(LocalDateTime.parse("2026-07-25T06:09:12"), projection.agentDeploymentFailedAt)
        assertEquals("docker compose up failed", projection.agentDeploymentFailureReason)
    }

    @Test
    fun RuntimeAgentInstallationSucceededClearsDeploymentFailure() {
        val repository = InMemoryRuntimeInfrastructureAccessViewReadModelRepository()
        val projector = RuntimeInfrastructureAccessViewReadModelProjector(repository)
        val runtimeInfrastructureId = UUID.nameUUIDFromBytes("runtime-infra-ready".toByteArray())
        val runtimeAgentId = UUID.nameUUIDFromBytes("runtime-agent-ready".toByteArray())

        projector.on(
            RuntimeAgentInstallationFailedEvent(
                runtimeInfrastructureId = runtimeInfrastructureId,
                failureReason = "previous failure"
            ),
            eventMessage(
                RuntimeAgentInstallationFailedEvent(
                    runtimeInfrastructureId = runtimeInfrastructureId,
                    failureReason = "previous failure"
                ),
                Instant.parse("2026-07-25T06:00:00Z")
            )
        )

        val event = RuntimeAgentInstallationSucceededEvent(
            runtimeInfrastructureId = runtimeInfrastructureId,
            runtimeAgentId = runtimeAgentId,
            agentVersion = "1.0.0"
        )
        projector.on(event, eventMessage(event, Instant.parse("2026-07-25T06:10:00Z")))

        val projection = repository.findProjectionById(runtimeInfrastructureId)!!
        assertEquals(RuntimeInfrastructureStateEnum.AGENT_READY, projection.state)
        assertEquals(runtimeAgentId, projection.runtimeAgentId)
        assertEquals("1.0.0", projection.runtimeAgentVersion)
        assertEquals(LocalDateTime.parse("2026-07-25T06:10:00"), projection.agentReadyAt)
        assertNull(projection.agentDeploymentFailedAt)
        assertNull(projection.agentDeploymentFailureReason)
    }

    private fun eventMessage(payload: Any, timestamp: Instant): EventMessage =
        GenericEventMessage(
            UUID.randomUUID().toString(),
            MessageType(payload::class.java),
            payload,
            emptyMap(),
            timestamp
        )
}

private class InMemoryRuntimeInfrastructureAccessViewReadModelRepository : RuntimeInfrastructureAccessViewReadModelRepository {
    private val projections = mutableMapOf<UUID, RuntimeInfrastructureAccessViewReadModelProjection>()

    override fun findAll(pageable: Pageable): Page<RuntimeInfrastructureAccessViewReadModel> =
        PageImpl(projections.values.map { it.toReadModel() }, pageable, projections.size.toLong())

    override fun findById(id: UUID): RuntimeInfrastructureAccessViewReadModel? =
        findProjectionById(id)?.toReadModel()

    override fun findProjectionById(id: UUID): RuntimeInfrastructureAccessViewReadModelProjection? =
        projections[id]

    override fun save(projection: RuntimeInfrastructureAccessViewReadModelProjection) {
        val id = requireNotNull(projection.runtimeInfrastructureId)
        projections[id] = projection
    }
}
