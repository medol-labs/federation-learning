package tech.medo.runtimeprovisioning.registerruntimeinfrastructure

import org.springframework.stereotype.Component
import tech.medo.runtimeprovisioning.registerruntimeinfrastructure.RegisterRuntimeInfrastructureCommand

import tech.medo.runtimeprovisioning.events.RuntimeInfrastructureRegisteredEvent
import tech.medo.runtimeprovisioning.runtimeinfrastructure.RuntimeInfrastructureState





@Component
class RegisterRuntimeInfrastructureDecision {
    fun decide(command: RegisterRuntimeInfrastructureCommand): List<Any> {
        return listOf(
            RuntimeInfrastructureRegisteredEvent(runtimeInfrastructureId = command.runtimeInfrastructureId)
        )
    }
}
