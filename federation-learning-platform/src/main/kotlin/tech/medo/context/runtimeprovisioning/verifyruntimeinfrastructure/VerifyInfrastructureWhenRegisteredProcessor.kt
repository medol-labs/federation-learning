package tech.medo.runtimeprovisioning.verifyruntimeinfrastructure

import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureRegisteredEvent
import tech.medo.runtimeprovisioning.verifyruntimeinfrastructure.VerifyRuntimeInfrastructureCommand
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum
import tech.medo.runtimeprovisioning.runtimeinfrastructureaccessview.RuntimeInfrastructureAccessViewReadModelRepository
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.replay.annotation.DisallowReplay
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class VerifyInfrastructureWhenRegisteredProcessor(
    private val commandGateway: CommandGateway,
    private val runtimeInfrastructureAccessView: RuntimeInfrastructureAccessViewReadModelRepository
) {
    private val log = LoggerFactory.getLogger(VerifyInfrastructureWhenRegisteredProcessor::class.java)

    @DisallowReplay
    @EventHandler
    fun on(event: RuntimeInfrastructureRegisteredEvent): java.util.concurrent.CompletableFuture<*> {
        val runtimeInfrastructure = runtimeInfrastructureAccessView.findProjectionById(event.runtimeInfrastructureId)
        val agentInstallMode = runtimeInfrastructure?.agentInstallMode

        if (runtimeInfrastructure?.state in setOf(
                RuntimeInfrastructureStateEnum.VERIFIED,
                RuntimeInfrastructureStateEnum.AGENT_READY,
                RuntimeInfrastructureStateEnum.CONNECTED,
                RuntimeInfrastructureStateEnum.RUNTIME_AGENT_FAILED,
                RuntimeInfrastructureStateEnum.VERIFICATION_FAILED
            )
        ) {
            log.debug(
                "Skip runtime infrastructure verification because infrastructure state has already moved on. runtimeInfrastructureId={}, runtimeAgentId={}, state={}",
                event.runtimeInfrastructureId,
                event.runtimeAgentId,
                runtimeInfrastructure?.state
            )
            return java.util.concurrent.CompletableFuture.completedFuture(null)
        }

        if (agentInstallMode.isNullOrBlank()) {
            log.warn(
                "Skip runtime infrastructure verification because installation mode is missing. runtimeInfrastructureId={}, runtimeAgentId={}",
                event.runtimeInfrastructureId,
                event.runtimeAgentId
            )
            return java.util.concurrent.CompletableFuture.completedFuture(null)
        }

        return commandGateway.send(
            VerifyRuntimeInfrastructureCommand(
                runtimeInfrastructureId = event.runtimeInfrastructureId,
                runtimeAgentId = event.runtimeAgentId,
                agentInstallMode = agentInstallMode,
                verificationPassed = true,
                observedNodeCount = runtimeInfrastructure.expectedNodeCount ?: 0,
                failureReason = null
            )
        ).resultMessage
    }
}
