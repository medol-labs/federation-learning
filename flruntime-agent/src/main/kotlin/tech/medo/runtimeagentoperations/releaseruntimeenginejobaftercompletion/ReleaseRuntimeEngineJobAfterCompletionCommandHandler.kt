package tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.releaseruntimeenginejobaftercompletion.ReleaseRuntimeEngineJobAfterCompletionCommand

import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState



@Component
class ReleaseRuntimeEngineJobAfterCompletionCommandHandler(
    private val decision: ReleaseRuntimeEngineJobAfterCompletionDecision
) {
    @CommandHandler
    fun handle(
        command: ReleaseRuntimeEngineJobAfterCompletionCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
