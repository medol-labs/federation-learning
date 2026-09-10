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
    val federationId: UUID?,
    val federationName: String?,
    val trainingJobId: UUID?,
    val trainingJobObjective: String?,
    val roundExecutionId: UUID?,
    val runtimeNodeName: String?,
    val pressureType: String,
    val observedValue: BigDecimal,
    val thresholdValue: BigDecimal
) {
    @TargetEntityId
    val selection: RuntimeNodeResourcePressureSelection = RuntimeNodeResourcePressureSelection(nodeId = nodeId)

}
