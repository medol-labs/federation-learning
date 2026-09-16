package tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.runtimeprovisioning.events.RuntimeInfrastructurePackageRegisteredEvent
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructurePackageStateEnum


interface RuntimeInfrastructurePackageCatalogReadModelProjectionUpdater {
    fun update(
        event: RuntimeInfrastructurePackageRegisteredEvent,
        message: EventMessage
    )
}

@Component
@ConditionalOnMissingBean(RuntimeInfrastructurePackageCatalogReadModelProjectionUpdater::class)
class DefaultRuntimeInfrastructurePackageCatalogReadModelProjectionUpdater(
    private val repository: RuntimeInfrastructurePackageCatalogReadModelRepository
) : RuntimeInfrastructurePackageCatalogReadModelProjectionUpdater {
    @Transactional
    override fun update(
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
            entity.state = RuntimeInfrastructurePackageStateEnum.REGISTERED
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}

@Namespace("readmodel-runtime-infrastructure-package-catalog")
@Component
class RuntimeInfrastructurePackageCatalogReadModelProjector(
    private val updater: RuntimeInfrastructurePackageCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: RuntimeInfrastructurePackageRegisteredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
