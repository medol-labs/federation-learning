package tech.medo.shared.application.metadata

import org.axonframework.messaging.commandhandling.CommandMessage
import org.axonframework.messaging.core.correlation.MessageOriginProvider
import org.axonframework.messaging.core.correlation.SimpleCorrelationDataProvider
import org.axonframework.messaging.core.interception.CorrelationDataInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CorrelationConfig {
    @Bean
    fun metadataCorrelationDataProvider(): SimpleCorrelationDataProvider =
        SimpleCorrelationDataProvider(*MetadataKeys.PROPAGATED_KEYS)

    @Bean
    fun messageOriginProvider(): MessageOriginProvider =
        MessageOriginProvider(MetadataKeys.CORRELATION_ID, MetadataKeys.CAUSATION_ID)

    @Bean
    fun correlationDataInterceptor(): CorrelationDataInterceptor<CommandMessage> =
        CorrelationDataInterceptor(messageOriginProvider(), metadataCorrelationDataProvider())
}
