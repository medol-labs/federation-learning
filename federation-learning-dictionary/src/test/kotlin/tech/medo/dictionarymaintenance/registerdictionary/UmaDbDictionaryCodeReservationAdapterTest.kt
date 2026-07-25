package tech.medo.dictionarymaintenance.registerdictionary

import com.fasterxml.jackson.databind.ObjectMapper
import org.axonframework.eventsourcing.eventstore.AppendCondition
import org.axonframework.eventsourcing.eventstore.EventStorageEngine.AppendTransaction
import org.axonframework.eventsourcing.eventstore.SourcingCondition
import org.axonframework.eventsourcing.eventstore.TaggedEventMessage
import org.axonframework.messaging.core.MessageType
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.eventhandling.GenericEventMessage
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tech.medo.dictionarymaintenance.domain.types.DictionaryCode
import tech.medo.dictionarymaintenance.events.DictionaryCodeReservedEvent
import tech.medo.infra.umadb.StoredEvent
import tech.medo.infra.umadb.StoredEventTag
import tech.medo.infra.umadb.UmaDbClient
import tech.medo.infra.umadb.UmaDbEventStorageEngine
import tech.medo.infra.umadb.UmaDbEventStorageProperties
import java.time.Duration
import java.time.Instant
import java.util.concurrent.CompletableFuture
import java.util.function.Function

class UmaDbDictionaryCodeReservationAdapterTest {
    @Test
    fun jacksonRestoresDictionaryCodeReservedEventWithValueClassPayload() {
        val objectMapper = ObjectMapper().findAndRegisterModules()
        val event = DictionaryCodeReservedEvent(
            dictionaryId = java.util.UUID.randomUUID(),
            dictionaryCode = DictionaryCode("RUNTIME_ENVIRONMENT_TYPE"),
            normalizedName = NORMALIZED_DICTIONARY_CODE
        )

        val restored = objectMapper.readValue(
            objectMapper.writeValueAsBytes(event),
            DictionaryCodeReservedEvent::class.java
        )

        assertEquals(event, restored)
    }

    @Test
    fun registerDictionaryCommandNormalizesDictionaryCodeValueForReservationSelection() {
        val command = RegisterDictionaryCommand(
            dictionaryCode = DictionaryCode("RUNTIME_ENVIRONMENT_TYPE"),
            dictionaryName = "Runtime environment type",
            description = null
        )

        assertEquals("runtime_environment_type", command.dictionaryCodeSelection.normalizedName)
    }

    @Test
    fun appendPassesDictionaryCodeReservationConditionAndTagsToUmaDb() {
        val client = RecordingUmaDbClient()
        val engine = engine(client)
        val condition = AppendCondition.withCriteria(
            EventCriteria.havingTags(Tag.of(DICTIONARY_CODE_TAG, NORMALIZED_DICTIONARY_CODE))
        )

        commit(
            engine.appendEvents(
                condition,
                null,
                listOf(tagged("dictionary-code-reserved", DICTIONARY_CODE_TAG, NORMALIZED_DICTIONARY_CODE))
            ).join()
        )

        assertEquals(
            listOf("$DICTIONARY_CODE_TAG=$NORMALIZED_DICTIONARY_CODE"),
            client.appendRequest!!.condition().failIfEventsMatch().first().tags()
        )
        assertEquals(
            listOf(StoredEventTag(DICTIONARY_CODE_TAG, NORMALIZED_DICTIONARY_CODE)),
            client.appendRequest!!.events().first().tags()
        )
    }

    @Test
    fun sourceReadsDictionaryCodeReservationEventsByDictionaryCodeTag() {
        val client = RecordingUmaDbClient()
        client.events.add(
            UmaDbClient.SequencedStoredEvent(
                0,
                stored("dictionary-code-reserved", DICTIONARY_CODE_TAG, NORMALIZED_DICTIONARY_CODE)
            )
        )
        val engine = engine(client)

        val stream = engine.source(
            SourcingCondition.conditionFor(
                EventCriteria.havingTags(Tag.of(DICTIONARY_CODE_TAG, NORMALIZED_DICTIONARY_CODE))
            )
        )

        val entry = stream.next().orElseThrow()

        assertEquals("dictionary-code-reserved", entry.message().identifier())
        assertEquals(
            listOf("$DICTIONARY_CODE_TAG=$NORMALIZED_DICTIONARY_CODE"),
            client.readRequest!!.queryItems().first().tags()
        )
    }

