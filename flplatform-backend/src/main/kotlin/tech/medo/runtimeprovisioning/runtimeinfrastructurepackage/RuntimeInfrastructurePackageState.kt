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
    private var runtimeInfrastructurePackageId: UUID? = null
    private var packageName: String? = null
    private var packageVersion: String? = null
    private var runtimeEnvironmentType: String? = null
    private var runtimeDeploymentTargetType: String? = null
    private var installProfile: String? = null
    private var architecture: String? = null
    private var installGuide: String? = null

    @EventSourcingHandler
    fun evolve(event: RuntimeInfrastructurePackageRegisteredEvent): RuntimeInfrastructurePackageState = apply {
        currentState = RuntimeInfrastructurePackageStateEnum.REGISTERED
        runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
        packageName = event.packageName
        packageVersion = event.packageVersion
        runtimeEnvironmentType = event.runtimeEnvironmentType
        runtimeDeploymentTargetType = event.runtimeDeploymentTargetType
        installProfile = event.installProfile
        architecture = event.architecture
        installGuide = event.installGuide
    }
}
