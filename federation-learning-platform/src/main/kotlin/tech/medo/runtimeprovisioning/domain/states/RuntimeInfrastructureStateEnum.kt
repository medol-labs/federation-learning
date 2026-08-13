package tech.medo.runtimeprovisioning.domain.states

enum class RuntimeInfrastructureStateEnum {
    PLANNED,
    REGISTERED,
    VERIFIED,
    VERIFICATION_FAILED,
    AGENT_READY,
    RUNTIME_AGENT_FAILED,
    OFFLINE,
    CONNECTED
}
