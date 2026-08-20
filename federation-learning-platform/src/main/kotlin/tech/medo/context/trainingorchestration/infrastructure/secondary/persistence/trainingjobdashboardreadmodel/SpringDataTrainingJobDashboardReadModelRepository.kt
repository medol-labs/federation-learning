package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingjobdashboardreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import tech.medo.trainingorchestration.domain.states.TrainingJobStateEnum;
import java.math.BigDecimal;


interface SpringDataTrainingJobDashboardReadModelRepository : JpaRepository<TrainingJobDashboardReadModelEntity, UUID>, JpaSpecificationExecutor<TrainingJobDashboardReadModelEntity> {

}
