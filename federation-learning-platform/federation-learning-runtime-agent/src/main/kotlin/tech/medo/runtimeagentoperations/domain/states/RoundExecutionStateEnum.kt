package tech.medo.runtimeagentoperations.domain.states

enum class RoundExecutionStateEnum {
    PlanReceived,
    PlanAccepted,
    PlanRejected,
    Running,
    StartFailed,
    Retried,
    Completed,
    Failed,
    UpdateSubmitted,
    RuntimeEngineReleased,
    RuntimeEngineReleaseHandled
}
