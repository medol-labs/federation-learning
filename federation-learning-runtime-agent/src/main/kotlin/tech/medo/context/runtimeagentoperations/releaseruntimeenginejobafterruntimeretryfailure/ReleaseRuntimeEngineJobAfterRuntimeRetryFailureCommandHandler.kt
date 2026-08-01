package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure.ReleaseRuntimeEngineJobAfterRuntimeRetryFailureCommand
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure.ReleaseRuntimeEngineJobAfterRuntimeRetryFailureInput
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterruntimeretryfailure.ReleaseRuntimeEngineJobAfterRuntimeRetryFailureService
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState



@Component
class ReleaseRuntimeEngineJobAfterRuntimeRetryFailureCommandHandler(
    private val decision: ReleaseRuntimeEngineJobAfterRuntimeRetryFailureDecision,
    private val releaseRuntimeEngineJobAfterRuntimeRetryFailureService: ReleaseRuntimeEngineJobAfterRuntimeRetryFailureService
) {
    @CommandHandler
    fun handle(
        command: ReleaseRuntimeEngineJobAfterRuntimeRetryFailureCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        val input = ReleaseRuntimeEngineJobAfterRuntimeRetryFailureInput(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId)
        val portResult = releaseRuntimeEngineJobAfterRuntimeRetryFailureService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}
