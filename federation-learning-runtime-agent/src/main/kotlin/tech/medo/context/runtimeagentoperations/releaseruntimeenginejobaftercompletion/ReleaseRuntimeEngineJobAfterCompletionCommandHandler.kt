package tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion.ReleaseRuntimeEngineJobAfterCompletionCommand
import tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion.ReleaseRuntimeEngineJobAfterCompletionInput
import tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion.ReleaseRuntimeEngineJobAfterCompletionService
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState

import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class ReleaseRuntimeEngineJobAfterCompletionCommandHandler(
    private val decision: ReleaseRuntimeEngineJobAfterCompletionDecision,
    private val releaseRuntimeEngineJobAfterCompletionService: ReleaseRuntimeEngineJobAfterCompletionService
) {
    @CommandHandler
    fun handle(
        command: ReleaseRuntimeEngineJobAfterCompletionCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        require(state.currentState == RoundExecutionStateEnum.UPDATE_SUBMITTED) {
            "ReleaseRuntimeEngineJobAfterCompletion requires RoundExecution to be UpdateSubmitted."
        }
        val input = ReleaseRuntimeEngineJobAfterCompletionInput(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId)
        val portResult = releaseRuntimeEngineJobAfterCompletionService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}
