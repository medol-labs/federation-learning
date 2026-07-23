package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterstartfailure.ReleaseRuntimeEngineJobAfterStartFailureCommand

import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState



@Component
class ReleaseRuntimeEngineJobAfterStartFailureCommandHandler(
    private val decision: ReleaseRuntimeEngineJobAfterStartFailureDecision
) {
    @CommandHandler
    fun handle(
        command: ReleaseRuntimeEngineJobAfterStartFailureCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
