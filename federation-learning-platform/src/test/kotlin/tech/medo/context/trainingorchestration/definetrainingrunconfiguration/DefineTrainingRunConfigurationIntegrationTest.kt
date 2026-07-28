package tech.medo.trainingorchestration.definetrainingrunconfiguration

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.trainingorchestration.definetrainingrunconfiguration.DefineTrainingRunConfigurationCommand
import java.util.UUID;
import java.math.BigDecimal;

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:define-training-run-configuration-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class DefineTrainingRunConfigurationIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun DefineRunnableConfiguration() {
        val command = DefineTrainingRunConfigurationCommand(
            trainingRunConfigurationId = UUID.nameUUIDFromBytes("cfg-1".toByteArray()),
            federationId = UUID.nameUUIDFromBytes("fed-1".toByteArray()),
            featureSchemaId = UUID.nameUUIDFromBytes("schema-1".toByteArray()),
            initialModelVersionId = UUID.nameUUIDFromBytes("model-1".toByteArray()),
            strategyName = "FED_AVG",
            aggregationAlgorithm = "FEDERATED_AVERAGING",
            maxRounds = 10,
            minimumNodesPerRound = 3,
            roundTimeoutSeconds = 1800,
            nodeResponseTimeoutSeconds = 300,
            localEpochs = 2,
            batchSize = 64,
            learningRate = BigDecimal("0.01"),
            optimizer = "SGD",
            lossFunction = "CROSS_ENTROPY",
            gradientClippingNorm = null,
            secureAggregationRequired = true,
            differentialPrivacyEnabled = false,
            dpNoiseMultiplier = null,
            dpClipNorm = null,
            minimumAccuracy = BigDecimal("0.9"),
            minimumFairnessScore = null,
            failureToleranceRatio = BigDecimal("0.2")
        )

        commandGateway.send(command).getResultMessage().join()
    }
}
