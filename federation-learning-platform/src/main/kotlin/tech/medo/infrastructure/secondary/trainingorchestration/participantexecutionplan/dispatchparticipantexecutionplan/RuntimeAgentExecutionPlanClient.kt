package tech.medo.infrastructure.secondary.trainingorchestration.participantexecutionplan.dispatchparticipantexecutionplan

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.util.UUID

interface RuntimeAgentExecutionPlanClient {
    fun receiveParticipantExecutionPlan(
        request: ReceiveParticipantExecutionPlanRequest
    ): ReceiveParticipantExecutionPlanResponse
}

@Component
class RestClientRuntimeAgentExecutionPlanClient(
    restClientBuilder: RestClient.Builder,
    @Value("\${training-orchestration.runtime-agent.endpoint:http://localhost:8082}") private val endpoint: String
) : RuntimeAgentExecutionPlanClient {
    private val restClient = restClientBuilder.build()

    override fun receiveParticipantExecutionPlan(
        request: ReceiveParticipantExecutionPlanRequest
    ): ReceiveParticipantExecutionPlanResponse {
        val baseUrl = endpoint.trim().removeSuffix("/")
        require(baseUrl.isNotBlank()) {
            "training-orchestration.runtime-agent.endpoint is required to dispatch participant execution plans."
        }

        return restClient.post()
            .uri("$baseUrl/roundexecution/receiveparticipantexecutionplan")
            .body(request)
            .retrieve()
            .body(ReceiveParticipantExecutionPlanResponse::class.java)
            ?: ReceiveParticipantExecutionPlanResponse()
    }
}

data class ReceiveParticipantExecutionPlanRequest(
    val executionPlanId: UUID,
    val executionSessionId: UUID,
    val trainingJobId: UUID,
    val trainingRunConfigurationId: UUID,
    val featureSchemaId: UUID,
    val roundId: UUID,
    val roundNumber: Int,
    val runtimeId: UUID,
    val organizationId: UUID,
    val baseModelId: UUID,
    val baseModelArtifactUri: String,
    val baseModelRegistryRef: String,
    val baseModelFormat: String,
    val baseModelArtifactDigest: String,
    val baseModelSignatureUri: String?
)

data class ReceiveParticipantExecutionPlanResponse(
    val executionPlanId: UUID? = null,
    val executionSessionId: UUID? = null,
    val trainingJobId: UUID? = null,
    val trainingRunConfigurationId: UUID? = null,
    val featureSchemaId: UUID? = null,
    val roundId: UUID? = null,
    val roundNumber: Int? = null,
    val runtimeId: UUID? = null,
    val organizationId: UUID? = null,
    val baseModelId: UUID? = null,
    val baseModelArtifactUri: String? = null,
    val baseModelRegistryRef: String? = null,
    val baseModelFormat: String? = null,
    val baseModelArtifactDigest: String? = null,
    val baseModelSignatureUri: String? = null
)
