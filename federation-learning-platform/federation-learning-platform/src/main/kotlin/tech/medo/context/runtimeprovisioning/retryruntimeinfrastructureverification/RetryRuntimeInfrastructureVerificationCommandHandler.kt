package tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification.RetryRuntimeInfrastructureVerificationCommand
import tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification.RetryRuntimeInfrastructureVerificationInput
import tech.medo.runtimeprovisioning.retryruntimeinfrastructureverification.RetryRuntimeInfrastructureVerificationService
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState



@Component
class RetryRuntimeInfrastructureVerificationCommandHandler(
    private val decision: RetryRuntimeInfrastructureVerificationDecision,
    private val retryRuntimeInfrastructureVerificationService: RetryRuntimeInfrastructureVerificationService
) {
    @CommandHandler
    fun handle(
        command: RetryRuntimeInfrastructureVerificationCommand,
        @InjectEntity(idProperty = "runtimeInfrastructureId") state: RuntimeInfrastructureState,
        eventAppender: EventAppender
    ) {
        val input = RetryRuntimeInfrastructureVerificationInput(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeInstallationPlanId = command.runtimeInstallationPlanId, organizationId = command.organizationId, organizationName = command.organizationName, runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId, runtimeInfrastructurePackageName = command.runtimeInfrastructurePackageName, runtimeInfrastructurePackageVersion = command.runtimeInfrastructurePackageVersion, runtimeEnvironmentType = command.runtimeEnvironmentType, runtimeName = command.runtimeName, runtimeAgentId = command.runtimeAgentId, agentInstallMode = command.agentInstallMode, expectedNodeCount = command.expectedNodeCount, currentRuntimeInfrastructureState = command.currentRuntimeInfrastructureState, retryReason = command.retryReason)
        val portResult = retryRuntimeInfrastructureVerificationService.execute(input)
        val now = java.time.LocalDateTime.now()

        eventAppender.append(decision.decide(command, state, portResult, now))
    }
}
