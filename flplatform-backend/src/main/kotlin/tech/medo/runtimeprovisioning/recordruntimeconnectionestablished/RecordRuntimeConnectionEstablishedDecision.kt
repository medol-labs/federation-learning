package tech.medo.runtimeprovisioning.recordruntimeconnectionestablished

import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.recordruntimeconnectionestablished.RecordRuntimeConnectionEstablishedCommand

import tech.medo.runtimeprovisioning.events.RuntimeConnectionEstablishedEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState





@Component
class RecordRuntimeConnectionEstablishedDecision {
    fun decide(command: RecordRuntimeConnectionEstablishedCommand, state: RuntimeInfrastructureState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            RuntimeConnectionEstablishedEvent(runtimeInfrastructureId = command.runtimeInfrastructureId, runtimeAgentId = command.runtimeAgentId)
        )
    }
}
