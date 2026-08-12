package tech.medo.runtimegovernance.activateruntimeidentity

import tech.medo.runtimeprovisioning.events.RuntimeAgentInstallationSucceededEvent
import tech.medo.runtimegovernance.activateruntimeidentity.ActivateRuntimeIdentityCommand
import tech.medo.runtimegovernance.runtimeidentitycatalog.RuntimeIdentityCatalogReadModelRepository
import tech.medo.runtimeprovisioning.runtimeinfrastructureaccessview.RuntimeInfrastructureAccessViewReadModelRepository
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.replay.annotation.DisallowReplay
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ActivateRuntimeIdentityWhenAgentInstalledProcessor(
    private val commandGateway: CommandGateway,
    private val runtimeInfrastructureAccessView: RuntimeInfrastructureAccessViewReadModelRepository,
    private val runtimeIdentityCatalog: RuntimeIdentityCatalogReadModelRepository
) {
    private val log = LoggerFactory.getLogger(ActivateRuntimeIdentityWhenAgentInstalledProcessor::class.java)

    @DisallowReplay
    @EventHandler
    fun on(event: RuntimeAgentInstallationSucceededEvent): java.util.concurrent.CompletableFuture<*> {
        val runtimeInfrastructure = runtimeInfrastructureAccessView.findProjectionById(event.runtimeInfrastructureId)
        val organizationId = runtimeInfrastructure?.organizationId
        val runtimeName = runtimeInfrastructure?.runtimeName

        val identityAlreadyActivated = runtimeIdentityCatalog.findAll(org.springframework.data.domain.Pageable.unpaged()).content
            .any { it.runtimeInfrastructureId == event.runtimeInfrastructureId && it.runtimeAgentId == event.runtimeAgentId && it.identityStatus.equals("Active", ignoreCase = true) }
        if (identityAlreadyActivated) {
            log.debug(
                "Skip runtime identity activation because identity is already active. runtimeInfrastructureId={}, runtimeAgentId={}",
                event.runtimeInfrastructureId,
                event.runtimeAgentId
            )
            return java.util.concurrent.CompletableFuture.completedFuture(null)
        }

        if (organizationId == null || runtimeName.isNullOrBlank()) {
            log.warn(
                "Skip runtime identity activation because runtime infrastructure details are missing. runtimeInfrastructureId={}, runtimeAgentId={}, organizationIdPresent={}, runtimeNamePresent={}",
                event.runtimeInfrastructureId,
                event.runtimeAgentId,
                organizationId != null,
                !runtimeName.isNullOrBlank()
            )
            return java.util.concurrent.CompletableFuture.completedFuture(null)
        }

        return commandGateway.send(
            ActivateRuntimeIdentityCommand(
                runtimeInfrastructureId = event.runtimeInfrastructureId,
                runtimeAgentId = event.runtimeAgentId,
                organizationId = organizationId,
                runtimeName = runtimeName
            )
        ).resultMessage
    }
}
