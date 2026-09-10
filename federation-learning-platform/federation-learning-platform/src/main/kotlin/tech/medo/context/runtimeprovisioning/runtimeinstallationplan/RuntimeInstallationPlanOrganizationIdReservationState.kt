package tech.medo.runtimeprovisioning.runtimeinstallationplan

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimeprovisioning.events.RuntimeInstallationPlanOrganizationIdReservedEvent
import java.util.UUID;


@EventSourced(idType = RuntimeInstallationPlanOrganizationIdSelection::class)
class RuntimeInstallationPlanOrganizationIdReservationState @EntityCreator constructor() {

    var reserved: Boolean = false
    var runtimeInstallationPlanId: UUID? = null

    companion object {
        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: RuntimeInstallationPlanOrganizationIdSelection): EventCriteria = EventCriteria.havingTags(
                Tag.of(RuntimeInstallationPlanOrganizationIdReservationTags.ORGANIZATION_ID, selection.normalizedName)
        )
    }

    @EventSourcingHandler
    fun evolve(event: RuntimeInstallationPlanOrganizationIdReservedEvent): RuntimeInstallationPlanOrganizationIdReservationState = apply {
        reserved = true
        runtimeInstallationPlanId = event.runtimeInstallationPlanId
    }
}
