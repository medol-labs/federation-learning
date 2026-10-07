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
import java.nio.file.Files
import java.nio.file.Path
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.security.MessageDigest
import java.util.UUID
import kotlin.io.path.createDirectories

@Service
class DataExportService(
    private val properties: DataExportProperties,
    private val objectMapper: ObjectMapper,
    private val requestPort: ObjectProvider<DataExportJobRequestPort>,
    private val dictionaryLabelProviders: ObjectProvider<DataExportDictionaryLabelProvider>
) {
    private val accessorCache = java.util.concurrent.ConcurrentHashMap<String, java.lang.reflect.Method?>()
    private val columnsType = object : TypeReference<List<DataExportColumn>>() {}
    private val sortType = object : TypeReference<List<DataExportSortOrder>>() {}

    fun pageSize(): Int = properties.pageSize.coerceAtLeast(1)

    fun validatedColumns(requested: List<DataExportColumn>?, allowed: List<DataExportColumn>): List<DataExportColumn> {
        val allowedByField = allowed.associateBy { it.field }
        val selected = requested
            ?.mapNotNull { requestedColumn ->
                allowedByField[requestedColumn.field]?.copy(
                    label = requestedColumn.label ?: allowedByField[requestedColumn.field]?.label,
                    dictionaryCode = requestedColumn.dictionaryCode ?: allowedByField[requestedColumn.field]?.dictionaryCode
                )
            }
            ?.takeIf { it.isNotEmpty() }
            ?: allowed
        return selected
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
            val request = DataExportJobRequestMessage(
                dataExportJobId = jobId,
                resourceName = resourceName,
                criteriaJson = criteriaJson,
                sortJson = sortJson,
                columnsJson = columnsJson,
                requestedLocale = requestedLocale,
                requestedAt = requestedAt,
                snapshotUpperBound = effectiveSnapshotUpperBound,
                requestHash = requestHash,
                fileName = fileName
            )
            val port = requestPort.getIfAvailable()
                ?: throw ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Data export job request port is not available.")
            port.request(request)
            ResponseEntity.accepted().body(DataExportJobResponse(jobId, "REQUESTED", fileName) as Any)
        } else {
            csvResponse(fileName, renderCsv(columns, firstPage, fetchPage, requestedLocale).content)
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
        requestedLocale: String? = null,
        fetchPage: (Pageable) -> Page<T>
    ): DataExportExecutionResult {
        val rendered = renderCsv(columns, firstPage, fetchPage, requestedLocale)
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
        fetchPage: (Pageable) -> Page<T>,
        requestedLocale: String?
    ): RenderedCsv {
        val builder = StringBuilder()
        val dictionaryCache = mutableMapOf<String, Map<String, String>>()
        var rowCount = 0L
        builder.append(columns.joinToString(",") { csvCell(it.label ?: it.field) }).append("\n")
        appendRows(builder, columns, firstPage.content, requestedLocale, dictionaryCache)
        rowCount += firstPage.content.size
        var pageNumber = 1
        while (pageNumber < firstPage.totalPages) {
            val page = fetchPage(PageRequest.of(pageNumber, pageSize(), firstPage.pageable.sort))
            appendRows(builder, columns, page.content, requestedLocale, dictionaryCache)
            rowCount += page.content.size
            pageNumber += 1
        }
        return RenderedCsv(("\uFEFF" + builder.toString()).toByteArray(StandardCharsets.UTF_8), rowCount)
    }

    private fun <T : Any> appendRows(
        builder: StringBuilder,
        columns: List<DataExportColumn>,
        rows: List<T>,
        requestedLocale: String?,
        dictionaryCache: MutableMap<String, Map<String, String>>
    ) {
        for (row in rows) {
            builder.append(columns.joinToString(",") { column ->
                csvCell(formatValue(readField(row, column.field), column, requestedLocale, dictionaryCache))
            }).append("\n")
        }
    }

    private fun readField(row: Any, field: String): Any? {
        val key = row.javaClass.name + "#" + field
        val method = accessorCache.computeIfAbsent(key) {
            val suffix = field.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
            row.javaClass.methods.firstOrNull { method ->
                method.parameterCount == 0 && (method.name == "get$suffix" || method.name == "is$suffix")
            }
        }
        return method?.invoke(row)
    }

    private fun formatValue(
        value: Any?,
        column: DataExportColumn,
        requestedLocale: String?,
        dictionaryCache: MutableMap<String, Map<String, String>>
    ): String {
        if (value == null) return ""
        if (value is Iterable<*>) {
            return value.map { formatValue(it, column, requestedLocale, dictionaryCache) }
                .filter { it.isNotBlank() }
                .joinToString("; ")
        }
        if (value.javaClass.isArray) {
            return (0 until java.lang.reflect.Array.getLength(value))
                .map { index -> formatValue(java.lang.reflect.Array.get(value, index), column, requestedLocale, dictionaryCache) }
                .filter { it.isNotBlank() }
                .joinToString("; ")
        }
        val rawValue = when (value) {
            is Enum<*> -> value.name
            else -> value.toString()
        }
        val dictionaryCode = column.dictionaryCode
        if (!dictionaryCode.isNullOrBlank()) {
            val labels = dictionaryCache.getOrPut(dictionaryCacheKey(dictionaryCode, requestedLocale)) {
                dictionaryLabels(dictionaryCode, requestedLocale)
            }
            return labels[rawValue] ?: rawValue
        }
        if (value is Boolean) {
            return if (isChineseLocale(requestedLocale)) {
                if (value) "是" else "否"
            } else {
                if (value) "Yes" else "No"
            }
        }
        return rawValue
    }

    private fun dictionaryCacheKey(dictionaryCode: String, requestedLocale: String?): String =
        listOf(requestedLocale.orEmpty(), dictionaryCode).joinToString("\u001F")

    private fun dictionaryLabels(dictionaryCode: String, requestedLocale: String?): Map<String, String> =
        dictionaryLabelProviders.orderedStream()
            .map { provider -> provider.labels(dictionaryCode, requestedLocale) }
            .filter { labels -> labels.isNotEmpty() }
            .findFirst()
            .orElse(emptyMap())

    private fun isChineseLocale(requestedLocale: String?): Boolean {
        val locale = requestedLocale?.lowercase() ?: return false
        return locale == "zh" || locale.startsWith("zh-") || locale.startsWith("zh_")
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

    private fun requestHash(
        resourceName: String,
        criteriaJson: String,
        sortJson: String,
        columnsJson: String,
        requestedLocale: String?,
        snapshotUpperBound: Instant
    ): String {
        val value = listOf(resourceName, criteriaJson, sortJson, columnsJson, requestedLocale.orEmpty(), snapshotUpperBound.toString()).joinToString("\u001F")
        val digest = MessageDigest.getInstance("SHA-256").digest(value.toByteArray(StandardCharsets.UTF_8))
        return digest.joinToString("") { "%02x".format(it) }
    }
}
