package tech.medo.datasetgovernance.definefeatureschema

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.definefeatureschema.DefineFeatureSchemaCommand



import tech.medo.datasetgovernance.featureschema.FeatureSchemaFeatureDomainVersionReservationState

@Component
class DefineFeatureSchemaCommandHandler(
    private val decision: DefineFeatureSchemaDecision
) {
    @CommandHandler
    fun handle(
        command: DefineFeatureSchemaCommand,
        @InjectEntity(idProperty = "featureSchemaFeatureDomainVersionSelection") featureSchemaFeatureDomainVersionReservation: FeatureSchemaFeatureDomainVersionReservationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, featureSchemaFeatureDomainVersionReservation))
    }
}
