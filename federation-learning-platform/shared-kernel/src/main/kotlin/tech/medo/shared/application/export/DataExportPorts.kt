package tech.medo.shared.application.export

import java.util.UUID

interface DataExportJobRequestPort {
    fun request(message: DataExportJobRequestMessage)
}

interface DataExportJobLifecyclePort {
    fun markProcessing(dataExportJobId: UUID)
    fun complete(dataExportJobId: UUID, fileName: String, filePath: String, rowCount: Long)
    fun fail(dataExportJobId: UUID, errorMessage: String)
}

interface DataExportResourceExecutor {
    val resourceName: String
    fun execute(task: DataExportExecutionTask): DataExportExecutionResult
}
