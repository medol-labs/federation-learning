package tech.medo.trainingorchestration.domain.states

enum class TrainingRoundStateEnum {
    ParticipantsSelected,
    Running,
    CollectingUpdates,
    Aggregating,
    EvaluatingGlobalModel,
    Completed,
    Failed
}
