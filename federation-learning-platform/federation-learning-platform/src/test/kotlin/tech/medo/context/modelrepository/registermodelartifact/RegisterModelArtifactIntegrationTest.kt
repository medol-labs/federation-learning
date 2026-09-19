package tech.medo.modelrepository.registermodelartifact

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.modelrepository.registermodelartifact.RegisterModelArtifactCommand
import java.util.UUID

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:register-model-artifact-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class RegisterModelArtifactIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun RegisterPullableModelArtifact() {
        val command = RegisterModelArtifactCommand(
            modelId = UUID.nameUUIDFromBytes("model-1".toByteArray()),
            modelName = "credit-risk",
            modelPlugin = "SKLEARN_LOGISTIC_REGRESSION",
            modelVersion = "v1",
            modelDescription = "Baseline credit risk classifier for federated training",
            sourceType = "EXTERNAL",
            fileId = UUID.nameUUIDFromBytes("file-1".toByteArray()),
            modelFormat = "ONNX"
        )

        commandGateway.send(command).getResultMessage().join()
    }
}
