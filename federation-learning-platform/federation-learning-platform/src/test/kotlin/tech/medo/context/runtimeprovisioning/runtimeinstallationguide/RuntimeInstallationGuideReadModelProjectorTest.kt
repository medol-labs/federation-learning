package tech.medo.runtimeprovisioning.runtimeinstallationguide

import org.axonframework.messaging.core.MessageType
import org.axonframework.messaging.eventhandling.GenericEventMessage
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import tech.medo.runtimeprovisioning.events.RuntimeInstallationPlanCreatedEvent
import java.util.UUID

class RuntimeInstallationGuideReadModelProjectorTest {
    private val runtimeInstallationPlanId = UUID.fromString("00000000-0000-0000-0000-000000000021")
    private val runtimeInfrastructureId = UUID.fromString("00000000-0000-0000-0000-000000000022")
    private val runtimeInfrastructurePackageId = UUID.fromString("00000000-0000-0000-0000-000000000023")
    private val organizationId = UUID.fromString("00000000-0000-0000-0000-000000000024")

    @Test
    fun `renders participant node bootstrap scheduling configuration`() {
        val repository = FakeRuntimeInstallationGuideRepository()
        val projector = RuntimeInstallationGuideReadModelProjector(repository)
        val event = RuntimeInstallationPlanCreatedEvent(
            runtimeInstallationPlanId = runtimeInstallationPlanId,
            runtimeInfrastructureId = runtimeInfrastructureId,
            organizationId = organizationId,
            organizationName = "Hospital A",
            runtimeInfrastructurePackageId = runtimeInfrastructurePackageId,
            runtimeInfrastructurePackageName = "K3s Runtime Package",
            runtimeInfrastructurePackageVersion = "1.0.0",
            runtimeEnvironmentType = "K3S",
            runtimeName = "hospital-a-runtime",
            bootstrapCommand = "kubectl label node <node-name> medol.dev/organization-id=$organizationId",
            nodeLabelCommand = "kubectl label node <node-name> medol.dev/runtime-infrastructure-id=$runtimeInfrastructureId",
            nodeTaintCommand = "kubectl taint node <node-name> medol.dev/runtime-only=true:NoSchedule --overwrite",
            runtimeAgentNodeSelectorYaml = "nodeSelector:\n  medol.dev/runtime-infrastructure-id: \"$runtimeInfrastructureId\"",
            runtimeAgentTolerationsYaml = "tolerations:\n  - key: \"medol.dev/runtime-only\"",
            bootstrapConfigYaml = "kind: RuntimeParticipantNodeConfig\nruntimeInfrastructureId: \"$runtimeInfrastructureId\"",
            agentInstallMode = "PLATFORM_MANAGED",
            expectedNodeCount = 1
        )

        projector.on(
            event,
            GenericEventMessage(MessageType(RuntimeInstallationPlanCreatedEvent::class.java), event)
        )

        val projection = repository.findProjectionById(runtimeInstallationPlanId)
        assertNotNull(projection)
        assertEquals(runtimeInfrastructureId, projection?.runtimeInfrastructureId)
        assertTrue(projection?.bootstrapCommand?.contains("kubectl label node <node-name>") == true)
        assertTrue(projection?.nodeLabelCommand?.contains("medol.dev/runtime-infrastructure-id=$runtimeInfrastructureId") == true)
        assertTrue(projection?.nodeTaintCommand?.contains("medol.dev/runtime-only=true:NoSchedule") == true)
        assertTrue(projection?.runtimeAgentNodeSelectorYaml?.contains("medol.dev/runtime-infrastructure-id: \"$runtimeInfrastructureId\"") == true)
        assertTrue(projection?.runtimeAgentTolerationsYaml?.contains("key: \"medol.dev/runtime-only\"") == true)
        assertTrue(projection?.bootstrapConfigYaml?.contains("kind: RuntimeParticipantNodeConfig") == true)
        assertTrue(projection?.bootstrapConfigYaml?.contains("runtimeInfrastructureId: \"$runtimeInfrastructureId\"") == true)
    }
}

private class FakeRuntimeInstallationGuideRepository : RuntimeInstallationGuideReadModelRepository {
    private val projections = linkedMapOf<UUID, RuntimeInstallationGuideReadModelProjection>()

    override fun findAll(pageable: Pageable): Page<RuntimeInstallationGuideReadModel> =
        PageImpl(projections.values.map { it.toReadModel() })

    override fun findAllByCriteria(
        criteria: RuntimeInstallationGuideReadModelCriteria?,
        pageable: Pageable
    ): Page<RuntimeInstallationGuideReadModel> =
        findAll(pageable)

    override fun findById(id: UUID): RuntimeInstallationGuideReadModel? =
        projections[id]?.toReadModel()

    override fun findProjectionById(id: UUID): RuntimeInstallationGuideReadModelProjection? =
        projections[id]

    override fun save(projection: RuntimeInstallationGuideReadModelProjection) {
        val id = requireNotNull(projection.runtimeInstallationPlanId)
        projections[id] = projection
    }
}
