package tech.medo.runtimeprovisioning.domain.states

enum class RuntimeInfrastructureStateEnum {
    Planned,
    Registered,
    Prepared,
    Verified,
    VerificationFailed,
    AgentReady,
    RuntimeAgentFailed,
    Offline,
    Connected
}
