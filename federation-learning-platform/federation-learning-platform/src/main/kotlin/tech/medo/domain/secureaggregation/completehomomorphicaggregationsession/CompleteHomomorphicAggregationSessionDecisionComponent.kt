package tech.medo.domain.secureaggregation.completehomomorphicaggregationsession

import org.springframework.stereotype.Component
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionDecision
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionCommand
import tech.medo.secureaggregation.completehomomorphicaggregationsession.CompleteHomomorphicAggregationSessionResult
import tech.medo.secureaggregation.secureaggregationsession.SecureAggregationSessionState
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelRepository

@Component
class CompleteHomomorphicAggregationSessionDecisionComponent(
    private val configurations: TrainingRunConfigurationCatalogReadModelRepository,
) : CompleteHomomorphicAggregationSessionDecision {
    override fun decide(
        command: CompleteHomomorphicAggregationSessionCommand,
        state: SecureAggregationSessionState,
        portResult: CompleteHomomorphicAggregationSessionResult,
    ): List<Any> {
        val configuration = requireNotNull(configurations.findProjectionById(command.trainingRunConfigurationId)) {
            "Training configuration is required to identify the aggregated model plugin."
        }
        val plugin = requireNotNull(configuration.initialModelPlugin?.takeIf { it.isNotBlank() }) {
            "Training configuration must provide initialModelPlugin."
        }
        require(command.modelPlugin.isBlank() || command.modelPlugin == plugin) {
            "Aggregated model plugin must match the training configuration."
        }
        return super.decide(command.copy(modelPlugin = plugin), state, portResult)
    }
}
