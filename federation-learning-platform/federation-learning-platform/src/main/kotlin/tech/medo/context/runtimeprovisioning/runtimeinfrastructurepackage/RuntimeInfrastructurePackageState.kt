package tech.medo.runtimeprovisioning.runtimeinfrastructurepackage

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimeprovisioning.events.RuntimeInfrastructurePackageRegisteredEvent
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructurePackageStateEnum

import java.util.UUID;


@EventSourced(idType = RuntimeInfrastructurePackageSelection::class)
class RuntimeInfrastructurePackageState @EntityCreator constructor() {
    companion object {
        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: RuntimeInfrastructurePackageSelection): EventCriteria = EventCriteria.either(
                EventCriteria.havingTags(Tag.of(RuntimeInfrastructurePackageTags.PACKAGE_NAME, selection.packageName.toString())),
                EventCriteria.havingTags(Tag.of(RuntimeInfrastructurePackageTags.PACKAGE_VERSION, selection.packageVersion.toString()))
        )
    }


    var currentState: RuntimeInfrastructurePackageStateEnum? = null
    var runtimeInfrastructurePackageId: UUID? = null
    var packageName: String? = null
    var packageVersion: String? = null
    var runtimeEnvironmentType: String? = null

    @EventSourcingHandler
    fun evolve(event: RuntimeInfrastructurePackageRegisteredEvent): RuntimeInfrastructurePackageState = apply {
        currentState = RuntimeInfrastructurePackageStateEnum.REGISTERED
        runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
        packageName = event.packageName
        packageVersion = event.packageVersion
        runtimeEnvironmentType = event.runtimeEnvironmentType
    }
}
