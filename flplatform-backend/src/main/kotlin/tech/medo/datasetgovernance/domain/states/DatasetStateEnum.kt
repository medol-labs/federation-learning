package tech.medo.datasetgovernance.domain.states

enum class DatasetStateEnum {
    REGISTERED,
    METADATA_REPORTED,
    CONTRACT_VALIDATION_COMPLETED,
    APPROVED,
    REJECTED,
    APPROVAL_REVOKED
}
