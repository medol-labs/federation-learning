package tech.medo.runtimemonitoring.detectruntimeagentoffline

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.runtimemonitoring.noderuntimehealth.NodeRuntimeHealthSelection
import java.util.UUID;


@Command
data class DetectRuntimeAgentOfflineCommand(
    val nodeId: UUID,
    val runtimeAgentId: UUID,
    val offlineReason: String
) {
    @TargetEntityId
    val selection: NodeRuntimeHealthSelection = NodeRuntimeHealthSelection(nodeId = nodeId)

}
