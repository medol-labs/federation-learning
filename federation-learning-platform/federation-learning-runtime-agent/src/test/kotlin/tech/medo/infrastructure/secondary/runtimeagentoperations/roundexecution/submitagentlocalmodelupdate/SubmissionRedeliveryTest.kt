package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.submitagentlocalmodelupdate

import java.lang.reflect.Proxy
import java.math.BigDecimal
import java.util.UUID
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import tech.medo.runtimeagentoperations.events.RoundExecutionCompletedEvent
import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitAgentLocalModelUpdateCommand
import tech.medo.runtimeagentoperations.submitagentlocalmodelupdate.SubmitLocalModelUpdateWhenRoundExecutionCompletedProcessor

class SubmissionRedeliveryTest {
    private class InterruptedDelivery : RuntimeException()

    @Test
    fun redeliveryReusesSubmissionModelAndUploadIdentity() {
        val commands = mutableListOf<SubmitAgentLocalModelUpdateCommand>()
        val gateway = Proxy.newProxyInstance(CommandGateway::class.java.classLoader, arrayOf(CommandGateway::class.java)) { _, method, args ->
            check(method.name == "send")
            commands += args[0] as SubmitAgentLocalModelUpdateCommand
            throw InterruptedDelivery()
        } as CommandGateway
        val processor = SubmitLocalModelUpdateWhenRoundExecutionCompletedProcessor(gateway)
        val id = UUID.randomUUID()
        val event = RoundExecutionCompletedEvent(
            roundExecutionId = id, executionSessionId = UUID.randomUUID(), executionPlanId = UUID.randomUUID(),
            trainingJobId = UUID.randomUUID(), trainingRunConfigurationId = UUID.randomUUID(), roundId = UUID.randomUUID(),
            roundNumber = 2, runtimeId = UUID.randomUUID(), organizationId = UUID.randomUUID(), featureSchemaId = UUID.randomUUID(),
            personalizationMode = null, personalizationScope = null, personalizedArtifactPolicy = null, personalizationMetric = null,
            minimumPersonalizationLift = null, personalizationTargetType = null, personalizationTargetId = null,
            personalizationCohortId = null, personalizationProfileId = null, runtimeEngineJobId = "test",
            secureAggregationRequired = false, secureAggregationSessionId = null, encryptionScheme = null, publicKeyVersion = null,
            localUpdateArtifactRef = "file:///update.json", encryptedUpdateArtifactRef = null, encryptedUpdateDigest = null,
            modelUpdateArtifactRef = "file:///update.json", modelUpdateArtifactDigest = "digest", updateProtectionType = "PLAIN",
            metricsArtifactRef = null, trainingLoss = BigDecimal.ZERO, personalizedArtifactAvailable = false,
            personalizedModelId = null, personalizedModelArtifactRef = null, personalizedModelArtifactDigest = null,
            personalizedMetricsArtifactRef = null, personalizedAccuracy = null, personalizationLift = null
        )
        repeat(2) { assertThrows(InterruptedDelivery::class.java) { processor.on(event) } }
        assertEquals(commands.first(), commands.last())
        assertEquals(listOf(id, id, id), commands.first().run { listOf(modelUpdateSubmissionId, localModelId, updateArtifactId) })
    }
}
