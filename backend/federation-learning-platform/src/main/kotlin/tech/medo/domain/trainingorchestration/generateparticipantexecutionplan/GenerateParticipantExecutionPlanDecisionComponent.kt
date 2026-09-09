package tech.medo.domain.trainingorchestration.generateparticipantexecutionplan

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.events.ParticipantExecutionPlanGeneratedEvent
import tech.medo.trainingorchestration.generateparticipantexecutionplan.GenerateParticipantExecutionPlanCommand
import tech.medo.trainingorchestration.generateparticipantexecutionplan.GenerateParticipantExecutionPlanDecision
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelProjection
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelRepository
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelRepository
import java.util.UUID

@Component
class GenerateParticipantExecutionPlanDecisionComponent(
    private val trainingRunConfigurationCatalog: TrainingRunConfigurationCatalogReadModelRepository,
    private val trainingRoundProgress: TrainingRoundProgressReadModelRepository,
) : GenerateParticipantExecutionPlanDecision {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun decide(command: GenerateParticipantExecutionPlanCommand): List<Any> {
        val baseModel = resolveBaseModel(command)
        log.info(
            "Generated participant execution plan. trainingJobId={}, roundId={}, roundNumber={}, runtimeId={}, baseModelId={}, artifactUri={}",
            command.trainingJobId,
            command.roundId,
            command.roundNumber,
            command.runtimeId,
            baseModel.baseModelId,
            baseModel.baseModelArtifactUri,
        )

        return listOf(
            ParticipantExecutionPlanGeneratedEvent(
                executionPlanId = command.executionPlanId,
                executionSessionId = command.executionSessionId,
                trainingJobId = command.trainingJobId,
                trainingRunConfigurationId = command.trainingRunConfigurationId,
                featureSchemaId = command.featureSchemaId,
                roundId = command.roundId,
                roundNumber = command.roundNumber,
                runtimeId = command.runtimeId,
                organizationId = command.organizationId,
                baseModelId = baseModel.baseModelId,
                baseModelArtifactUri = baseModel.baseModelArtifactUri,
                baseModelRegistryRef = baseModel.baseModelRegistryRef,
                baseModelFormat = baseModel.baseModelFormat,
                baseModelArtifactDigest = baseModel.baseModelArtifactDigest,
                baseModelSignatureUri = baseModel.baseModelSignatureUri,
                secureAggregationRequired = command.secureAggregationRequired,
                secureAggregationSessionId = command.secureAggregationSessionId,
                encryptionScheme = command.encryptionScheme,
                publicKeyVersion = command.publicKeyVersion,
                publicKeyRef = command.publicKeyRef,
                encryptedParameterScale = command.encryptedParameterScale,
            ),
        )
    }

    private fun resolveBaseModel(command: GenerateParticipantExecutionPlanCommand): BaseModelSnapshot {
        previousRoundSnapshot(command)?.let { return it }

        val configuration = trainingRunConfigurationCatalog.findProjectionById(command.trainingRunConfigurationId)
            ?: error(
                "Training run configuration ${command.trainingRunConfigurationId} is required to generate participant execution plans.",
            )

        return BaseModelSnapshot(
            baseModelId = configuration.initialModelId
                ?: error("TrainingRunConfiguration ${command.trainingRunConfigurationId} does not provide initialModelId."),
            baseModelArtifactUri = configuration.initialModelArtifactUri.orEmpty(),
            baseModelRegistryRef = configuration.initialModelRegistryRef.orEmpty(),
            baseModelFormat = configuration.initialModelFormat.orEmpty(),
            baseModelArtifactDigest = configuration.initialModelArtifactDigest.orEmpty(),
            baseModelSignatureUri = configuration.initialModelSignatureUri,
        )
    }

    private fun previousRoundSnapshot(command: GenerateParticipantExecutionPlanCommand): BaseModelSnapshot? {
        if (command.roundNumber <= 1) {
            return null
        }

        val previousRound = trainingRoundProgress.findProjectionsByTrainingJobId(command.trainingJobId)
            .filter { projection ->
                val roundNumber = projection.roundNumber
                roundNumber != null && roundNumber < command.roundNumber
            }
            .filter { it.aggregatedModelId != null }
            .maxByOrNull { it.roundNumber ?: 0 }

        return previousRound?.toBaseModelSnapshot()
    }

    private fun TrainingRoundProgressReadModelProjection.toBaseModelSnapshot(): BaseModelSnapshot? {
        // The round progress projection currently exposes only aggregatedModelId, not the
        // immutable model artifact snapshot required to dispatch the next round safely.
        return null
    }
}

private data class BaseModelSnapshot(
    val baseModelId: UUID,
    val baseModelArtifactUri: String,
    val baseModelRegistryRef: String,
    val baseModelFormat: String,
    val baseModelArtifactDigest: String,
    val baseModelSignatureUri: String?,
)
