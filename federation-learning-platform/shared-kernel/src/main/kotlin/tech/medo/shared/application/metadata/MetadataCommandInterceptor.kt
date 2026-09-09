package tech.medo.shared.application.metadata

import org.axonframework.messaging.commandhandling.CommandMessage
import org.axonframework.messaging.core.MessageDispatchInterceptor
import org.axonframework.messaging.core.MessageDispatchInterceptorChain
import org.axonframework.messaging.core.MessageStream
import org.axonframework.messaging.core.unitofwork.ProcessingContext
import org.springframework.stereotype.Component

@Component
class MetadataCommandInterceptor : MessageDispatchInterceptor<CommandMessage> {
    override fun interceptOnDispatch(
        message: CommandMessage,
        context: ProcessingContext?,
        chain: MessageDispatchInterceptorChain<CommandMessage>
    ): MessageStream<*> {
        val metadata = MetadataFactory.fromValues(
            userId = message.metadata()[MetadataKeys.USER_ID],
            sessionId = message.metadata()[MetadataKeys.SESSION_ID],
            correlationId = message.metadata()[MetadataKeys.CORRELATION_ID],
            causationId = message.metadata()[MetadataKeys.CAUSATION_ID] ?: message.identifier(),
            traceId = message.metadata()[MetadataKeys.TRACE_ID],
            tenantId = message.metadata()[MetadataKeys.TENANT_ID]
        )

        require(!metadata[MetadataKeys.SESSION_ID].isNullOrBlank()) {
            "Missing required metadata: " + MetadataKeys.SESSION_ID
        }

        return chain.proceed(message.andMetadata(metadata), context)
    }
}
