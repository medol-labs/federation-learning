package tech.medo.infrastructure.secondary.trainingorchestration.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier
import com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.node.ObjectNode
import tech.medo.trainingorchestration.events.TrainingRoundCompletedEvent
import java.util.UUID

/** Old completion payloads predate the successor identity. Derive it consistently on every replay. */
class TrainingRoundEventCompatibilityModule : SimpleModule("training-round-completion-v1") {
    init {
        setDeserializerModifier(object : BeanDeserializerModifier() {
            override fun modifyDeserializer(config: DeserializationConfig, bean: BeanDescription, delegate: JsonDeserializer<*>): JsonDeserializer<*> =
                if (bean.beanClass == TrainingRoundCompletedEvent::class.java) CompletionReader(delegate) else delegate
        })
    }

    private class CompletionReader(delegate: JsonDeserializer<*>) : DelegatingDeserializer(delegate) {
        override fun newDelegatingInstance(delegate: JsonDeserializer<*>) = CompletionReader(delegate)

        override fun deserialize(parser: JsonParser, context: DeserializationContext): Any {
            val node = parser.codec.readTree<ObjectNode>(parser)
            if (!node.has("nextRoundId")) {
                val job = UUID.fromString(node.required("trainingJobId").asText())
                val round = node.required("roundNumber").intValue()
                node.put("nextRoundId", UUID.nameUUIDFromBytes("training-round-successor:$job:$round".toByteArray()).toString())
            }
            return node.traverse(parser.codec).use { it.nextToken(); super.deserialize(it, context) }
        }
    }
}
