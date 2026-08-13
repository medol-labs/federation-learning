package tech.medo.infrastructure.secondary.trainingorchestration.participantexecutionplan.dispatchparticipantexecutionplan

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanInput
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanResult
import java.util.UUID

class RuntimeAgentDispatchParticipantExecutionPlanAdapterTest {
    @Test
    fun dispatchesParticipantExecutionPlanToRuntimeAgent() {
        val client = RecordingRuntimeAgentExecutionPlanClient()
        val adapter = RuntimeAgentDispatchParticipantExecutionPlanAdapter(client)
        val input = input()

        val result = adapter.execute(input)

        assertTrue(result is DispatchParticipantExecutionPlanResult.Succeeded)
        assertEquals(input.executionPlanId, client.request?.executionPlanId)
        assertEquals(input.executionSessionId, client.request?.executionSessionId)
        assertEquals(input.trainingJobId, client.request?.trainingJobId)
        assertEquals(input.trainingRunConfigurationId, client.request?.trainingRunConfigurationId)
        assertEquals(input.featureSchemaId, client.request?.featureSchemaId)
        assertEquals(input.roundId, client.request?.roundId)
        assertEquals(input.roundNumber, client.request?.roundNumber)
        assertEquals(input.runtimeId, client.request?.runtimeId)
        assertEquals(input.organizationId, client.request?.organizationId)
        assertEquals(input.baseModelId, client.request?.baseModelId)
        assertEquals(input.baseModelArtifactUri, client.request?.baseModelArtifactUri)
        assertEquals(input.baseModelRegistryRef, client.request?.baseModelRegistryRef)
        assertEquals(input.baseModelFormat, client.request?.baseModelFormat)
        assertEquals(input.baseModelArtifactDigest, client.request?.baseModelArtifactDigest)
        assertEquals(input.baseModelSignatureUri, client.request?.baseModelSignatureUri)
    }

    private class RecordingRuntimeAgentExecutionPlanClient : RuntimeAgentExecutionPlanClient {
        var request: ReceiveParticipantExecutionPlanRequest? = null

        override fun receiveParticipantExecutionPlan(
            request: ReceiveParticipantExecutionPlanRequest
        ): ReceiveParticipantExecutionPlanResponse {
            this.request = request
            return ReceiveParticipantExecutionPlanResponse(executionPlanId = request.executionPlanId)
        }
    }

    private fun input(): DispatchParticipantExecutionPlanInput =
        DispatchParticipantExecutionPlanInput(
            executionPlanId = uuid("11111111-1111-4111-8111-111111111111"),
            executionSessionId = uuid("22222222-2222-4222-8222-222222222222"),
            trainingJobId = uuid("33333333-3333-4333-8333-333333333333"),
            trainingRunConfigurationId = uuid("44444444-4444-4444-8444-444444444444"),
            featureSchemaId = uuid("55555555-5555-4555-8555-555555555555"),
            roundId = uuid("66666666-6666-4666-8666-666666666666"),
            roundNumber = 1,
            runtimeId = uuid("77777777-7777-4777-8777-777777777777"),
            organizationId = uuid("88888888-8888-4888-8888-888888888888"),
            baseModelId = uuid("99999999-9999-4999-8999-999999999999"),
            baseModelArtifactUri = "oci://registry.example.com/fl/model@sha256:abc",
            baseModelRegistryRef = "oci://registry.example.com/fl",
            baseModelFormat = "ONNX",
            baseModelArtifactDigest = "sha256:abc",
            baseModelSignatureUri = "oci://registry.example.com/fl/model.sig"
        )

    private fun uuid(value: String): UUID = UUID.fromString(value)
}
