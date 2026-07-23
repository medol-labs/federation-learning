package tech.medo.runtimeprovisioning.domain.states

enum class RuntimeInfrastructureStateEnum {
    REGISTERED,
    VERIFIED,
    VERIFICATION_FAILED,
    AGENT_READY,
    RUNTIME_AGENT_FAILED,
    OFFLINE,
    CONNECTED
}
