package tech.medo.shared.application.sync

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.UUID

object SyncValueConverters {
    fun required(value: String?, target: String, field: String): String =
        value ?: throw IllegalArgumentException("Sync target $target requires field $field")

    fun required(value: Int?, target: String, field: String): Int =
        value ?: throw IllegalArgumentException("Sync target $target requires field $field")

    fun required(value: Long?, target: String, field: String): Long =
        value ?: throw IllegalArgumentException("Sync target $target requires field $field")

    fun required(value: Boolean?, target: String, field: String): Boolean =
        value ?: throw IllegalArgumentException("Sync target $target requires field $field")

    fun required(value: BigDecimal?, target: String, field: String): BigDecimal =
        value ?: throw IllegalArgumentException("Sync target $target requires field $field")

    fun required(value: UUID?, target: String, field: String): UUID =
        value ?: throw IllegalArgumentException("Sync target $target requires field $field")

    fun required(value: LocalDate?, target: String, field: String): LocalDate =
        value ?: throw IllegalArgumentException("Sync target $target requires field $field")

    fun required(value: LocalDateTime?, target: String, field: String): LocalDateTime =
        value ?: throw IllegalArgumentException("Sync target $target requires field $field")

    fun string(value: Any?): String? = value?.toString()

    fun int(value: Any?): Int? = when (value) {
        is Int -> value
        is Number -> value.toInt()
        is String -> value.takeIf { it.isNotBlank() }?.toInt()
        else -> null
    }

    fun long(value: Any?): Long? = when (value) {
        is Long -> value
        is Number -> value.toLong()
        is String -> value.takeIf { it.isNotBlank() }?.toLong()
        else -> null
    }

    fun decimal(value: Any?): BigDecimal? = when (value) {
        is BigDecimal -> value
        is Number -> BigDecimal.valueOf(value.toDouble())
        is String -> value.takeIf { it.isNotBlank() }?.let(::BigDecimal)
        else -> null
    }

    fun boolean(value: Any?): Boolean? = when (value) {
        is Boolean -> value
        is String -> value.takeIf { it.isNotBlank() }?.toBooleanStrictOrNull()
        else -> null
    }

    fun uuid(value: Any?): UUID? = when (value) {
        is UUID -> value
        is String -> value.takeIf { it.isNotBlank() }?.let(UUID::fromString)
        else -> null
    }

    fun localDate(value: Any?): LocalDate? = when (value) {
        is LocalDate -> value
        is String -> value.takeIf { it.isNotBlank() }?.let(LocalDate::parse)
        else -> null
    }

    fun localDateTime(value: Any?): LocalDateTime? = when (value) {
        is LocalDateTime -> value
        is OffsetDateTime -> value.toLocalDateTime()
        is String -> value.takeIf { it.isNotBlank() }?.let {
            runCatching { LocalDateTime.parse(it) }.getOrElse { _ -> OffsetDateTime.parse(it).toLocalDateTime() }
        }
        else -> null
    }

    fun stringList(value: Any?): List<String> = when (value) {
        is Iterable<*> -> value.mapNotNull { it?.toString() }
        is Array<*> -> value.mapNotNull { it?.toString() }
        is String -> if (value.isBlank()) emptyList() else value.split(",").map { it.trim() }.filter { it.isNotBlank() }
        else -> emptyList()
    }
}
