package tech.medo.infrastructure.secondary.trainingorchestration.participantexecutionplan.dispatchparticipantexecutionplan

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientResponseException
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanInput
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanResult
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanService

@Component
class RuntimeAgentDispatchParticipantExecutionPlanAdapter(
    private val client: RuntimeAgentExecutionPlanClient
) : DispatchParticipantExecutionPlanService {
    private val log = LoggerFactory.getLogger(RuntimeAgentDispatchParticipantExecutionPlanAdapter::class.java)

    override fun supports(input: DispatchParticipantExecutionPlanInput): Boolean = true

    override fun execute(input: DispatchParticipantExecutionPlanInput): DispatchParticipantExecutionPlanResult {
        val request = ReceiveParticipantExecutionPlanRequest(
            executionPlanId = input.executionPlanId,
            executionSessionId = input.executionSessionId,
            trainingJobId = input.trainingJobId,
            trainingRunConfigurationId = input.trainingRunConfigurationId,
            featureSchemaId = input.featureSchemaId,
            roundId = input.roundId,
            roundNumber = input.roundNumber,
            runtimeId = input.runtimeId,
            organizationId = input.organizationId,
            baseModelVersionId = input.baseModelVersionId
        )

        return try {
            log.info(
                "Dispatching participant execution plan to runtime agent. executionPlanId={}, trainingJobId={}, roundId={}, runtimeId={}",
                input.executionPlanId,
                input.trainingJobId,
                input.roundId,
                input.runtimeId
            )
            client.receiveParticipantExecutionPlan(request)
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
