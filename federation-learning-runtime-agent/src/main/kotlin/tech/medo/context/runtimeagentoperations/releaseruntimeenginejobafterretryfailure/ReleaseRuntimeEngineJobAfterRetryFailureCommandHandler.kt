package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure.ReleaseRuntimeEngineJobAfterRetryFailureCommand
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure.ReleaseRuntimeEngineJobAfterRetryFailureInput
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure.ReleaseRuntimeEngineJobAfterRetryFailureService
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState

import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class ReleaseRuntimeEngineJobAfterRetryFailureCommandHandler(
    private val decision: ReleaseRuntimeEngineJobAfterRetryFailureDecision,
    private val releaseRuntimeEngineJobAfterRetryFailureService: ReleaseRuntimeEngineJobAfterRetryFailureService
) {
    @CommandHandler
    fun handle(
        command: ReleaseRuntimeEngineJobAfterRetryFailureCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        require(state.currentState == RoundExecutionStateEnum.RETRIED) {
            "ReleaseRuntimeEngineJobAfterRetryFailure requires RoundExecution to be Retried."
        }
        val input = ReleaseRuntimeEngineJobAfterRetryFailureInput(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId)
        val portResult = releaseRuntimeEngineJobAfterRetryFailureService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}
