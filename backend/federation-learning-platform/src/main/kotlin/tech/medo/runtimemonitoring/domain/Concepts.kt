package tech.medo.runtimemonitoring.domain

object Concepts {
    data object NodeRuntimeHealth {
        const val NAME = "NodeRuntimeHealth"
        val slices = listOf("RuntimeTelemetryLatest", "DetectRuntimeAgentOffline", "MarkRuntimeAgentRecovered", "RuntimeHealthDashboard")
        val states = listOf("Healthy", "Offline")
    }

    data object RuntimeNodeResourcePressure {
        const val NAME = "RuntimeNodeResourcePressure"
        val slices = listOf("DetectRuntimeNodeResourcePressure")
        val states = listOf("PressureDetected")
    }

    data object RuntimeNodeCapacity {
        const val NAME = "RuntimeNodeCapacity"
        val slices = listOf("DetectRuntimeNodeCapacityChange")
        val states = listOf("CapacityChanged")
    }

    data object RuntimeNodeInventory {
        const val NAME = "RuntimeNodeInventory"
        val slices = listOf("RecordRuntimeNodeInventory", "RuntimeNodeInventoryView")
        val states = listOf("Reported")
    }

    data object RuntimeNodeResourceTelemetry {
        const val NAME = "RuntimeNodeResourceTelemetry"
        val slices = listOf("RuntimeNodeResourceLatest")
        val states = emptyList<String>()
    }

    data object TrainingAlert {
        const val NAME = "TrainingAlert"
        val slices = listOf("RaiseTrainingAlert", "AcknowledgeTrainingAlert", "ResolveTrainingAlert", "TrainingAlertCatalog")
        val states = listOf("Raised", "Acknowledged", "Resolved")
    }

    data object AuditRecord {
        const val NAME = "AuditRecord"
        val slices = listOf("AppendAuditTrail", "AuditRecordLog")
        val states = listOf("Appended")
    }
}
