package tech.medo.domain.trainingorchestration.selecttrainingroundparticipants

import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.events.TrainingRoundParticipantSelectionFailedEvent
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsDecision
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsResult
import java.math.BigDecimal
import java.time.LocalDateTime
import tech.medo.trainingorchestration.traininground.TrainingRoundTrainingJobIdPreviousRoundNumberReservationState

@Component
class SelectTrainingRoundParticipantsDecisionComponent : SelectTrainingRoundParticipantsDecision {
    override fun decide(
        command: SelectTrainingRoundParticipantsCommand,
        reservation: TrainingRoundTrainingJobIdPreviousRoundNumberReservationState,
        portResult: SelectTrainingRoundParticipantsResult,
        now: LocalDateTime
    ): List<Any> {
        if (reservation.reserved) return emptyList()
        return when (portResult) {
            is SelectTrainingRoundParticipantsResult.Succeeded -> {
                require(portResult.minimumNodesPerRound > 0 && portResult.selectedRuntimeCount >= portResult.minimumNodesPerRound)
                require(portResult.roundNumber == Math.addExact(command.previousRoundNumber, 1))
                super.decide(command, reservation, portResult, now)
            }
            is SelectTrainingRoundParticipantsResult.Rejected -> listOf(
                TrainingRoundParticipantSelectionFailedEvent(
                    trainingJobId = command.trainingJobId,
                    federationId = command.federationId,
                    federationName = command.federationName,
                    trainingRunConfigurationId = command.trainingRunConfigurationId,
                    configurationName = command.configurationName,
                    featureSchemaId = command.featureSchemaId,
                    featureDomain = command.featureDomain,
                    featureSchemaVersion = command.featureSchemaVersion,
                    trainingJobObjective = command.trainingJobObjective,
                    roundId = command.roundId,
                    previousRoundNumber = command.previousRoundNumber,
                    roundNumber = portResult.roundNumber,
                    maxRounds = portResult.maxRounds,
                    minimumAccuracy = portResult.minimumAccuracy,
                    aggregationAlgorithm = portResult.aggregationAlgorithm,
                    personalizationMode = portResult.personalizationMode,
                    personalizationScope = portResult.personalizationScope,
                    personalizedArtifactPolicy = portResult.personalizedArtifactPolicy,
                    personalizationMetric = portResult.personalizationMetric,
                    minimumPersonalizationLift = portResult.minimumPersonalizationLift,
                    minimumNodesPerRound = portResult.minimumNodesPerRound,
                    secureAggregationRequired = portResult.secureAggregationRequired,
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
                    federationId = command.federationId,
                    federationName = command.federationName,
                    trainingRunConfigurationId = command.trainingRunConfigurationId,
                    configurationName = command.configurationName,
                    featureSchemaId = command.featureSchemaId,
                    featureDomain = command.featureDomain,
                    featureSchemaVersion = command.featureSchemaVersion,
                    trainingJobObjective = command.trainingJobObjective,
                    roundId = command.roundId,
                    previousRoundNumber = command.previousRoundNumber,
                    roundNumber = Math.addExact(command.previousRoundNumber, 1),
                    maxRounds = 0,
                    minimumAccuracy = BigDecimal.ZERO,
                    aggregationAlgorithm = "UNKNOWN",
                    personalizationMode = null,
                    personalizationScope = null,
                    personalizedArtifactPolicy = null,
                    personalizationMetric = null,
                    minimumPersonalizationLift = null,
                    minimumNodesPerRound = 0,
                    secureAggregationRequired = false,
                    selectedOrganizationIds = emptyList(),
                    selectedRuntimeIds = emptyList(),
                    selectedParticipants = emptyList(),
                    selectedOrganizationCount = 0,
                    selectedRuntimeCount = 0,
                    failureReason = portResult.failureReason
                )
            )
        }
    }

}
