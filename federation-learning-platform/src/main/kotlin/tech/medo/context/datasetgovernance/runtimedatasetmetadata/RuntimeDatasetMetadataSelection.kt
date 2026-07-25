package tech.medo.datasetgovernance.runtimedatasetmetadata

import java.util.UUID;


data class RuntimeDatasetMetadataSelection(
    val metadataReportId: UUID
)

object RuntimeDatasetMetadataTags {
    const val METADATA_REPORT_ID = "metadataReportId"
}

object RuntimeDatasetMetadataMetadata {
    val concepts = listOf("RuntimeDatasetMetadata")
}
