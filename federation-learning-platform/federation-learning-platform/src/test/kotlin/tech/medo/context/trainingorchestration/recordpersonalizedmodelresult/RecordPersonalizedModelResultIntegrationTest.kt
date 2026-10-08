package tech.medo.trainingorchestration.recordpersonalizedmodelresult

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.trainingorchestration.recordpersonalizedmodelresult.RecordPersonalizedModelResultCommand
import java.util.UUID
import java.math.BigDecimal

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:record-personalized-model-result-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class RecordPersonalizedModelResultIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun RecordPersonalizedArtifactFromAcceptedUpdate() {
        val command = RecordPersonalizedModelResultCommand(
            personalizedModelId = UUID.nameUUIDFromBytes("personalized-model-1".toByteArray()),
            modelUpdateSubmissionId = java.util.UUID.randomUUID(),
            trainingJobId = java.util.UUID.randomUUID(),
            trainingRunConfigurationId = java.util.UUID.randomUUID(),
            roundId = java.util.UUID.randomUUID(),
            roundNumber = 0,
            runtimeId = java.util.UUID.randomUUID(),
            featureSchemaId = java.util.UUID.randomUUID(),
            personalizationMode = null,
            personalizationScope = null,
            personalizedArtifactPolicy = null,
            personalizationMetric = null,
            minimumPersonalizationLift = null,
            personalizationTargetType = null,
            personalizationTargetId = null,
            personalizationCohortId = null,
            personalizationProfileId = null,
            personalizedModelArtifactRef = "",
            personalizedModelArtifactDigest = "",
            personalizedMetricsArtifactRef = null,
            personalizedAccuracy = null,
            personalizationLift = null
        )

        commandGateway.send(command).getResultMessage().join()
    }
}
