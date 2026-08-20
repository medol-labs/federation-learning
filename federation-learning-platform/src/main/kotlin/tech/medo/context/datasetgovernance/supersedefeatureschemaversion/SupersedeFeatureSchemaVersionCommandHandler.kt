package tech.medo.datasetgovernance.supersedefeatureschemaversion

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.supersedefeatureschemaversion.SupersedeFeatureSchemaVersionCommand

import tech.medo.datasetgovernance.featureschema.FeatureSchemaState




@Component
class SupersedeFeatureSchemaVersionCommandHandler(
    private val decision: SupersedeFeatureSchemaVersionDecision
) {
    @CommandHandler
    fun handle(
        command: SupersedeFeatureSchemaVersionCommand,
        @InjectEntity(idProperty = "selection") state: FeatureSchemaState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
