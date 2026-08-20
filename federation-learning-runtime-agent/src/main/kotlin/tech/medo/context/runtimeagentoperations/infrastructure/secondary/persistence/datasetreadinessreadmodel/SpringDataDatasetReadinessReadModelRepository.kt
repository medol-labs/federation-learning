package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.datasetreadinessreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


interface SpringDataDatasetReadinessReadModelRepository : JpaRepository<DatasetReadinessReadModelEntity, UUID>, JpaSpecificationExecutor<DatasetReadinessReadModelEntity> {

}
