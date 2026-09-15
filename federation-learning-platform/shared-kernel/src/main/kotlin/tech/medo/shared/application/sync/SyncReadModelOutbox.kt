package tech.medo.shared.application.sync

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Lob
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.axonframework.messaging.eventhandling.EventMessage
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity
@Table(
    name = "medol_sync_read_model_outbox",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_sync_read_model_outbox_event",
            columnNames = ["source_context", "source_read_model", "read_model_key", "event_id", "operation"]
        )
    ],
    indexes = [
        Index(
            name = "idx_sync_read_model_outbox_source_sequence",
            columnList = "source_context, source_read_model, sequence"
        )
    ]
)
class SyncReadModelOutbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var sequence: Long? = null

    @Column(name = "source_context")
    var sourceContext: String = ""

    @Column(name = "source_read_model")
    var sourceReadModel: String = ""

    @Column(name = "read_model_key")
    var readModelKey: String = ""
    var operation: String = "UPSERT"

    @Column(name = "event_id")
    var eventId: String = ""

    @Column(name = "event_type")
    var eventType: String = ""

    @Column(name = "occurred_at")
    var occurredAt: LocalDateTime? = null

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Lob
    @Column(columnDefinition = "text")
    var payloadJson: String = "{}"
}

interface SyncReadModelOutboxRepository : JpaRepository<SyncReadModelOutbox, Long> {
    fun findBySourceContextAndSourceReadModelAndSequenceGreaterThanOrderBySequenceAsc(
        sourceContext: String,
        sourceReadModel: String,
        sequence: Long,
        pageable: Pageable
    ): List<SyncReadModelOutbox>

    fun existsBySourceContextAndSourceReadModelAndReadModelKeyAndEventIdAndOperation(
        sourceContext: String,
        sourceReadModel: String,
        readModelKey: String,
        eventId: String,
        operation: String
    ): Boolean
}

@Component
class SyncReadModelOutboxAppender(
    private val repository: SyncReadModelOutboxRepository,
    private val objectMapper: ObjectMapper
) {
    fun append(
        sourceContext: String,
        sourceReadModel: String,
        readModelKey: String,
        operation: String,
        payload: Any,
        message: EventMessage
    ) {
        val eventId = message.identifier()
        if (repository.existsBySourceContextAndSourceReadModelAndReadModelKeyAndEventIdAndOperation(
                sourceContext,
                sourceReadModel,
                readModelKey,
                eventId,
                operation
            )
        ) {
            return
        }
        repository.save(SyncReadModelOutbox().also {
            it.sourceContext = sourceContext
            it.sourceReadModel = sourceReadModel
            it.readModelKey = readModelKey
            it.operation = operation
            it.eventId = eventId
            it.eventType = message.type().toString()
            it.occurredAt = LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)
            it.payloadJson = objectMapper.writeValueAsString(payload)
        })
    }
}
