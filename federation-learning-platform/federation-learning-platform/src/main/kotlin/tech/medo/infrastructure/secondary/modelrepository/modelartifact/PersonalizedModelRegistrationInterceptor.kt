package tech.medo.infrastructure.secondary.modelrepository.modelartifact

import org.axonframework.messaging.commandhandling.CommandMessage
import org.axonframework.messaging.commandhandling.GenericCommandMessage
import org.axonframework.messaging.core.GenericMessage
import org.axonframework.messaging.core.MessageDispatchInterceptor
import org.axonframework.messaging.core.MessageDispatchInterceptorChain
import org.axonframework.messaging.core.MessageStream
import org.axonframework.messaging.core.unitofwork.ProcessingContext
import org.springframework.stereotype.Component
import tech.medo.modelrepository.registerpersonalizedmodelartifact.RegisterPersonalizedModelArtifactCommand
import tech.medo.trainingorchestration.trainingjobdashboard.TrainingJobDashboardReadModelRepository
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelKey
import tech.medo.trainingorchestration.trainingroundprogress.TrainingRoundProgressReadModelRepository
import tech.medo.trainingorchestration.trainingrunconfigurationcatalog.TrainingRunConfigurationCatalogReadModelRepository

@Component
class PersonalizedModelRegistrationInterceptor(
    private val jobs: TrainingJobDashboardReadModelRepository,
    private val configurations: TrainingRunConfigurationCatalogReadModelRepository,
    private val rounds: TrainingRoundProgressReadModelRepository
) : MessageDispatchInterceptor<CommandMessage> {
    override fun interceptOnDispatch(
        message: CommandMessage,
        context: ProcessingContext?,
        chain: MessageDispatchInterceptorChain<CommandMessage>
    ): MessageStream<*> {
        val command = message.payload() as? RegisterPersonalizedModelArtifactCommand
            ?: return chain.proceed(message, context)
        val resolved = resolve(command)
        val enriched = GenericCommandMessage(
            GenericMessage(message.identifier(), message.type(), resolved,
                message.metadata().mapNotNull { (key, value) -> value?.let { key to it } }.toMap()),
            message.routingKey().orElse(null), message.priority().orElse(0)
        )
        return chain.proceed(enriched, context)
    }

    internal fun resolve(command: RegisterPersonalizedModelArtifactCommand): RegisterPersonalizedModelArtifactCommand {
        val job = requireNotNull(jobs.findProjectionById(command.trainingJobId)) { "Personalized model training job was not found." }
        val configuration = requireNotNull(configurations.findProjectionById(requireNotNull(job.trainingRunConfigurationId))) {
            "Personalized model training configuration was not found."
        }
        val round = requireNotNull(rounds.findProjectionById(TrainingRoundProgressReadModelKey(command.trainingJobId, command.roundId))) {
            "Personalized model training round was not found."
        }
        val target = requireNotNull(command.personalizationTargetId) { "Personalized model target is required." }
        return command.copy(
            modelName = "personalized-${requireNotNull(command.personalizationTargetType).lowercase()}-$target",
            modelVersion = "round-${round.roundNumber}-${command.modelId}",
            modelPlugin = requireNotNull(configuration.initialModelPlugin),
            modelFormat = requireNotNull(configuration.initialModelFormat),
            sourceType = "FEDERATED_TRAINING",
            modelScope = requireNotNull(command.personalizationTargetType),
            modelRegistryRef = command.modelArtifactUri,
            modelDescription = "Personalized model produced by training job ${command.trainingJobId}, round ${round.roundNumber}.",
            trainingJobObjective = requireNotNull(job.objective),
            baseGlobalModelId = round.baseModelId
        )
    }
}
