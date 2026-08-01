package tech.medo.runtimeagentoperations.domain

object Concepts {
    data object RuntimeDatasetBinding {
        const val NAME = "RuntimeDatasetBinding"
        val slices = listOf("ConfigureRuntimeDatasetBinding", "RuntimeDatasetBindingCatalog")
        val states = listOf("Configured")
    }

    data object Dataset {
        const val NAME = "Dataset"
        val slices = listOf("DeclareDataset", "ValidateDatasetContract", "RetryDatasetContractValidation", "RejectDatasetForTraining", "ApproveDatasetForTraining", "RevokeDatasetTrainingApproval", "DatasetCapability", "DatasetReadiness")
        val states = listOf("Registered", "ContractValidationCompleted", "Approved", "Rejected", "ApprovalRevoked")
    }

    data object AgentDatasetAccessValidation {
        const val NAME = "AgentDatasetAccessValidation"
        val slices = listOf("ValidateAgentDatasetAccess", "RevalidateAgentDatasetAccess", "AgentDatasetAccessValidationCatalog")
        val states = listOf("Checked")
    }

    data object AgentDatasetProfile {
        const val NAME = "AgentDatasetProfile"
        val slices = listOf("ProfileAgentDataset", "ReprofileAgentDataset")
        val states = listOf("Reported")
    }

    data object RoundExecution {
        const val NAME = "RoundExecution"
        val slices = listOf("ReceiveParticipantExecutionPlan", "AcceptExecutionPlan", "RejectExecutionPlan", "StartRoundExecution", "CompleteRoundExecution", "FailRoundExecution", "RetryRoundExecutionAfterStartFailure", "RetryRoundExecutionAfterRuntimeFailure", "SubmitAgentLocalModelUpdate", "ReleaseRuntimeEngineJobAfterCompletion", "ReleaseRuntimeEngineJobAfterFailure", "ReleaseRuntimeEngineJobAfterStartFailure", "ReleaseRuntimeEngineJobAfterRetryFailure", "ReleaseRuntimeEngineJobAfterRuntimeRetryFailure", "RoundExecutionCatalog")
        val states = listOf("PlanReceived", "PlanAccepted", "PlanRejected", "Running", "StartFailed", "Retried", "Completed", "Failed", "UpdateSubmitted", "RuntimeEngineReleased", "RuntimeEngineReleaseHandled")
    }

    data object RuntimeAgentLifecycle {
        const val NAME = "RuntimeAgentLifecycle"
        val slices = listOf("LoadRuntimeAgentBootstrapConfiguration", "ReportRuntimeAgentStarted", "ReportRuntimeInstanceSelfCheckPassed", "RuntimeAgentLifecycleCatalog")
        val states = listOf("BootstrapLoaded", "Started", "Ready")
    }

    data object AgentRuntimeInfrastructureConnection {
        const val NAME = "AgentRuntimeInfrastructureConnection"
        val slices = listOf("ReportRuntimeInstanceConnected", "AgentRuntimeInfrastructureConnectionCatalog")
        val states = listOf("Connected", "ConnectionReportFailed")
    }

    data object AgentRuntimeTelemetry {
        const val NAME = "AgentRuntimeTelemetry"
        val slices = listOf("AgentRuntimeTelemetryLatest")
        val states = emptyList<String>()
    }

    data object AgentRuntimeNodeInventory {
        const val NAME = "AgentRuntimeNodeInventory"
        val slices = listOf("ReportAgentRuntimeNodeInventory", "AgentRuntimeNodeInventoryCatalog")
        val states = listOf("Reported")
    }

    data object AgentRuntimeNodeResourceTelemetry {
        const val NAME = "AgentRuntimeNodeResourceTelemetry"
        val slices = listOf("AgentRuntimeNodeResourceLatest")
        val states = emptyList<String>()
    }
}
