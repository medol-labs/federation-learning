package tech.medo.runtimeprovisioning.createruntimeinstallationplan

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanCommand
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanInput
import tech.medo.runtimeprovisioning.createruntimeinstallationplan.CreateRuntimeInstallationPlanService




@Component
class CreateRuntimeInstallationPlanCommandHandler(
    private val decision: CreateRuntimeInstallationPlanDecision,
    private val createRuntimeInstallationPlanService: CreateRuntimeInstallationPlanService
) {
    @CommandHandler
    fun handle(
        command: CreateRuntimeInstallationPlanCommand,
        eventAppender: EventAppender
    ) {
        val input = CreateRuntimeInstallationPlanInput(runtimeInstallationPlanId = command.runtimeInstallationPlanId, runtimeInfrastructureId = command.runtimeInfrastructureId, organizationId = command.organizationId, runtimeInfrastructurePackageId = command.runtimeInfrastructurePackageId, runtimeName = command.runtimeName, agentInstallMode = command.agentInstallMode, expectedNodeCount = command.expectedNodeCount)
        val portResult = createRuntimeInstallationPlanService.execute(input)

        eventAppender.append(decision.decide(command, portResult))
    }
}
