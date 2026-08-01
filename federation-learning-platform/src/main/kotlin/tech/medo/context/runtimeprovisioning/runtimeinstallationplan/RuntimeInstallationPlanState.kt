package tech.medo.runtimeprovisioning.runtimeinstallationplan

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimeprovisioning.events.RuntimeInstallationPlanCreatedEvent
import tech.medo.runtimeprovisioning.domain.states.RuntimeInstallationPlanStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = RuntimeInstallationPlanTags.ORGANIZATION_ID)
class RuntimeInstallationPlanState @EntityCreator constructor() {

    var currentState: RuntimeInstallationPlanStateEnum? = null
    var runtimeInstallationPlanId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var organizationId: UUID? = null
    var runtimeInfrastructurePackageId: UUID? = null
    var runtimeName: String? = null
    var agentInstallMode: String? = null
    var expectedNodeCount: Int? = null

    @EventSourcingHandler
    fun evolve(event: RuntimeInstallationPlanCreatedEvent): RuntimeInstallationPlanState = apply {
        currentState = RuntimeInstallationPlanStateEnum.PLANNED
        runtimeInstallationPlanId = event.runtimeInstallationPlanId
        runtimeInfrastructureId = event.runtimeInfrastructureId
        organizationId = event.organizationId
        runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
        runtimeName = event.runtimeName
        agentInstallMode = event.agentInstallMode
        expectedNodeCount = event.expectedNodeCount
    }
}
