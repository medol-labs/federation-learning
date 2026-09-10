package tech.medo.runtimemonitoring.detectruntimenoderesourcepressure

import tech.medo.runtimemonitoring.detectruntimenoderesourcepressure.DetectRuntimeNodeResourcePressureCommand


import tech.medo.runtimemonitoring.events.RuntimeNodeResourcePressureDetectedEvent
import tech.medo.runtimemonitoring.runtimenoderesourcepressure.RuntimeNodeResourcePressureState





interface DetectRuntimeNodeResourcePressureDecision {
    fun decide(command: DetectRuntimeNodeResourcePressureCommand): List<Any> {
        return listOf(
            RuntimeNodeResourcePressureDetectedEvent(nodeId = command.nodeId, runtimeAgentId = command.runtimeAgentId, federationId = command.federationId, federationName = command.federationName, trainingJobId = command.trainingJobId, trainingJobObjective = command.trainingJobObjective, roundExecutionId = command.roundExecutionId, runtimeNodeName = command.runtimeNodeName, pressureType = command.pressureType, observedValue = command.observedValue, thresholdValue = command.thresholdValue)
        )
    }
}
