package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.runtimehealthdashboardreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


interface SpringDataRuntimeHealthDashboardReadModelRepository : JpaRepository<RuntimeHealthDashboardReadModelEntity, UUID>, JpaSpecificationExecutor<RuntimeHealthDashboardReadModelEntity> {

}
