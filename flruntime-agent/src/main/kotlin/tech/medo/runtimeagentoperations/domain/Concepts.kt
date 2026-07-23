package tech.medo.runtimeagentoperations.domain

object Concepts {
    data object RuntimeDatasetBinding {
        const val NAME = "RuntimeDatasetBinding"
        val slices = listOf("ConfigureRuntimeDatasetBinding", "RuntimeDatasetBindingCatalog")
        val states = listOf("Configured")
    }

    data object RuntimeDatasetMetadata {
        const val NAME = "RuntimeDatasetMetadata"
        val slices = listOf("ProfileAgentDataset", "ReprofileAgentDataset", "RuntimeDatasetMetadataCatalog")
        val states = listOf("Reported")
    }

    data object AgentDatasetAccessValidation {
        const val NAME = "AgentDatasetAccessValidation"
        val slices = listOf("ValidateAgentDatasetAccess", "AgentDatasetAccessValidationCatalog")
        val states = listOf("Checked")
    }

    data object RoundExecution {
        const val NAME = "RoundExecution"
        val slices = listOf("ReceiveParticipantExecutionPlan", "AcceptExecutionPlan", "RejectExecutionPlan", "StartRoundExecution", "CompleteRoundExecution", "FailRoundExecution", "RetryRoundExecutionAfterStartFailure", "RetryRoundExecutionAfterRuntimeFailure", "SubmitAgentLocalModelUpdate", "ReleaseRuntimeEngineJobAfterCompletion", "ReleaseRuntimeEngineJobAfterFailure", "ReleaseRuntimeEngineJobAfterStartFailure", "ReleaseRuntimeEngineJobAfterRetryFailure", "ReleaseRuntimeEngineJobAfterRuntimeRetryFailure", "RoundExecutionCatalog")
        val states = listOf("PlanReceived", "PlanAccepted", "PlanRejected", "Running", "StartFailed", "Retried", "Completed", "Failed", "UpdateSubmitted", "RuntimeEngineReleased", "RuntimeEngineReleaseHandled")
    }

    data object RuntimeAgentLifecycle {
        const val NAME = "RuntimeAgentLifecycle"
        val slices = listOf("ReportRuntimeAgentStarted", "ReportRuntimeInstanceSelfCheckPassed", "RuntimeAgentLifecycleCatalog")
        val states = listOf("Started", "Ready")
    }

    data object AgentRuntimeInfrastructureConnection {
        const val NAME = "AgentRuntimeInfrastructureConnection"
        val slices = listOf("ReportRuntimeInstanceConnected", "AgentRuntimeInfrastructureConnectionCatalog")
        val states = listOf("Connected")
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
