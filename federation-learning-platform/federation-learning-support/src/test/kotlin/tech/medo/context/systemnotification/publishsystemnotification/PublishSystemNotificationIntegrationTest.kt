package tech.medo.systemnotification.publishsystemnotification

import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tech.medo.systemnotification.publishsystemnotification.PublishSystemNotificationCommand
import java.util.UUID
import tech.medo.systemnotification.domain.types.NotificationCategory
import tech.medo.systemnotification.domain.types.NotificationSeverity
import tech.medo.systemnotification.domain.types.NotificationToastPolicy
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

@SpringBootTest(properties = [
    "spring.docker.compose.enabled=false",
    "spring.flyway.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:publish-system-notification-integration-test;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "axon.axonserver.enabled=false",
    "axon.axonserver.event-store.enabled=false",
    "medol.axon.event-storage=inmemory"
])
class PublishSystemNotificationIntegrationTest(
    @Autowired private val commandGateway: CommandGateway
) {
    @Test
    fun PublishSystemNotificationintegration() {
        val command = PublishSystemNotificationCommand(
            notificationId = java.util.UUID.randomUUID(),
            recipientUserId = "",
            recipientRole = null,
            category = NotificationCategory.SYSTEM,
            severity = NotificationSeverity.INFO,
            messageKey = "",
            messageArgsJson = null,
            fallbackTitle = "",
            fallbackBody = null,
            targetType = null,
            targetId = null,
            targetRoute = null,
            toastPolicy = NotificationToastPolicy.NONE,
            publishedAt = java.time.LocalDateTime.now(),
            status = ""
        )

        commandGateway.send(command).getResultMessage().join()
    }
}
