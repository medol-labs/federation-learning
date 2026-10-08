package tech.medo.systemnotification.infrastructure.secondary.persistence.systemnotificationsummaryreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import tech.medo.systemnotification.domain.types.NotificationSeverity;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import tech.medo.systemnotification.domain.types.NotificationToastPolicy;


interface SpringDataSystemNotificationSummaryReadModelRepository : JpaRepository<SystemNotificationSummaryReadModelEntity, String>, JpaSpecificationExecutor<SystemNotificationSummaryReadModelEntity> {

}
