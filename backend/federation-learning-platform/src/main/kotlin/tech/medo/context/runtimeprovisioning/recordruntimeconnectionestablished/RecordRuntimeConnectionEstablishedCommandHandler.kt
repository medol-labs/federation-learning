package tech.medo.runtimeprovisioning.recordruntimeconnectionestablished

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.recordruntimeconnectionestablished.RecordRuntimeConnectionEstablishedCommand

import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState



@Component
class RecordRuntimeConnectionEstablishedCommandHandler(
    private val decision: RecordRuntimeConnectionEstablishedDecision
) {
    @CommandHandler
    fun handle(
        command: RecordRuntimeConnectionEstablishedCommand,
        @InjectEntity(idProperty = "runtimeInfrastructureId") state: RuntimeInfrastructureState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
