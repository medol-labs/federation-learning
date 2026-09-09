package tech.medo.runtimemonitoring.detectruntimenoderesourcepressure

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimemonitoring.runtimenoderesourcepressure.RuntimeNodeResourcePressureSelection
import java.util.UUID;
import java.math.BigDecimal;


@Command
data class DetectRuntimeNodeResourcePressureCommand(
    val nodeId: UUID,
    val runtimeAgentId: UUID,
    val trainingJobId: UUID?,
    val pressureType: String,
    val observedValue: BigDecimal,
    val thresholdValue: BigDecimal
) {
    @TargetEntityId
    val selection: RuntimeNodeResourcePressureSelection = RuntimeNodeResourcePressureSelection(nodeId = nodeId)

}
