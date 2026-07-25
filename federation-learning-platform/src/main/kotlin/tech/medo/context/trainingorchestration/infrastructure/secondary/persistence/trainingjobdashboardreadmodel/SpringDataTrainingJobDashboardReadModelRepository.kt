package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingjobdashboardreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataTrainingJobDashboardReadModelRepository : JpaRepository<TrainingJobDashboardReadModelEntity, UUID> {

}
