package tech.medo.trainingorchestration.registerruntimeengineprofile

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.registerruntimeengineprofile.RegisterRuntimeEngineProfileCommand
import tech.medo.trainingorchestration.events.RuntimeEngineProfileRegisteredEvent
import java.util.UUID

class RegisterRuntimeEngineProfileDecisionTest {
    @Test
    fun RegisterPyTorchVisionRuntimeEngineProfile() {


        val command = RegisterRuntimeEngineProfileCommand(
            runtimeEngineProfileId = UUID.nameUUIDFromBytes("engine-profile-1".toByteArray()),
            profileName = "PyTorch Vision Runtime Engine",
            pluginProfile = "pytorch-vision",
            runtimeEngineImage = "registry.example.com/fl/federation-learning-runtime-engine:pytorch-vision",
            imageDigest = null,
            supportedModelPluginsDescription = null,
            supportedAggregationAlgorithmsDescription = null,
            active = true
        )

        val events = (object : RegisterRuntimeEngineProfileDecision {}).decide(
            command
        )

        val event = events.filterIsInstance<RuntimeEngineProfileRegisteredEvent>().single()
        assertEquals(UUID.nameUUIDFromBytes("engine-profile-1".toByteArray()), event.runtimeEngineProfileId)
        assertEquals("PyTorch Vision Runtime Engine", event.profileName)
        assertEquals("pytorch-vision", event.pluginProfile)
        assertEquals("registry.example.com/fl/federation-learning-runtime-engine:pytorch-vision", event.runtimeEngineImage)
        assertEquals(command.imageDigest, event.imageDigest)
        assertEquals(command.supportedModelPluginsDescription, event.supportedModelPluginsDescription)
        assertEquals(command.supportedAggregationAlgorithmsDescription, event.supportedAggregationAlgorithmsDescription)
        assertEquals(true, event.active)
    }
}
