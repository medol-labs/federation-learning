package tech.medo.trainingorchestration.registerruntimeengineprofile

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.trainingorchestration.runtimeengineprofile.RuntimeEngineProfileSelection
import java.util.UUID;


@Command
data class RegisterRuntimeEngineProfileCommand(
    val runtimeEngineProfileId: UUID = java.util.UUID.randomUUID(),
    val profileName: String,
    val pluginProfile: String,
    val runtimeEngineImage: String,
    val imageDigest: String?,
    val supportedModelPluginsDescription: String?,
    val supportedAggregationAlgorithmsDescription: String?,
    val active: Boolean
) {
    @TargetEntityId
    val selection: RuntimeEngineProfileSelection = RuntimeEngineProfileSelection(profileName = profileName)

}
