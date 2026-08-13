package tech.medo.trainingorchestration.domain.states

enum class TrainingJobStateEnum {
    DRAFT,
    SUBMITTED,
    RUNNING,
    PAUSED,
    CANCELED,
    COMPLETED
}
