package tech.medo.runtimemonitoring.detectruntimenoderesourcepressure

import org.springframework.stereotype.Component
import tech.medo.runtimemonitoring.detectruntimenoderesourcepressure.DetectRuntimeNodeResourcePressureCommand

import tech.medo.runtimemonitoring.events.RuntimeNodeResourcePressureDetectedEvent
import tech.medo.runtimemonitoring.runtimenoderesourcepressure.RuntimeNodeResourcePressureState





@Component
class DetectRuntimeNodeResourcePressureDecision {
    fun decide(command: DetectRuntimeNodeResourcePressureCommand): List<Any> {
        return listOf(
            RuntimeNodeResourcePressureDetectedEvent(nodeId = command.nodeId, runtimeAgentId = command.runtimeAgentId, trainingJobId = command.trainingJobId, pressureType = command.pressureType, observedValue = command.observedValue, thresholdValue = command.thresholdValue)
        )
    }
}
