package tech.medo.infrastructure.secondary.trainingorchestration.participantexecutionplan.dispatchparticipantexecutionplan

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientResponseException
import tech.medo.infrastructure.secondary.runtimeprovisioning.runtimeagentendpointcatalog.RuntimeAgentEndpointResolver
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanInput
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanResult
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanService

@Component
class RuntimeAgentDispatchParticipantExecutionPlanAdapter(
    private val client: RuntimeAgentExecutionPlanClient,
    private val endpointResolver: RuntimeAgentEndpointResolver
) : DispatchParticipantExecutionPlanService {
    private val log = LoggerFactory.getLogger(RuntimeAgentDispatchParticipantExecutionPlanAdapter::class.java)

    override fun supports(input: DispatchParticipantExecutionPlanInput): Boolean = true

    override fun execute(input: DispatchParticipantExecutionPlanInput): DispatchParticipantExecutionPlanResult {
        val request = ReceiveParticipantExecutionPlanRequest(
            executionPlanId = input.executionPlanId,
            roundExecutionId = input.executionPlanId,
            executionSessionId = input.executionSessionId,
            trainingJobId = input.trainingJobId,
            trainingRunConfigurationId = input.trainingRunConfigurationId,
            featureSchemaId = input.featureSchemaId,
            roundId = input.roundId,
            roundNumber = input.roundNumber,
            runtimeId = input.runtimeId,
            organizationId = input.organizationId,
            baseModelId = input.baseModelId,
            baseModelArtifactUri = input.baseModelArtifactUri,
            baseModelRegistryRef = input.baseModelRegistryRef,
            baseModelPlugin = input.baseModelPlugin,
            baseModelFormat = input.baseModelFormat,
            baseModelArtifactDigest = input.baseModelArtifactDigest,
            baseModelSignatureUri = input.baseModelSignatureUri,
            runtimeEngineProfileId = input.runtimeEngineProfileId,
            runtimeEngineProfileName = input.runtimeEngineProfileName,
            runtimeEnginePluginProfile = input.runtimeEnginePluginProfile,
            runtimeEngineImage = input.runtimeEngineImage,
            runtimeEngineImageDigest = input.runtimeEngineImageDigest,
            secureAggregationRequired = input.secureAggregationRequired,
            secureAggregationSessionId = input.secureAggregationSessionId,
            encryptionScheme = input.encryptionScheme,
            publicKeyVersion = input.publicKeyVersion,
            publicKeyRef = input.publicKeyRef,
            encryptedParameterScale = input.encryptedParameterScale
        )

        return try {
            val endpoint = endpointResolver.resolveConnectedEndpoint(input.runtimeId)
                ?: error("Runtime agent endpoint is not available for runtimeId=${input.runtimeId}.")
            log.info(
                "Dispatching participant execution plan to runtime agent. executionPlanId={}, trainingJobId={}, roundId={}, runtimeId={}, endpoint={}",
                input.executionPlanId,
                input.trainingJobId,
                input.roundId,
                input.runtimeId,
                endpoint
            )
            client.receiveParticipantExecutionPlan(endpoint, request)
            DispatchParticipantExecutionPlanResult.Succeeded()
        } catch (ex: RestClientResponseException) {
            throw IllegalStateException(
                "Runtime agent rejected participant execution plan with status ${ex.statusCode.value()}: ${ex.message}",
                ex
            )
        } catch (ex: Exception) {
            throw IllegalStateException(
                "Runtime agent participant execution plan dispatch unavailable: ${ex.message ?: ex.javaClass.name}",
                ex
            )
        }
    }
}
