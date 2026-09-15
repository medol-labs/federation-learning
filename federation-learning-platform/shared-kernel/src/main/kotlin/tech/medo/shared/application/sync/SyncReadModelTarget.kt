package tech.medo.shared.application.sync

import java.time.LocalDateTime

data class SyncReadModelTarget(
    val name: String,
    val source: String,
    val sourceContext: String,
    val sourceReadModel: String,
    val sourcePath: String,
    val deltaPath: String = "$sourcePath/deltas",
    val fieldMappings: Map<String, String>,
    val queryParameters: (SyncReadModelContext) -> Map<String, String> = { emptyMap() },
    val upsert: (Map<String, Any?>, LocalDateTime) -> Unit
)

data class SyncReadModelContext(
    val properties: SyncReadModelProperties,
    val checkpoint: SyncReadModelCheckpoint?
) {
    fun requiredParameter(name: String, target: String): String =
        properties.parameters[name]
            ?: throw IllegalArgumentException("Sync target $target requires medol.sync.parameters.$name")
}

data class SyncReadModelResult(
    val target: String,
    val itemCount: Int,
    val nextCursor: String? = null,
    val nextSequence: Long? = null
)
