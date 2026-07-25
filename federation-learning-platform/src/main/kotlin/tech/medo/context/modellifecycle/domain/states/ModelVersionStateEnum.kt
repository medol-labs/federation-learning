package tech.medo.modellifecycle.domain.states

enum class ModelVersionStateEnum {
    CANDIDATE,
    EVALUATION_PACKAGED,
    APPROVED,
    PRODUCTION,
    ROLLED_BACK,
    RETIRED
}
