package tech.medo.modellifecycle.registercandidatemodel

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.modellifecycle.registercandidatemodel.RegisterCandidateModelCommand
import java.util.UUID;
import java.math.BigDecimal;

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:register-candidate-model-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class RegisterCandidateModelIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun RegisterCandidateModelintegration() {
        val command = RegisterCandidateModelCommand(
            modelVersionId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            finalRoundId = java.util.UUID.randomUUID(),
            modelArtifactId = java.util.UUID.randomUUID(),
            modelHash = "",
            evaluationReportId = java.util.UUID.randomUUID(),
            finalGlobalAccuracy = java.math.BigDecimal.ZERO
        )

        commandGateway.send(command).getResultMessage().join()
    }
}
