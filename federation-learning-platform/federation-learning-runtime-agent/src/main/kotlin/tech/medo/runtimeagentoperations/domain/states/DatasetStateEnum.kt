package tech.medo.runtimeagentoperations.domain.states

enum class DatasetStateEnum {
    Registered,
    ContractValidationFailed,
    ContractValidationCompleted,
    Approved,
    Rejected,
    ApprovalRevoked
}
