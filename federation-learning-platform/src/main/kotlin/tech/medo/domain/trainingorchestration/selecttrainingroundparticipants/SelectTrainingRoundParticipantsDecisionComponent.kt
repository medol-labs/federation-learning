package tech.medo.domain.trainingorchestration.selecttrainingroundparticipants

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.events.TrainingRoundParticipantsSelectedEvent
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsDecision
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsResult

@Component
class SelectTrainingRoundParticipantsDecisionComponent : SelectTrainingRoundParticipantsDecision {
    private val log = LoggerFactory.getLogger(SelectTrainingRoundParticipantsDecisionComponent::class.java)

    override fun decide(
        command: SelectTrainingRoundParticipantsCommand,
        portResult: SelectTrainingRoundParticipantsResult
    ): List<Any> =
        when (portResult) {
            is SelectTrainingRoundParticipantsResult.Succeeded -> decideSucceeded(command, portResult)
        }

    private fun decideSucceeded(
        command: SelectTrainingRoundParticipantsCommand,
        result: SelectTrainingRoundParticipantsResult.Succeeded
    ): List<Any> {
        require(result.minimumNodesPerRound > 0) {
            "TrainingRunConfiguration.minimumNodesPerRound must be greater than zero."
        }
        if (result.selectedRuntimeCount < result.minimumNodesPerRound) {
            log.warn(
                "Skip training round participant selection because selected runtime count is below quorum. trainingJobId={}, featureSchemaId={}, selectedRuntimeCount={}, minimumNodesPerRound={}",
                command.trainingJobId,
                result.featureSchemaId,
                result.selectedRuntimeCount,
                result.minimumNodesPerRound
            )
            return emptyList()
        }

        log.info(
            "Selected training round participants. trainingJobId={}, featureSchemaId={}, selectedRuntimeCount={}, selectedOrganizationCount={}, minimumNodesPerRound={}",
            command.trainingJobId,
            result.featureSchemaId,
            result.selectedRuntimeCount,
            result.selectedOrganizationCount,
            result.minimumNodesPerRound
        )

        return listOf(
            TrainingRoundParticipantsSelectedEvent(
                trainingJobId = command.trainingJobId,
                trainingRunConfigurationId = result.trainingRunConfigurationId,
                featureSchemaId = result.featureSchemaId,
                roundId = result.roundId,
                roundNumber = result.roundNumber,
                minimumNodesPerRound = result.minimumNodesPerRound,
                selectedOrganizationIds = result.selectedOrganizationIds,
                selectedRuntimeIds = result.selectedRuntimeIds,
                selectedParticipants = result.selectedParticipants,
                selectedOrganizationCount = result.selectedOrganizationCount,
                selectedRuntimeCount = result.selectedRuntimeCount
            )
        )
    }
}
