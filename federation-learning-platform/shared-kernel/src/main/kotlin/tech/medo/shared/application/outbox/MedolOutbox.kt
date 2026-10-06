package tech.medo.shared.application.outbox

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.LockModeType
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.axonframework.messaging.eventhandling.EventMessage
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity
@Table(
    name = "medol_outbox",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_medol_outbox_event",
            columnNames = ["source_context", "source_name", "message_key", "event_id", "operation"]
        )
    ],
    indexes = [
        Index(
            name = "idx_medol_outbox_channel_sequence",
            columnList = "channel, sequence"
        ),
        Index(
            name = "idx_medol_outbox_source_sequence",
            columnList = "source_context, source_name, sequence"
        ),
        Index(
            name = "idx_medol_outbox_queue_available",
            columnList = "channel, status, available_at, sequence"
        )
    ]
)
class MedolOutboxMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var sequence: Long? = null

    var channel: String = ""

    @Column(name = "source_context")
    var sourceContext: String = ""

    @Column(name = "source_name")
    var sourceName: String = ""

    @Column(name = "message_key")
    var messageKey: String = ""
    var operation: String = "UPSERT"

    @Column(name = "event_id")
    var eventId: String = ""

    @Column(name = "event_type")
    var eventType: String = ""

    @Column(name = "occurred_at")
    var occurredAt: LocalDateTime? = null

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()

    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "text")
    var payloadJson: String = "{}"

    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "text")
    var headersJson: String = "{}"

    var status: String? = MedolOutboxStatus.AVAILABLE

    @Column(name = "available_at")
    var availableAt: LocalDateTime? = LocalDateTime.now()

    @Column(name = "claimed_by")
    var claimedBy: String? = null

    @Column(name = "claimed_until")
    var claimedUntil: LocalDateTime? = null

    @Column(name = "retry_count")
    var retryCount: Int? = 0

    @Column(name = "processed_at")
    var processedAt: LocalDateTime? = null

    @Column(name = "last_error", columnDefinition = "text")
    var lastError: String? = null
}

object MedolOutboxStatus {
    const val AVAILABLE = "AVAILABLE"
    const val PROCESSING = "PROCESSING"
    const val PROCESSED = "PROCESSED"
    const val FAILED = "FAILED"
}

interface MedolOutboxRepository : JpaRepository<MedolOutboxMessage, Long> {
    fun findByChannelAndSequenceGreaterThanOrderBySequenceAsc(
        channel: String,
        sequence: Long,
        pageable: Pageable
    ): List<MedolOutboxMessage>

    fun findBySourceContextAndSourceNameAndSequenceGreaterThanOrderBySequenceAsc(
        sourceContext: String,
        sourceName: String,
        sequence: Long,
        pageable: Pageable
    ): List<MedolOutboxMessage>

    fun existsByChannelAndMessageKeyAndEventIdAndOperation(
        channel: String,
        messageKey: String,
        eventId: String,
        operation: String
    ): Boolean

    fun existsBySourceContextAndSourceNameAndMessageKeyAndEventIdAndOperation(
        sourceContext: String,
        sourceName: String,
        messageKey: String,
        eventId: String,
        operation: String
    ): Boolean

    fun findFirstByChannelOrderBySequenceDesc(channel: String): MedolOutboxMessage?

    fun findFirstBySourceContextAndSourceNameOrderBySequenceDesc(
        sourceContext: String,
        sourceName: String
    ): MedolOutboxMessage?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(
        """
        select message from MedolOutboxMessage message
        where message.channel = :channel
          and (message.status is null or message.status = :status)
          and (message.availableAt is null or message.availableAt <= :availableAt)
        order by message.sequence asc
        """
    )
    fun findAvailableForClaim(
        @Param("channel") channel: String,
        @Param("status") status: String,
        @Param("availableAt") availableAt: LocalDateTime,
        pageable: Pageable
    ): List<MedolOutboxMessage>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(
        """
        select message from MedolOutboxMessage message
        where message.channel = :channel
          and message.status = :status
          and message.claimedUntil <= :claimedUntil
        order by message.sequence asc
        """
    )
    fun findExpiredClaimsForClaim(
        @Param("channel") channel: String,
        @Param("status") status: String,
        @Param("claimedUntil") claimedUntil: LocalDateTime,
        pageable: Pageable
    ): List<MedolOutboxMessage>

    fun deleteByStatusAndProcessedAtBefore(status: String, processedAt: LocalDateTime): Long
}

