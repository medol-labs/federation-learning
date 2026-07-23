package tech.medo.runtimeagentoperations.domain.states

enum class RoundExecutionStateEnum {
    PLAN_RECEIVED,
    PLAN_ACCEPTED,
    PLAN_REJECTED,
    RUNNING,
    START_FAILED,
    RETRIED,
    COMPLETED,
    FAILED,
    UPDATE_SUBMITTED,
    RUNTIME_ENGINE_RELEASED,
    RUNTIME_ENGINE_RELEASE_HANDLED
}