    private fun engine(client: UmaDbClient): UmaDbEventStorageEngine =
        UmaDbEventStorageEngine(
            UmaDbEventStorageProperties.of("localhost:50051", true, "", 16, Duration.ofSeconds(1)),
            client
        )

    @Suppress("UNCHECKED_CAST")
    private fun commit(transaction: AppendTransaction<*>) {
        val typed = transaction as AppendTransaction<UmaDbClient.AppendResult>
        typed.afterCommit(typed.commit().join()).join()
    }

    private fun tagged(identifier: String, tagKey: String, tagValue: String): TaggedEventMessage<EventMessage> =
        TestTaggedEventMessage(event(identifier), setOf(Tag.of(tagKey, tagValue)))

    private fun event(identifier: String): EventMessage =
        GenericEventMessage(
            identifier,
            MessageType("tech.medo.dictionarymaintenance.events.DictionaryCodeReservedEvent"),
            mapOf("id" to identifier),
            emptyMap<String, String>(),
            NOW
        )

    private fun stored(identifier: String, tagKey: String, tagValue: String): StoredEvent =
        StoredEvent(
            identifier,
            "tech.medo.dictionarymaintenance.events.DictionaryCodeReservedEvent",
            NOW,
            emptyMap<String, Any>(),
            mapOf("id" to identifier),
            listOf(StoredEventTag(tagKey, tagValue))
        )

    private data class TestTaggedEventMessage(
        private val event: EventMessage,
        private val tags: Set<Tag>
    ) : TaggedEventMessage<EventMessage> {
        override fun event(): EventMessage = event

        override fun tags(): Set<Tag> = tags

        override fun updateTags(updater: Function<Set<Tag>, Set<Tag>>): TaggedEventMessage<EventMessage> =
            TestTaggedEventMessage(event, updater.apply(tags))
    }

    private class RecordingUmaDbClient : UmaDbClient {
        val events = mutableListOf<UmaDbClient.SequencedStoredEvent>()
        var appendRequest: UmaDbClient.AppendRequest? = null
        var readRequest: UmaDbClient.ReadRequest? = null

        override fun append(request: UmaDbClient.AppendRequest): CompletableFuture<UmaDbClient.AppendResult> {
            appendRequest = request
            var position = events.lastOrNull()?.position() ?: -1
            request.events().forEach { event ->
                events.add(UmaDbClient.SequencedStoredEvent(++position, event))
            }
            return CompletableFuture.completedFuture(UmaDbClient.AppendResult(position))
        }

        override fun read(request: UmaDbClient.ReadRequest): CompletableFuture<UmaDbClient.ReadResult> {
            readRequest = request
            return CompletableFuture.completedFuture(
                UmaDbClient.ReadResult(
                    events
                        .filter { it.position() >= request.start() }
                        .filter { matchesAny(it.event(), request.queryItems()) }
                        .take(request.limit() ?: Int.MAX_VALUE)
                )
            )
        }

        override fun subscribe(request: UmaDbClient.SubscribeRequest): CompletableFuture<UmaDbClient.ReadResult> =
            CompletableFuture.completedFuture(UmaDbClient.ReadResult(emptyList()))

        override fun head(): CompletableFuture<UmaDbClient.HeadResult> =
            CompletableFuture.completedFuture(UmaDbClient.HeadResult(events.lastOrNull()?.position() ?: -1))

        private fun matchesAny(event: StoredEvent, queryItems: List<UmaDbClient.QueryItem>): Boolean =
            queryItems.isEmpty() || queryItems.any { item -> matches(event, item) }

        private fun matches(event: StoredEvent, item: UmaDbClient.QueryItem): Boolean {
            val typeMatches = item.types().isEmpty() || item.types().contains(event.eventType())
            val eventTags = event.tags().map { "${it.key()}=${it.value()}" }
            val tagsMatch = item.tags().isEmpty() || eventTags.containsAll(item.tags())
            return typeMatches && tagsMatch
        }
    }

    private companion object {
        private val NOW: Instant = Instant.parse("2026-07-14T00:00:00Z")
        private const val DICTIONARY_CODE_TAG = "dictionaryCode"
        private const val NORMALIZED_DICTIONARY_CODE = "runtime_connectivity_mode"
    }
}
