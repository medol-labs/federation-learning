package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureCommand
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureInput
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterfailure.ReleaseRuntimeEngineJobAfterFailureService
import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState



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
        val input = ReleaseRuntimeEngineJobAfterFailureInput(roundExecutionId = command.roundExecutionId, runtimeEngineJobId = command.runtimeEngineJobId)
        val portResult = releaseRuntimeEngineJobAfterFailureService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}
