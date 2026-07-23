package tech.medo.runtimemonitoring.runtimenoderesourcepressure

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimemonitoring.events.RuntimeNodeResourcePressureDetectedEvent
import tech.medo.runtimemonitoring.domain.states.RuntimeNodeResourcePressureStateEnum

import java.util.UUID;
import java.math.BigDecimal;


@EventSourced(idType = UUID::class, tagKey = RuntimeNodeResourcePressureTags.NODE_ID)
class RuntimeNodeResourcePressureState @EntityCreator constructor() {

    var currentState: RuntimeNodeResourcePressureStateEnum? = null
    private var nodeId: UUID? = null
    private var runtimeAgentId: UUID? = null
    private var trainingJobId: UUID? = null
    private var pressureType: String? = null
    private var observedValue: BigDecimal? = null
    private var thresholdValue: BigDecimal? = null

    @EventSourcingHandler
    fun evolve(event: RuntimeNodeResourcePressureDetectedEvent): RuntimeNodeResourcePressureState = apply {
        currentState = RuntimeNodeResourcePressureStateEnum.PRESSURE_DETECTED
        nodeId = event.nodeId
        runtimeAgentId = event.runtimeAgentId
        trainingJobId = event.trainingJobId
        pressureType = event.pressureType
        observedValue = event.observedValue
        thresholdValue = event.thresholdValue
    }
}
