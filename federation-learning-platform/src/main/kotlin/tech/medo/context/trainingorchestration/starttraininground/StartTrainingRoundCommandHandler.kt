package tech.medo.trainingorchestration.starttraininground

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundCommand
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundInput
import tech.medo.trainingorchestration.starttraininground.StartTrainingRoundService
import tech.medo.trainingorchestration.traininground.TrainingRoundState

import tech.medo.trainingorchestration.domain.states.TrainingRoundStateEnum


@Component
class StartTrainingRoundCommandHandler(
    private val decision: StartTrainingRoundDecision,
    private val startTrainingRoundService: StartTrainingRoundService
) {
    @CommandHandler
    fun handle(
        command: StartTrainingRoundCommand,
        @InjectEntity(idProperty = "trainingJobId") state: TrainingRoundState,
        eventAppender: EventAppender
    ) {
        require(state.currentState == TrainingRoundStateEnum.PARTICIPANTS_SELECTED) {
            "StartTrainingRound requires TrainingRound to be ParticipantsSelected."
        }
        val input = StartTrainingRoundInput(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, selectedOrganizationIds = command.selectedOrganizationIds, selectedRuntimeIds = command.selectedRuntimeIds, selectedOrganizationCount = command.selectedOrganizationCount, selectedRuntimeCount = command.selectedRuntimeCount, minimumNodesPerRound = command.minimumNodesPerRound, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, secureAggregationRequired = command.secureAggregationRequired, secureAggregationSessionId = command.secureAggregationSessionId, encryptionScheme = command.encryptionScheme, publicKeyVersion = command.publicKeyVersion, publicKeyRef = command.publicKeyRef, encryptedParameterScale = command.encryptedParameterScale)
        val portResult = startTrainingRoundService.execute(input)
        val now = java.time.LocalDateTime.now()

        eventAppender.append(decision.decide(command, state, portResult, now))
    }
}
