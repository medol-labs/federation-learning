package tech.medo.runtimemonitoring.detectruntimeagentoffline

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimemonitoring.noderuntimehealth.NodeRuntimeHealthSelection
import java.util.UUID;


@Command
data class DetectRuntimeAgentOfflineCommand(
    val nodeId: UUID,
    val runtimeAgentId: UUID,
    val federationId: UUID?,
    val federationName: String?,
    val trainingJobId: UUID?,
    val trainingJobObjective: String?,
    val roundExecutionId: UUID?,
    val runtimeNodeName: String?,
    val offlineReason: String
) {
    @TargetEntityId
    val selection: NodeRuntimeHealthSelection = NodeRuntimeHealthSelection(nodeId = nodeId)

}
