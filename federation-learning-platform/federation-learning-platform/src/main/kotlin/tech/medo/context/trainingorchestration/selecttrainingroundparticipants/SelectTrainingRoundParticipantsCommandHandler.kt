package tech.medo.trainingorchestration.selecttrainingroundparticipants

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsCommand
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsInput
import tech.medo.trainingorchestration.selecttrainingroundparticipants.SelectTrainingRoundParticipantsService




import tech.medo.trainingorchestration.traininground.TrainingRoundTrainingJobIdPreviousRoundNumberReservationState

@Component
class SelectTrainingRoundParticipantsCommandHandler(
    private val decision: SelectTrainingRoundParticipantsDecision,
    private val selectTrainingRoundParticipantsService: SelectTrainingRoundParticipantsService
) {
    @CommandHandler
    fun handle(
        command: SelectTrainingRoundParticipantsCommand,
        @InjectEntity(idProperty = "trainingRoundTrainingJobIdPreviousRoundNumberSelection") trainingRoundTrainingJobIdPreviousRoundNumberReservation: TrainingRoundTrainingJobIdPreviousRoundNumberReservationState,
        eventAppender: EventAppender
    ) {
        val input = SelectTrainingRoundParticipantsInput(trainingJobId = command.trainingJobId, roundId = command.roundId, previousRoundNumber = command.previousRoundNumber, federationId = command.federationId, federationName = command.federationName, trainingRunConfigurationId = command.trainingRunConfigurationId, configurationName = command.configurationName, featureSchemaId = command.featureSchemaId, featureDomain = command.featureDomain, featureSchemaVersion = command.featureSchemaVersion, trainingJobObjective = command.trainingJobObjective)
        val portResult = selectTrainingRoundParticipantsService.execute(input)
        val now = java.time.LocalDateTime.now()

        eventAppender.append(decision.decide(command, trainingRoundTrainingJobIdPreviousRoundNumberReservation, portResult, now))
    }
}
