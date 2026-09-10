package tech.medo.trainingorchestration.aggregateplainmodelupdates

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesCommand
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesInput
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesService
import tech.medo.trainingorchestration.traininground.TrainingRoundState



@Component
class AggregatePlainModelUpdatesCommandHandler(
    private val decision: AggregatePlainModelUpdatesDecision,
    private val aggregatePlainModelUpdatesService: AggregatePlainModelUpdatesService
) {
    @CommandHandler
    fun handle(
        command: AggregatePlainModelUpdatesCommand,
        @InjectEntity(idProperty = "trainingJobId") state: TrainingRoundState,
        eventAppender: EventAppender
    ) {
        // TODO: validate child/member state before appending events.
        val input = AggregatePlainModelUpdatesInput(trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, trainingJobObjective = command.trainingJobObjective, featureSchemaId = command.featureSchemaId, roundId = command.roundId, roundNumber = command.roundNumber, maxRounds = command.maxRounds, minimumAccuracy = command.minimumAccuracy, aggregationAlgorithm = command.aggregationAlgorithm, aggregatedModelId = command.aggregatedModelId, modelUpdateArtifactRefs = command.modelUpdateArtifactRefs)
        val portResult = aggregatePlainModelUpdatesService.execute(input)

        eventAppender.append(decision.decide(command, state, portResult))
    }
}
