package tech.medo.shared.application.export

import org.springframework.stereotype.Component

@Component
class DataExportResourceExecutorRegistry(executors: List<DataExportResourceExecutor>) {
    private val executorsByResourceName = executors.associateBy { it.resourceName }

    fun executor(resourceName: String): DataExportResourceExecutor =
        executorsByResourceName[resourceName]
            ?: throw IllegalArgumentException("No data export executor registered for resource $resourceName.")
}
