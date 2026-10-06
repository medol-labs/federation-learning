package tech.medo.shared.application.export

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.ObjectProvider
import org.springframework.core.io.ByteArrayResource
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.UUID
import kotlin.io.path.createDirectories

@Service
class DataExportService(
    private val properties: DataExportProperties,
    private val objectMapper: ObjectMapper,
    private val requestPort: ObjectProvider<DataExportJobRequestPort>
) {
    private val accessorCache = java.util.concurrent.ConcurrentHashMap<String, java.lang.reflect.Method?>()
    private val columnsType = object : TypeReference<List<DataExportColumn>>() {}
    private val sortType = object : TypeReference<List<DataExportSortOrder>>() {}

    fun pageSize(): Int = properties.pageSize.coerceAtLeast(1)

    fun validatedColumns(requested: List<DataExportColumn>?, allowed: List<DataExportColumn>): List<DataExportColumn> {
        val allowedByField = allowed.associateBy { it.field }
        return requested
            ?.mapNotNull { requestedColumn ->
                allowedByField[requestedColumn.field]?.copy(label = requestedColumn.label ?: allowedByField[requestedColumn.field]?.label)
            }
            ?.takeIf { it.isNotEmpty() }
            ?: allowed
    }

    fun <T : Any> export(
        resourceName: String,
        columns: List<DataExportColumn>,
        criteria: Any,
        sort: Sort,
        firstPage: Page<T>,
        fetchPage: (Pageable) -> Page<T>,
        requestedLocale: String? = null,
        snapshotUpperBound: Instant? = null
    ): ResponseEntity<Any> {
        val total = firstPage.totalElements
        if (total > properties.maxRows) {
            throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Data export exceeds the configured maximum row count.")
        }
        val fileName = exportFileName(resourceName)
        return if (total > properties.asyncThreshold) {
            val requestedAt = Instant.now()
            val effectiveSnapshotUpperBound = snapshotUpperBound ?: requestedAt
            val criteriaJson = objectMapper.writeValueAsString(criteria)
            val sortJson = objectMapper.writeValueAsString(sortOrders(sort))
            val columnsJson = objectMapper.writeValueAsString(columns)
            val requestHash = requestHash(resourceName, criteriaJson, sortJson, columnsJson, requestedLocale, effectiveSnapshotUpperBound)
            val jobId = UUID.nameUUIDFromBytes(requestHash.toByteArray(StandardCharsets.UTF_8))
            val request = DataExportJobRequestMessage(jobId, resourceName, criteriaJson, sortJson, columnsJson, requestedLocale, requestedAt, effectiveSnapshotUpperBound, requestHash, fileName)
            val port = requestPort.getIfAvailable()
                ?: throw ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Data export job request port is not available.")
            port.request(request)
            ResponseEntity.accepted().body(DataExportJobResponse(jobId, "REQUESTED", fileName) as Any)
        } else {
            csvResponse(fileName, renderCsv(columns, firstPage, fetchPage).content)
        }
    }

    fun csvResponse(fileName: String, content: ByteArray): ResponseEntity<Any> {
        val encoded = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replace("+", "%20")
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''$encoded")
            .contentType(MediaType.parseMediaType("text/csv;charset=UTF-8"))
            .body(ByteArrayResource(content) as Any)
    }

    fun <T : Any> writeCsvFile(
        fileName: String,
        columns: List<DataExportColumn>,
        firstPage: Page<T>,
        fetchPage: (Pageable) -> Page<T>
    ): DataExportExecutionResult {
        val rendered = renderCsv(columns, firstPage, fetchPage)
        val directory = Path.of(properties.storagePath).createDirectories()
        val path = directory.resolve(fileName).normalize()
        Files.write(path, rendered.content)
        return DataExportExecutionResult(fileName, path.toString(), rendered.rowCount)
    }

    fun columnsFromJson(columnsJson: String, allowed: List<DataExportColumn>): List<DataExportColumn> =
        validatedColumns(objectMapper.readValue(columnsJson, columnsType), allowed)

    fun sortFromJson(sortJson: String): Sort {
        val orders = objectMapper.readValue(sortJson, sortType)
        if (orders.isEmpty()) return Sort.unsorted()
        return Sort.by(orders.map {
            Sort.Order(Sort.Direction.fromOptionalString(it.direction).orElse(Sort.Direction.ASC), it.property)
        })
    }

    private data class RenderedCsv(val content: ByteArray, val rowCount: Long)

    private fun <T : Any> renderCsv(
        columns: List<DataExportColumn>,
        firstPage: Page<T>,
        fetchPage: (Pageable) -> Page<T>
    ): RenderedCsv {
        val builder = StringBuilder()
        var rowCount = 0L
        builder.append(columns.joinToString(",") { csvCell(it.label ?: it.field) }).append("\n")
        appendRows(builder, columns, firstPage.content)
        rowCount += firstPage.content.size
        var pageNumber = 1
        while (pageNumber < firstPage.totalPages) {
            val page = fetchPage(PageRequest.of(pageNumber, pageSize(), firstPage.pageable.sort))
            appendRows(builder, columns, page.content)
            rowCount += page.content.size
            pageNumber += 1
        }
        return RenderedCsv(("\uFEFF" + builder.toString()).toByteArray(StandardCharsets.UTF_8), rowCount)
    }

    private fun <T : Any> appendRows(builder: StringBuilder, columns: List<DataExportColumn>, rows: List<T>) {
        for (row in rows) {
            builder.append(columns.joinToString(",") { column -> csvCell(readField(row, column.field)) }).append("\n")
        }
    }

    private fun readField(row: Any, field: String): String {
        val key = row.javaClass.name + "#" + field
        val method = accessorCache.computeIfAbsent(key) {
            val suffix = field.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
            row.javaClass.methods.firstOrNull { method ->
                method.parameterCount == 0 && (method.name == "get$suffix" || method.name == "is$suffix")
            }
        }
        return method?.invoke(row)?.toString() ?: ""
    }

    private fun csvCell(value: String): String {
        val escaped = value.replace("\"", "\"\"")
        return if (escaped.any { it == ',' || it == '"' || it == '\n' || it == '\r' }) "\"$escaped\"" else escaped
    }

    private fun exportFileName(resourceName: String): String {
        val timestamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
            .withZone(java.time.ZoneId.systemDefault())
            .format(Instant.now())
        return "$resourceName-$timestamp.csv"
    }

    private fun sortOrders(sort: Sort): List<DataExportSortOrder> =
        sort.map { DataExportSortOrder(it.property, it.direction.name) }.toList()

    private fun requestHash(resourceName: String, criteriaJson: String, sortJson: String, columnsJson: String, requestedLocale: String?, snapshotUpperBound: Instant): String {
        val value = listOf(resourceName, criteriaJson, sortJson, columnsJson, requestedLocale.orEmpty(), snapshotUpperBound.toString()).joinToString("\u001F")
        val digest = MessageDigest.getInstance("SHA-256").digest(value.toByteArray(StandardCharsets.UTF_8))
        return digest.joinToString("") { "%02x".format(it) }
    }
}
