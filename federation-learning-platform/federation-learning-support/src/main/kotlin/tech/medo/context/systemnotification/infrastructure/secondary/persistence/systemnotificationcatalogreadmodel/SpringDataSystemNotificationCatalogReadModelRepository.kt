package tech.medo.systemnotification.infrastructure.secondary.persistence.systemnotificationcatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import tech.medo.systemnotification.domain.types.NotificationCategory;
import tech.medo.systemnotification.domain.types.NotificationSeverity;
import tech.medo.systemnotification.domain.states.SystemNotificationStateEnum;
import tech.medo.systemnotification.domain.types.NotificationToastPolicy;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


interface SpringDataSystemNotificationCatalogReadModelRepository : JpaRepository<SystemNotificationCatalogReadModelEntity, UUID>, JpaSpecificationExecutor<SystemNotificationCatalogReadModelEntity> {

}
