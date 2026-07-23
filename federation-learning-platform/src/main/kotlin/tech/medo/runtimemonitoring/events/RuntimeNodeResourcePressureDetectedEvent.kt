package tech.medo.runtimemonitoring.events

import org.axonframework.eventsourcing.annotation.EventTag
import org.axonframework.messaging.eventhandling.annotation.Event
import java.util.UUID;
import java.math.BigDecimal;



@Event
data class RuntimeNodeResourcePressureDetectedEvent(
    @EventTag(key = "nodeId")
    val nodeId: UUID,
    val runtimeAgentId: UUID,
    val trainingJobId: UUID?,
    val pressureType: String,
    val observedValue: BigDecimal,
    val thresholdValue: BigDecimal
)
