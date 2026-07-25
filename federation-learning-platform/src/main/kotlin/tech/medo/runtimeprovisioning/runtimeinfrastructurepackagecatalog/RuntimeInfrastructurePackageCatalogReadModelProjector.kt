package tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.stereotype.Component
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.runtimeprovisioning.events.RuntimeInfrastructurePackageRegisteredEvent
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructurePackageStateEnum


@Component
class RuntimeInfrastructurePackageCatalogReadModelProjector(private val repository: RuntimeInfrastructurePackageCatalogReadModelRepository) {
    @EventHandler
    fun on(
        event: RuntimeInfrastructurePackageRegisteredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.runtimeInfrastructurePackageId) ?: RuntimeInfrastructurePackageCatalogReadModelProjection().apply {
                this.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
        }
            entity.runtimeInfrastructurePackageId = event.runtimeInfrastructurePackageId
            entity.packageName = event.packageName
            entity.packageVersion = event.packageVersion
            entity.runtimeEnvironmentType = event.runtimeEnvironmentType
            entity.runtimeDeploymentTargetType = event.runtimeDeploymentTargetType
            entity.installProfile = event.installProfile
            entity.architecture = event.architecture
            entity.installGuide = event.installGuide
            entity.state = RuntimeInfrastructurePackageStateEnum.REGISTERED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}
