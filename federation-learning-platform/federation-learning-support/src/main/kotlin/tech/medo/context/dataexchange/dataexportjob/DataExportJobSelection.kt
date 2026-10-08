package tech.medo.dataexchange.dataexportjob

import java.util.UUID;


data class DataExportJobSelection(
    val dataExportJobId: UUID
)

object DataExportJobTags {
    const val DATA_EXPORT_JOB_ID = "dataExportJobId"
}

object DataExportJobMetadata {
    val concepts = emptyList<String>()
}
