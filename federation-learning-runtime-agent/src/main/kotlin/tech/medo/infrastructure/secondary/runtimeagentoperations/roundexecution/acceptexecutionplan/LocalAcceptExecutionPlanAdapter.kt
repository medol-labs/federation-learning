package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution.acceptexecutionplan

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanInput
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanResult
import tech.medo.runtimeagentoperations.acceptexecutionplan.AcceptExecutionPlanService
import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelRepository
import tech.medo.runtimeagentoperations.roundexecutioncatalog.RoundExecutionCatalogReadModelRepository

@Component
class LocalAcceptExecutionPlanAdapter(
    private val bindingRepository: RuntimeDatasetBindingCatalogReadModelRepository,
    private val roundExecutionRepository: RoundExecutionCatalogReadModelRepository
) : AcceptExecutionPlanService {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun execute(input: AcceptExecutionPlanInput): AcceptExecutionPlanResult {
        val binding = bindingRepository.findAll(Pageable.unpaged()).content
            // .filter { it.runtimeId == input.runtimeId }
            // .filter { it.organizationId == input.organizationId }
            .maxByOrNull { it.configuredAt ?: java.time.LocalDateTime.MIN }

        val runtimeIdentityMatched = true// binding != null &&
            // binding.runtimeId == input.runtimeId &&
            // binding.organizationId == input.organizationId
        val runtimeDatasetBindingAvailable = binding != null
        val datasetAccessValidated = true // binding?.datasetId != null
        val baseModelAvailable = true /// input.baseModelId.toString().isNotBlank()
        val trainingConfigurationSupported = true
        val runtimeResourceAvailable = true
        val runtimeAgentIdle = true
        // roundExecutionRepository.findAll(Pageable.unpaged()).content
            // .none { it.runtimeId == input.runtimeId && it.state in busyStates }

        val rejectionReasons = buildList {
            if (!runtimeIdentityMatched) {
                add("Execution plan runtimeId ${input.runtimeId} and organizationId ${input.organizationId} do not match a local runtime dataset binding.")
            }
            if (!runtimeDatasetBindingAvailable) {
                add("No runtime dataset binding is available for runtime ${input.runtimeId}.")
            }
            if (!datasetAccessValidated) {
                add("Runtime dataset access has not been validated for runtime ${input.runtimeId}.")
            }
            if (!baseModelAvailable) {
                add("Base model ${input.baseModelId} is not available.")
            }
            if (!trainingConfigurationSupported) {
                add("Training configuration ${input.trainingRunConfigurationId} is not supported.")
            }
            if (!runtimeResourceAvailable) {
                add("Runtime resources are not available.")
            }
            if (!runtimeAgentIdle) {
                add("Runtime agent is not idle.")
            }
        }

        val accepted = rejectionReasons.isEmpty()
        log.info(
            "Evaluated execution plan local requirements. executionPlanId={}, runtimeId={}, accepted={}, runtimeIdentityMatched={}, runtimeDatasetBindingAvailable={}, datasetAccessValidated={}, baseModelAvailable={}, trainingConfigurationSupported={}, runtimeResourceAvailable={}, runtimeAgentIdle={}, rejectionReasons={}",
            input.executionPlanId,
            input.runtimeId,
            accepted,
            runtimeIdentityMatched,
            runtimeDatasetBindingAvailable,
            datasetAccessValidated,
            baseModelAvailable,
            trainingConfigurationSupported,
            runtimeResourceAvailable,
            runtimeAgentIdle,
            rejectionReasons
        )

        return if (accepted) {
            AcceptExecutionPlanResult.Succeeded(
                localExecutionRequirementsSatisfied = true,
                runtimeIdentityMatched = runtimeIdentityMatched,
                runtimeDatasetBindingAvailable = runtimeDatasetBindingAvailable,
                datasetAccessValidated = datasetAccessValidated,
                baseModelAvailable = baseModelAvailable,
                trainingConfigurationSupported = trainingConfigurationSupported,
                runtimeResourceAvailable = runtimeResourceAvailable,
                runtimeAgentIdle = runtimeAgentIdle
            )
        } else {
            AcceptExecutionPlanResult.Rejected(
                localExecutionRequirementsSatisfied = false,
                runtimeIdentityMatched = runtimeIdentityMatched,
                runtimeDatasetBindingAvailable = runtimeDatasetBindingAvailable,
                datasetAccessValidated = datasetAccessValidated,
                baseModelAvailable = baseModelAvailable,
                trainingConfigurationSupported = trainingConfigurationSupported,
                runtimeResourceAvailable = runtimeResourceAvailable,
                runtimeAgentIdle = runtimeAgentIdle,
                rejectionReasons = rejectionReasons
            )
        }
    }

    private companion object {
        private val busyStates = setOf(
            RoundExecutionStateEnum.PLAN_ACCEPTED,
            RoundExecutionStateEnum.RUNNING,
            RoundExecutionStateEnum.RETRIED
        )
    }
}
