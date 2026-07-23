package tech.medo.datasetgovernance.domain

object Concepts {
    data object FeatureSchema {
        const val NAME = "FeatureSchema"
        val slices = listOf("DefineFeatureSchema", "PublishFeatureSchema", "DeprecateFeatureSchema", "RetireFeatureSchema", "FeatureSchemaCatalog", "SupersedeFeatureSchemaVersion", "MarkCurrentRecommendedFeatureSchemaVersion", "CurrentRecommendedFeatureSchemaCatalog")
        val states = listOf("Draft", "Published", "Deprecated", "Retired")
    }

    data object Dataset {
        const val NAME = "Dataset"
        val slices = listOf("DeclareDataset", "RecordRuntimeDatasetMetadata", "ValidateDatasetContract", "RejectDatasetForTraining", "ApproveDatasetForTraining", "RevokeDatasetTrainingApproval", "DatasetCapability", "DatasetReadiness")
        val states = listOf("Registered", "MetadataReported", "ContractValidationCompleted", "Approved", "Rejected", "ApprovalRevoked")
    }
}
