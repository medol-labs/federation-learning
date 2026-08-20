package tech.medo.datasetgovernance.publishfeatureschema

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.publishfeatureschema.PublishFeatureSchemaCommand

import tech.medo.datasetgovernance.featureschema.FeatureSchemaState




@Component
class PublishFeatureSchemaCommandHandler(
    private val decision: PublishFeatureSchemaDecision
) {
    @CommandHandler
    fun handle(
        command: PublishFeatureSchemaCommand,
        @InjectEntity(idProperty = "selection") state: FeatureSchemaState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
