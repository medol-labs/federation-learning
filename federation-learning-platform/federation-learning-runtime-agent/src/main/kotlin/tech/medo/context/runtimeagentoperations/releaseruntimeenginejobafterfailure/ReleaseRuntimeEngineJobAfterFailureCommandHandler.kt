package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureCommand
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureInput
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureService


import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState
import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class ReleaseRuntimeEngineJobAfterFailureCommandHandler(
    private val decision: ReleaseRuntimeEngineJobAfterFailureDecision,
    private val releaseRuntimeEngineJobAfterFailureService: ReleaseRuntimeEngineJobAfterFailureService
) {
    @CommandHandler
    fun handle(
        command: ReleaseRuntimeEngineJobAfterFailureCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        if (state.currentState != RoundExecutionStateEnum.Failed) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimeagentoperations.releaseRuntimeEngineJobAfterFailure.requiresState",
                args = mapOf(
                    "command" to "ReleaseRuntimeEngineJobAfterFailure",
                    "aggregate" to "RoundExecution",
                    "expectedState" to "Failed",
                    "actualState" to state.currentState.toString()
                ),
                message = "ReleaseRuntimeEngineJobAfterFailure requires RoundExecution to be Failed."
            )
        }
        val input = ReleaseRuntimeEngineJobAfterFailureInput(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId)
        val portResult = releaseRuntimeEngineJobAfterFailureService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}
