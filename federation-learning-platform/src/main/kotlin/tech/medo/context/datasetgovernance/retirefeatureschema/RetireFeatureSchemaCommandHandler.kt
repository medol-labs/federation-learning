package tech.medo.datasetgovernance.retirefeatureschema

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.retirefeatureschema.RetireFeatureSchemaCommand

import tech.medo.datasetgovernance.featureschema.FeatureSchemaState




@Component
class RetireFeatureSchemaCommandHandler(
    private val decision: RetireFeatureSchemaDecision
) {
    @CommandHandler
    fun handle(
        command: RetireFeatureSchemaCommand,
        @InjectEntity(idProperty = "selection") state: FeatureSchemaState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, state))
    }
}
