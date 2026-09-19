package tech.medo.trainingorchestration.registerruntimeengineprofile

import tech.medo.trainingorchestration.registerruntimeengineprofile.RegisterRuntimeEngineProfileCommand


import tech.medo.trainingorchestration.events.RuntimeEngineProfileRegisteredEvent
import tech.medo.trainingorchestration.runtimeengineprofile.RuntimeEngineProfileState





interface RegisterRuntimeEngineProfileDecision {
    fun decide(command: RegisterRuntimeEngineProfileCommand): List<Any> {
        return listOf(
            RuntimeEngineProfileRegisteredEvent(runtimeEngineProfileId = command.runtimeEngineProfileId, profileName = command.profileName, pluginProfile = command.pluginProfile, runtimeEngineImage = command.runtimeEngineImage, imageDigest = command.imageDigest, supportedModelPluginsDescription = command.supportedModelPluginsDescription, supportedAggregationAlgorithmsDescription = command.supportedAggregationAlgorithmsDescription, active = command.active)
        )
    }
}
