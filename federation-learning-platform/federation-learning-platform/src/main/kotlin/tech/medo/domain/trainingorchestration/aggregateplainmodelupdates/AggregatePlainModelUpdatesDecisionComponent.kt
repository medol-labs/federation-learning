package tech.medo.domain.trainingorchestration.aggregateplainmodelupdates

import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesDecision
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesCommand
import tech.medo.trainingorchestration.aggregateplainmodelupdates.AggregatePlainModelUpdatesResult
import tech.medo.trainingorchestration.traininground.TrainingRoundState
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelRepository
import java.time.LocalDateTime

@Component
class AggregatePlainModelUpdatesDecisionComponent(
    private val configurations: TrainingRunConfigurationCatalogReadModelRepository,
) : AggregatePlainModelUpdatesDecision {
    override fun decide(
        command: AggregatePlainModelUpdatesCommand,
        state: TrainingRoundState,
        portResult: AggregatePlainModelUpdatesResult,
        now: LocalDateTime,
    ): List<Any> {
        if (portResult !is AggregatePlainModelUpdatesResult.Succeeded) {
            return super.decide(command, state, portResult, now)
        }
        val configuration = requireNotNull(configurations.findProjectionById(command.trainingRunConfigurationId)) {
            "Training configuration is required to identify the aggregated model plugin."
        }
        val plugin = requireNotNull(configuration.initialModelPlugin?.takeIf { it.isNotBlank() }) {
            "Training configuration must provide initialModelPlugin."
        }
        require(command.modelPlugin.isBlank() || command.modelPlugin == plugin) {
            "Aggregated model plugin must match the training configuration."
        }
        return super.decide(command.copy(modelPlugin = plugin), state, portResult, now)
    }
}
