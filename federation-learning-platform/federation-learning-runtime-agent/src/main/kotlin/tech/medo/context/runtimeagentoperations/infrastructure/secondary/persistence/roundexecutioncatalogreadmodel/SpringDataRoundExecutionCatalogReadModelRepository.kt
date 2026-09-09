package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.roundexecutioncatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import tech.medo.runtimeagentoperations.domain.states.RoundExecutionStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;


interface SpringDataRoundExecutionCatalogReadModelRepository : JpaRepository<RoundExecutionCatalogReadModelEntity, UUID>, JpaSpecificationExecutor<RoundExecutionCatalogReadModelEntity> {

}
