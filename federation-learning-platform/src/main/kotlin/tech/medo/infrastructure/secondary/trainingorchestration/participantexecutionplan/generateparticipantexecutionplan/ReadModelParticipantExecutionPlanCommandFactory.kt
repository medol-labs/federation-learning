package tech.medo.infrastructure.secondary.trainingorchestration.participantexecutionplan.generateparticipantexecutionplan

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent
import tech.medo.trainingorchestration.generateparticipantexecutionplan.GenerateParticipantExecutionPlanCommand
import tech.medo.trainingorchestration.generateparticipantexecutionplan.ParticipantExecutionPlanCommandFactory
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelProjection
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelRepository
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelRepository
import java.util.UUID

@Component
class ReadModelParticipantExecutionPlanCommandFactory(
    private val trainingRunConfigurationCatalog: TrainingRunConfigurationCatalogReadModelRepository,
    private val trainingRoundProgress: TrainingRoundProgressReadModelRepository
) : ParticipantExecutionPlanCommandFactory {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun buildCommands(event: TrainingRoundStartedEvent): List<GenerateParticipantExecutionPlanCommand> {
        val participants = event.selectedParticipants.distinctBy { it.runtimeId }
        if (participants.isEmpty()) {
            return emptyList()
        }

        val baseModel = resolveBaseModel(event)
        log.info(
            "Generating participant execution plans. trainingJobId={}, roundId={}, roundNumber={}, participantCount={}, baseModelId={}, artifactUri={}",
            event.trainingJobId,
            event.roundId,
            event.roundNumber,
            participants.size,
            baseModel.baseModelId,
            baseModel.baseModelArtifactUri
        )

        return participants.map { participant ->
            GenerateParticipantExecutionPlanCommand(
                trainingJobId = event.trainingJobId,
                trainingRunConfigurationId = event.trainingRunConfigurationId,
                featureSchemaId = event.featureSchemaId,
                roundId = event.roundId,
                roundNumber = event.roundNumber,
                runtimeId = participant.runtimeId,
                organizationId = participant.organizationId,
                baseModelId = baseModel.baseModelId,
                baseModelArtifactUri = baseModel.baseModelArtifactUri,
                baseModelRegistryRef = baseModel.baseModelRegistryRef,
                baseModelFormat = baseModel.baseModelFormat,
                baseModelArtifactDigest = baseModel.baseModelArtifactDigest,
                baseModelSignatureUri = baseModel.baseModelSignatureUri
            )
        }
    }

    private fun resolveBaseModel(event: TrainingRoundStartedEvent): BaseModelSnapshot {
        previousRoundSnapshot(event)?.let { return it }

        val configuration = trainingRunConfigurationCatalog.findProjectionById(event.trainingRunConfigurationId)
            ?: error("Training run configuration ${event.trainingRunConfigurationId} is required to generate participant execution plans.")

        return BaseModelSnapshot(
            baseModelId = configuration.initialModelId
                ?: error("TrainingRunConfiguration ${event.trainingRunConfigurationId} does not provide initialModelId."),
            baseModelArtifactUri = configuration.initialModelArtifactUri.orEmpty(),
            baseModelRegistryRef = configuration.initialModelRegistryRef.orEmpty(),
            baseModelFormat = configuration.initialModelFormat.orEmpty(),
            baseModelArtifactDigest = configuration.initialModelArtifactDigest.orEmpty(),
            baseModelSignatureUri = configuration.initialModelSignatureUri
        )
    }

    private fun previousRoundSnapshot(event: TrainingRoundStartedEvent): BaseModelSnapshot? {
        if (event.roundNumber <= 1) {
            return null
        }

        val previousRound = trainingRoundProgress.findProjectionsByTrainingJobId(event.trainingJobId)
            .filter { projection ->
                val roundNumber = projection.roundNumber
                roundNumber != null && roundNumber < event.roundNumber
            }
            .filter { it.aggregatedModelId != null }
            .maxByOrNull { it.roundNumber ?: 0 }

        return previousRound?.toBaseModelSnapshot()
    }

    private fun TrainingRoundProgressReadModelProjection.toBaseModelSnapshot(): BaseModelSnapshot? {
        // TrainingRoundProgress currently carries only aggregatedModelId. It does not expose
        // the immutable artifact snapshot required to safely dispatch a later-round base model.
        return null
    }
}

private data class BaseModelSnapshot(
    val baseModelId: UUID,
    val baseModelArtifactUri: String,
    val baseModelRegistryRef: String,
    val baseModelFormat: String,
    val baseModelArtifactDigest: String,
    val baseModelSignatureUri: String?
)
