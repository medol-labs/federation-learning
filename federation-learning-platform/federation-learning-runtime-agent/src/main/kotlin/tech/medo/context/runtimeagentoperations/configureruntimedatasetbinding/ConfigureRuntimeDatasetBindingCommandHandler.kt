package tech.medo.runtimeagentoperations.configureruntimedatasetbinding

import org.axonframework.messaging.commandhandling.annotation.CommandHandler
import org.axonframework.messaging.eventhandling.gateway.EventAppender
import org.axonframework.modelling.annotation.InjectEntity
import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.configureruntimedatasetbinding.ConfigureRuntimeDatasetBindingCommand



import tech.medo.runtimeagentoperations.runtimedatasetbinding.RuntimeDatasetBindingRuntimeIdDatasetIdReservationState

@Component
class ConfigureRuntimeDatasetBindingCommandHandler(
    private val decision: ConfigureRuntimeDatasetBindingDecision
) {
    @CommandHandler
    fun handle(
        command: ConfigureRuntimeDatasetBindingCommand,
        @InjectEntity(idProperty = "runtimeDatasetBindingRuntimeIdDatasetIdSelection") runtimeDatasetBindingRuntimeIdDatasetIdReservation: RuntimeDatasetBindingRuntimeIdDatasetIdReservationState,
        eventAppender: EventAppender
    ) {
        eventAppender.append(decision.decide(command, runtimeDatasetBindingRuntimeIdDatasetIdReservation))
    }
}
