package tech.medo.infrastructure.secondary.runtimeagentoperations.agentdatasetaccessvalidation.validateagentdatasetaccess

import org.springframework.stereotype.Component
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessInput
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessResult
import tech.medo.runtimeagentoperations.validateagentdatasetaccess.ValidateAgentDatasetAccessService
import java.nio.file.Files
import java.nio.file.Path

@Component
class CsvFileValidateAgentDatasetAccessAdapter : ValidateAgentDatasetAccessService {
    override fun execute(input: ValidateAgentDatasetAccessInput): ValidateAgentDatasetAccessResult {
        fun rejected(reason: String): ValidateAgentDatasetAccessResult.Rejected =
            ValidateAgentDatasetAccessResult.Rejected(
                failureReason = reason
            )

        if (!input.isCsvFileBinding()) {
            return rejected("Only CSV file dataset access validation is supported.")
        }

        val filePath = input.filePath?.trim()
        if (filePath.isNullOrEmpty()) {
            return rejected("CSV dataset binding filePath is required.")
        }

        val path = Path.of(filePath)
        if (!Files.isRegularFile(path)) {
            return rejected("CSV dataset file does not exist: $filePath")
        }
        if (!Files.isReadable(path)) {
            return rejected("CSV dataset file is not readable: $filePath")
        }

        val readResult = readCsvShape(path)
        return if (readResult.failureReason == null) {
            ValidateAgentDatasetAccessResult.Succeeded(
                readable = true,
                schemaReadable = true,
                sampleBatchReadable = true
            )
        } else {
            rejected(readResult.failureReason)
        }
    }

    private fun ValidateAgentDatasetAccessInput.isCsvFileBinding(): Boolean {
        val source = dataSourceType.normalized()
        val format = dataFormat.normalized()
        return format == "csv" || source == "csv" || source == "file_csv" || (source == "file" && format == "csv")
    }

    private fun readCsvShape(path: Path): CsvShapeReadResult {
        Files.newBufferedReader(path).useLines { lines ->
            val iterator = lines.iterator()
            val header = iterator.nextNonBlankLine()
                ?: return CsvShapeReadResult("CSV dataset file is empty: $path")
            val headerColumns = header.split(',').map { it.trim() }.filter { it.isNotEmpty() }
            if (headerColumns.isEmpty()) {
                return CsvShapeReadResult("CSV dataset file has no readable header: $path")
            }
            val sample = iterator.nextNonBlankLine()
                ?: return CsvShapeReadResult("CSV dataset file has no readable sample row: $path")
            if (sample.split(',').isEmpty()) {
                return CsvShapeReadResult("CSV dataset file has no readable sample row: $path")
            }
        }
        return CsvShapeReadResult(null)
    }

    private fun Iterator<String>.nextNonBlankLine(): String? {
        while (hasNext()) {
            val line = next().trim()
            if (line.isNotEmpty()) {
                return line
            }
        }
        return null
    }

    private fun String?.normalized(): String =
        this?.trim()?.lowercase()?.replace("-", "_") ?: ""

    private data class CsvShapeReadResult(val failureReason: String?)
}
