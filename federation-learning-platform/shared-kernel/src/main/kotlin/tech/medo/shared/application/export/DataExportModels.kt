package tech.medo.shared.application.export

import java.time.Instant
import java.util.UUID

data class DataExportColumn(
    val field: String,
    val label: String? = null,
    val dictionaryCode: String? = null
)

data class DataExportRequest(
    val columns: List<DataExportColumn>? = null,
    val requestedLocale: String? = null
)

data class DataExportSortOrder(
    val property: String,
    val direction: String = "ASC"
)

data class DataExportJobResponse(
    val jobId: UUID,
    val status: String,
    val fileName: String? = null,
    val errorMessage: String? = null
)

data class DataExportJobRequestMessage(
    val dataExportJobId: UUID,
    val resourceName: String,
    val criteriaJson: String,
    val sortJson: String,
    val columnsJson: String,
    val requestedLocale: String?,
    val requestedAt: Instant,
    val snapshotUpperBound: Instant,
    val requestHash: String,
    val fileName: String
)

data class DataExportExecutionTask(
    val dataExportJobId: UUID,
    val resourceName: String,
    val criteriaJson: String,
    val sortJson: String,
    val columnsJson: String,
    val requestedLocale: String?,
    val requestedAt: Instant,
    val snapshotUpperBound: Instant,
    val requestHash: String,
    val fileName: String
)

data class DataExportExecutionResult(
    val fileName: String,
    val filePath: String,
    val rowCount: Long
)
