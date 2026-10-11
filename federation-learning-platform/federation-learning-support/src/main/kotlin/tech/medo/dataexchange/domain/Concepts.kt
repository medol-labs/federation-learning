package tech.medo.dataexchange.domain

object Concepts {
    data object DataExportJob {
        const val NAME = "DataExportJob"
        val slices = listOf("DataExportJob", "DownloadDataExportFile")
        val states = emptyList<String>()
    }
}
