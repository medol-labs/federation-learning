package tech.medo.trainingorchestration.generateparticipantexecutionplan

import tech.medo.trainingorchestration.events.TrainingRoundStartedEvent

interface ParticipantExecutionPlanCommandFactory {
    fun buildCommands(event: TrainingRoundStartedEvent): List<GenerateParticipantExecutionPlanCommand>
}
