package tech.medo.modellifecycle.domain.states

enum class ModelStateEnum {
    CANDIDATE,
    EVALUATION_PACKAGED,
    APPROVED,
    PRODUCTION,
    ROLLED_BACK,
    RETIRED
}
