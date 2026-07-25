package tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.releaseruntimeenginejobafterretryfailure.ReleaseRuntimeEngineJobAfterRetryFailureCommand

import tech.medo.runtimeagentoperations.roundexecution.RoundExecutionState



@Component
class ReleaseRuntimeEngineJobAfterRetryFailureCommandHandler(
    private val decision: ReleaseRuntimeEngineJobAfterRetryFailureDecision
) {
    @CommandHandler
    fun handle(
        command: ReleaseRuntimeEngineJobAfterRetryFailureCommand,
        @InjectEntity(idProperty = "executionPlanId") state: RoundExecutionState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
