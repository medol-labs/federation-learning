package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.datasetcapabilityreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import tech.medo.runtimeagentoperations.domain.types.FeatureDefinition;
import tech.medo.runtimeagentoperations.domain.types.LabelDefinition;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


interface SpringDataDatasetCapabilityReadModelRepository : JpaRepository<DatasetCapabilityReadModelEntity, UUID>, JpaSpecificationExecutor<DatasetCapabilityReadModelEntity> {

}
