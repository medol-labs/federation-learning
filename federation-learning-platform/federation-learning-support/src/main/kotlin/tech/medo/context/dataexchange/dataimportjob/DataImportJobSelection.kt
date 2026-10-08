package tech.medo.dataexchange.dataimportjob

import java.util.UUID;


data class DataImportJobSelection(
    val dataImportJobId: UUID
)

object DataImportJobTags {
    const val DATA_IMPORT_JOB_ID = "dataImportJobId"
}

object DataImportJobMetadata {
    val concepts = emptyList<String>()
}
