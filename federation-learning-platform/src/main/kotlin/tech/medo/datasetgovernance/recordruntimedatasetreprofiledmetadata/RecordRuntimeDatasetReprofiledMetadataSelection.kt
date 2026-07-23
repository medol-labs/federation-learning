package tech.medo.datasetgovernance.recordruntimedatasetreprofiledmetadata

import java.util.UUID;


data class RecordRuntimeDatasetReprofiledMetadataSelection(
    val metadataReportId: UUID
)

object RecordRuntimeDatasetReprofiledMetadataTags {
    const val METADATA_REPORT_ID = "metadataReportId"
}

object RecordRuntimeDatasetReprofiledMetadataMetadata {
    val concepts = emptyList<String>()
}
