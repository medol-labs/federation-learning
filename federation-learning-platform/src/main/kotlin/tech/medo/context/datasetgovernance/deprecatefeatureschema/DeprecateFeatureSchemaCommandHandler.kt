package tech.medo.datasetgovernance.deprecatefeatureschema

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.deprecatefeatureschema.DeprecateFeatureSchemaCommand

import tech.medo.datasetgovernance.featureschema.FeatureSchemaState




@Component
class DeprecateFeatureSchemaCommandHandler(
    private val decision: DeprecateFeatureSchemaDecision
) {
    @CommandHandler
    fun handle(
        command: DeprecateFeatureSchemaCommand,
        @InjectEntity(idProperty = "selection") state: FeatureSchemaState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
