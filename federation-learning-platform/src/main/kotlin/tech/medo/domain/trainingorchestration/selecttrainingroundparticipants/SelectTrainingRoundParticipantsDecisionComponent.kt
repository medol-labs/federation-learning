package tech.medo.domain.trainingorchestration.selecttrainingroundparticipants

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.events.TrainingRoundParticipantSelectionFailedEvent
import tech.medo.trainingorchestration.events.TrainingRoundParticipantsSelectedEvent
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsDecision
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsResult
import java.time.LocalDateTime
import java.util.UUID

@Component
class SelectTrainingRoundParticipantsDecisionComponent : SelectTrainingRoundParticipantsDecision {
    private val log = LoggerFactory.getLogger(SelectTrainingRoundParticipantsDecisionComponent::class.java)

    override fun decide(
        command: SelectTrainingRoundParticipantsCommand,
        portResult: SelectTrainingRoundParticipantsResult,
        now: LocalDateTime
    ): List<Any> =
        when (portResult) {
            is SelectTrainingRoundParticipantsResult.Succeeded -> decideSucceeded(command, portResult)
            is SelectTrainingRoundParticipantsResult.Rejected -> listOf(
                TrainingRoundParticipantSelectionFailedEvent(
                    trainingJobId = command.trainingJobId,
                    trainingRunConfigurationId = portResult.trainingRunConfigurationId,
                    featureSchemaId = portResult.featureSchemaId,
                    roundId = portResult.roundId,
                    roundNumber = portResult.roundNumber,
                    minimumNodesPerRound = portResult.minimumNodesPerRound,
                    selectedOrganizationIds = portResult.selectedOrganizationIds,
                    selectedRuntimeIds = portResult.selectedRuntimeIds,
                    selectedParticipants = portResult.selectedParticipants,
                    selectedOrganizationCount = portResult.selectedOrganizationCount,
                    selectedRuntimeCount = portResult.selectedRuntimeCount,
                    failureReason = portResult.failureReason
                )
            )
            is SelectTrainingRoundParticipantsResult.Unavailable -> listOf(
                TrainingRoundParticipantSelectionFailedEvent(
                    trainingJobId = command.trainingJobId,
                    trainingRunConfigurationId = UUID.randomUUID(),
                    featureSchemaId = UUID.randomUUID(),
                    roundId = UUID.randomUUID(),
                    roundNumber = 0,
                    minimumNodesPerRound = 0,
                    selectedOrganizationIds = emptyList(),
                    selectedRuntimeIds = emptyList(),
                    selectedParticipants = emptyList(),
                    selectedOrganizationCount = 0,
                    selectedRuntimeCount = 0,
                    failureReason = portResult.failureReason
                )
            )
        }

    private fun decideSucceeded(
        command: SelectTrainingRoundParticipantsCommand,
        result: SelectTrainingRoundParticipantsResult.Succeeded
    ): List<Any> {
        require(result.minimumNodesPerRound > 0) {
            "TrainingRunConfiguration.minimumNodesPerRound must be greater than zero."
        }
        if (result.selectedRuntimeCount < result.minimumNodesPerRound) {
            error("SelectTrainingRoundParticipantsResult.Succeeded cannot be below quorum.")
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
