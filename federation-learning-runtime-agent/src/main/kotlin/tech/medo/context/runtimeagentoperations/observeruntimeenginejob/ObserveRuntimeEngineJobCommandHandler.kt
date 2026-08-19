package tech.medo.runtimeagentoperations.observeruntimeenginejob

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.observeruntimeenginejob.ObserveRuntimeEngineJobCommand
import tech.medo.runtimeagentoperations.observeruntimeenginejob.ObserveRuntimeEngineJobInput
import tech.medo.runtimeagentoperations.observeruntimeenginejob.ObserveRuntimeEngineJobService
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState



@Component
class ObserveRuntimeEngineJobCommandHandler(
    private val decision: ObserveRuntimeEngineJobDecision,
    private val observeRuntimeEngineJobService: ObserveRuntimeEngineJobService
) {
    @CommandHandler
    fun handle(
        command: ObserveRuntimeEngineJobCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        val input = ObserveRuntimeEngineJobInput(roundExecutionId = command.roundExecutionId, executionSessionId = command.executionSessionId, executionPlanId = command.executionPlanId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, organizationId = command.organizationId, featureSchemaId = command.featureSchemaId, runtimeEngineJobId = command.runtimeEngineJobId)
        val portResult = observeRuntimeEngineJobService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}
