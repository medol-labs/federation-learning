package tech.medo.runtimeagentoperations.declaredataset

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import tech.medo.runtimeagentoperations.declaredataset.DeclareDatasetCommand
import tech.medo.runtimeagentoperations.domain.types.FeatureDefinition
import tech.medo.runtimeagentoperations.domain.types.LabelDefinition
import java.util.UUID;

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:declare-dataset-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory",
    "runtime-agent.feature-schema-lookup.enabled=false"
])
class DeclareDatasetIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun DeclareDatasetWithFeatureSchemaSnapshot() {
        val command = DeclareDatasetCommand(
            datasetId = UUID.fromString("11111111-1111-4111-8111-111111111111"),
            organizationId = UUID.fromString("22222222-2222-4222-8222-222222222222"),
            featureSchemaId = UUID.fromString("33333333-3333-4333-8333-333333333333"),
            datasetName = "credit-risk",
            datasetUsage = "TRAINING"
        )

        commandGateway.send(command).getResultMessage().join()
    }

    @TestConfiguration
    class LocalDeclareDatasetServiceConfig {
        @Bean
        fun localDeclareDatasetService(): DeclareDatasetService =
            object : DeclareDatasetService {
                override fun execute(input: DeclareDatasetInput): DeclareDatasetResult =
                    DeclareDatasetResult.Succeeded(
                        features = listOf(
                            FeatureDefinition(
                                featureName = "age",
                                dataType = "INTEGER",
                                required = true,
                                nullable = false,
                                description = "Patient age",
                                validationRules = emptyList(),
                                defaultValue = null,
                                isIdentifier = false,
                                isSensitive = false,
                                encodingStrategy = null,
                                featureTags = emptyList()
                            )
                        ),
                        labels = listOf(
                            LabelDefinition(
                                labelName = "risk",
                                dataType = "STRING",
                                cardinality = 2,
                                classLabels = listOf("low", "high"),
                                isMultilabel = false,
                                description = "Risk label",
                                validationRules = emptyList(),
                                defaultValue = null
                            )
                        )
                    )
            }
    }
}
