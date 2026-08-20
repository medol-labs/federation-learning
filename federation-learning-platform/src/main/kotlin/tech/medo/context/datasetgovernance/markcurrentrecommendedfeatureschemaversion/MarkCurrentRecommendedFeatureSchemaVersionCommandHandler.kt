package tech.medo.datasetgovernance.markcurrentrecommendedfeatureschemaversion

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.markcurrentrecommendedfeatureschemaversion.MarkCurrentRecommendedFeatureSchemaVersionCommand

import tech.medo.datasetgovernance.featureschema.FeatureSchemaState




@Component
class MarkCurrentRecommendedFeatureSchemaVersionCommandHandler(
    private val decision: MarkCurrentRecommendedFeatureSchemaVersionDecision
) {
    @CommandHandler
    fun handle(
        command: MarkCurrentRecommendedFeatureSchemaVersionCommand,
        @InjectEntity(idProperty = "selection") state: FeatureSchemaState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
