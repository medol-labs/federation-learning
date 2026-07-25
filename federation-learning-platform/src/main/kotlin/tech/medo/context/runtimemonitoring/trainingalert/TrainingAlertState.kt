package tech.medo.runtimemonitoring.trainingalert

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimemonitoring.events.TrainingAlertRaisedEvent
import tech.medo.runtimemonitoring.events.TrainingAlertAcknowledgedEvent
import tech.medo.runtimemonitoring.events.TrainingAlertResolvedEvent
import tech.medo.runtimemonitoring.domain.states.TrainingAlertStateEnum

import java.util.UUID;


@EventSourced(idType = UUID::class, tagKey = TrainingAlertTags.ALERT_ID)
class TrainingAlertState @EntityCreator constructor() {

    var currentState: TrainingAlertStateEnum? = null
    private var alertId: UUID? = null
    private var nodeId: UUID? = null
    private var trainingJobId: UUID? = null
    private var severity: String? = null
    private var message: String? = null
    private var acknowledgementNote: String? = null
    private var resolutionSummary: String? = null

    @EventSourcingHandler
    fun evolve(event: TrainingAlertRaisedEvent): TrainingAlertState = apply {
        currentState = TrainingAlertStateEnum.RAISED
        alertId = event.alertId
        nodeId = event.nodeId
        trainingJobId = event.trainingJobId
        severity = event.severity
        message = event.message
    }

    @EventSourcingHandler
    fun evolve(event: TrainingAlertAcknowledgedEvent): TrainingAlertState = apply {
        currentState = TrainingAlertStateEnum.ACKNOWLEDGED
        alertId = event.alertId
        acknowledgementNote = event.acknowledgementNote
    }

    @EventSourcingHandler
    fun evolve(event: TrainingAlertResolvedEvent): TrainingAlertState = apply {
        currentState = TrainingAlertStateEnum.RESOLVED
        alertId = event.alertId
        resolutionSummary = event.resolutionSummary
    }
}
