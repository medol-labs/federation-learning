package tech.medo.trainingorchestration.domain.states

enum class TrainingJobStateEnum {
    Draft,
    Submitted,
    Running,
    Paused,
    Canceled,
    Completed
}
