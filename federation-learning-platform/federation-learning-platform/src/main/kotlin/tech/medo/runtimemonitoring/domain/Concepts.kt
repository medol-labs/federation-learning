package tech.medo.runtimemonitoring.domain

object Concepts {
    data object NodeRuntimeHealth {
        const val NAME = "NodeRuntimeHealth"
        val slices = listOf("RecordRuntimeTelemetry", "RuntimeTelemetryLatest", "DetectRuntimeAgentOffline", "MarkRuntimeAgentRecovered", "RuntimeHealthDashboard")
        val states = listOf("Recorded", "Healthy", "Offline")
    }

    data object RuntimeNodeResourcePressure {
        const val NAME = "RuntimeNodeResourcePressure"
        val slices = listOf("DetectRuntimeNodeResourcePressure")
        val states = listOf("PressureDetected")
    }

    data object RuntimeNodeInventory {
        const val NAME = "RuntimeNodeInventory"
        val slices = listOf("RecordRuntimeNodeInventory", "RuntimeNodeInventoryView")
        val states = listOf("Reported")
    }

    data object RuntimeNodeResourceTelemetry {
        const val NAME = "RuntimeNodeResourceTelemetry"
        val slices = listOf("RecordRuntimeNodeResourceTelemetry", "RuntimeNodeResourceLatest")
        val states = listOf("Recorded")
    }

    data object TrainingAlert {
        const val NAME = "TrainingAlert"
        val slices = listOf("RaiseTrainingAlert", "AcknowledgeTrainingAlert", "ResolveTrainingAlert", "TrainingAlertCatalog")
        val states = listOf("Raised", "Acknowledged", "Resolved")
    }
}
