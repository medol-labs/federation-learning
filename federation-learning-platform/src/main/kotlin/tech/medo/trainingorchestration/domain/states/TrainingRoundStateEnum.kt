package tech.medo.trainingorchestration.domain.states

enum class TrainingRoundStateEnum {
    PARTICIPANTS_SELECTED,
    RUNNING,
    COLLECTING_UPDATES,
    AGGREGATING,
    EVALUATING_GLOBAL_MODEL,
    COMPLETED,
    FAILED
}
