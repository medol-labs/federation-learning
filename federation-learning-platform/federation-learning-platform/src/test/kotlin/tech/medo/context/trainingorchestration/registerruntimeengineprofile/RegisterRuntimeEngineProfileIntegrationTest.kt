package tech.medo.trainingorchestration.registerruntimeengineprofile

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.trainingorchestration.registerruntimeengineprofile.RegisterRuntimeEngineProfileCommand
import java.util.UUID

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:register-runtime-engine-profile-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class RegisterRuntimeEngineProfileIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
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

        commandGateway.send(command).getResultMessage().join()
    }
}