@Component
class MedolOutboxAppender(
    private val repository: MedolOutboxRepository,
    private val objectMapper: ObjectMapper
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun appendReadModel(
        sourceContext: String,
        sourceReadModel: String,
        readModelKey: String,
        operation: String,
        payload: Any,
        message: EventMessage
    ) {
        val eventId = message.identifier()
        if (repository.existsBySourceContextAndSourceNameAndMessageKeyAndEventIdAndOperation(
                sourceContext,
                sourceReadModel,
                readModelKey,
                eventId,
                operation
            )
        ) {
            log.debug(
                "MEDOL OUTBOX skip duplicate readmodel source={}.{} key={} operation={} eventId={}",
                sourceContext,
                sourceReadModel,
                readModelKey,
                operation,
                eventId
            )
            return
        }
        append(
            channel = readModelOutboxChannel(sourceContext, sourceReadModel),
            sourceContext = sourceContext,
            sourceName = sourceReadModel,
            messageKey = readModelKey,
            operation = operation,
            payload = payload,
            message = message,
            eventId = eventId
        )
    }

    fun append(
        channel: String,
        sourceContext: String,
        sourceName: String,
        messageKey: String,
        operation: String,
        payload: Any,
        message: EventMessage,
        eventId: String = message.identifier(),
        headers: Map<String, Any?> = emptyMap()
    ) {
        if (repository.existsByChannelAndMessageKeyAndEventIdAndOperation(
                channel,
                messageKey,
                eventId,
                operation
            )
        ) {
            log.debug(
                "MEDOL OUTBOX skip duplicate channel={} key={} operation={} eventId={}",
                channel,
                messageKey,
                operation,
                eventId
            )
            return
        }
        val saved = repository.save(MedolOutboxMessage().also {
            it.channel = channel
            it.sourceContext = sourceContext
            it.sourceName = sourceName
            it.messageKey = messageKey
            it.operation = operation
            it.eventId = eventId
            it.eventType = message.type().toString()
            it.occurredAt = LocalDateTime.ofInstant(message.timestamp(), ZoneOffset.UTC)
            it.payloadJson = objectMapper.writeValueAsString(payload)
            it.headersJson = objectMapper.writeValueAsString(headers)
        })
        log.info(
            "MEDOL OUTBOX stored sequence={} channel={} source={}.{} key={} operation={} eventId={} eventType={}",
            saved.sequence,
            saved.channel,
            saved.sourceContext,
            saved.sourceName,
            saved.messageKey,
            saved.operation,
            saved.eventId,
            saved.eventType
        )
    }

    fun appendExternal(
        channel: String,
        sourceContext: String,
        sourceName: String,
        messageKey: String,
        operation: String,
        payload: Any,
        eventId: String,
        eventType: String,
        occurredAt: LocalDateTime = LocalDateTime.now(),
        headers: Map<String, Any?> = emptyMap()
    ) {
        if (repository.existsByChannelAndMessageKeyAndEventIdAndOperation(
                channel,
                messageKey,
                eventId,
                operation
            )
        ) {
            log.debug(
                "MEDOL OUTBOX skip duplicate channel={} key={} operation={} eventId={}",
                channel,
                messageKey,
                operation,
                eventId
            )
            return
        }
        val saved = repository.save(MedolOutboxMessage().also {
            it.channel = channel
            it.sourceContext = sourceContext
            it.sourceName = sourceName
            it.messageKey = messageKey
            it.operation = operation
            it.eventId = eventId
            it.eventType = eventType
            it.occurredAt = occurredAt
            it.payloadJson = objectMapper.writeValueAsString(payload)
            it.headersJson = objectMapper.writeValueAsString(headers)
        })
        log.info(
            "MEDOL OUTBOX stored sequence={} channel={} source={}.{} key={} operation={} eventId={} eventType={}",
            saved.sequence,
            saved.channel,
            saved.sourceContext,
            saved.sourceName,
            saved.messageKey,
            saved.operation,
            saved.eventId,
            saved.eventType
        )
    }
}

@Component
class MedolOutboxQueue(
    private val repository: MedolOutboxRepository
) {
    @Transactional
    fun claimAvailable(
        channel: String,
        consumerId: String,
        batchSize: Int,
        claimTimeout: Duration = Duration.ofMinutes(5)
    ): List<MedolOutboxMessage> {
        val now = LocalDateTime.now()
        val limit = PageRequest.of(0, batchSize.coerceIn(1, 1000))
        val available = repository.findAvailableForClaim(
            channel,
            MedolOutboxStatus.AVAILABLE,
            now,
            limit
        )
        val expired = repository.findExpiredClaimsForClaim(
            channel,
            MedolOutboxStatus.PROCESSING,
            now,
            limit
        )
        val claimed = (available + expired)
            .distinctBy { it.sequence }
            .take(batchSize.coerceIn(1, 1000))
            .onEach {
                it.status = MedolOutboxStatus.PROCESSING
                it.claimedBy = consumerId
                it.claimedUntil = now.plus(claimTimeout)
                it.lastError = null
            }
        return repository.saveAll(claimed).toList()
    }

    @Transactional
    fun markProcessed(sequence: Long) {
        repository.findById(sequence).ifPresent {
            it.status = MedolOutboxStatus.PROCESSED
            it.processedAt = LocalDateTime.now()
            it.claimedBy = null
            it.claimedUntil = null
            repository.save(it)
        }
    }

    @Transactional
    fun markFailed(sequence: Long, error: String?, maxRetries: Int = 10, retryDelay: Duration = Duration.ofSeconds(30)) {
        repository.findById(sequence).ifPresent {
            val nextRetryCount = (it.retryCount ?: 0) + 1
            it.retryCount = nextRetryCount
            it.lastError = error?.take(4000)
            it.claimedBy = null
            it.claimedUntil = null
            if (nextRetryCount >= maxRetries) {
                it.status = MedolOutboxStatus.FAILED
            } else {
                it.status = MedolOutboxStatus.AVAILABLE
                it.availableAt = LocalDateTime.now().plus(retryDelay)
            }
            repository.save(it)
        }
    }

    @Transactional
    fun purgeProcessedBefore(cutoff: LocalDateTime): Long =
        repository.deleteByStatusAndProcessedAtBefore(MedolOutboxStatus.PROCESSED, cutoff)
}

fun readModelOutboxChannel(sourceContext: String, sourceReadModel: String): String =
    "readmodel.$sourceContext.$sourceReadModel"
