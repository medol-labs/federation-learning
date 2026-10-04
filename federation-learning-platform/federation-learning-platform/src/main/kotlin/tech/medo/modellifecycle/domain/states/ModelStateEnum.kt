package tech.medo.modellifecycle.domain.states

enum class ModelStateEnum {
    Candidate,
    EvaluationPackaged,
    Approved,
    Production,
    RolledBack,
    Retired
}
