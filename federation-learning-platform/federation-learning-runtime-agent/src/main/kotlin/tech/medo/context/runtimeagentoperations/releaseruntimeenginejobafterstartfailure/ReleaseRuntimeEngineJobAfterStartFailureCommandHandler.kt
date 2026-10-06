package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure.ReleaseRuntimeEngineJobAfterStartFailureCommand
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure.ReleaseRuntimeEngineJobAfterStartFailureInput
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure.ReleaseRuntimeEngineJobAfterStartFailureService


import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState
import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum


@Component
class ReleaseRuntimeEngineJobAfterStartFailureCommandHandler(
    private val decision: ReleaseRuntimeEngineJobAfterStartFailureDecision,
    private val releaseRuntimeEngineJobAfterStartFailureService: ReleaseRuntimeEngineJobAfterStartFailureService
) {
    @CommandHandler
    fun handle(
        command: ReleaseRuntimeEngineJobAfterStartFailureCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        if (state.currentState != RoundExecutionStateEnum.StartFailed) {
            throw tech.medo.shared.domain.CommandRejectedException(
                code = "COMMAND_REQUIRES_STATE",
                i18nKey = "errors.runtimeagentoperations.releaseRuntimeEngineJobAfterStartFailure.requiresState",
                args = mapOf(
                    "command" to "ReleaseRuntimeEngineJobAfterStartFailure",
                    "aggregate" to "RoundExecution",
                    "expectedState" to "StartFailed",
                    "actualState" to state.currentState.toString()
                ),
                message = "ReleaseRuntimeEngineJobAfterStartFailure requires RoundExecution to be StartFailed."
            )
        }
        val input = ReleaseRuntimeEngineJobAfterStartFailureInput(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId)
        val portResult = releaseRuntimeEngineJobAfterStartFailureService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}
