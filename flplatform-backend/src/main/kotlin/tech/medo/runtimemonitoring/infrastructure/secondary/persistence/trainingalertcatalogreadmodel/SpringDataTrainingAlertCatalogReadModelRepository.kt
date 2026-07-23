package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.trainingalertcatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataTrainingAlertCatalogReadModelRepository : JpaRepository<TrainingAlertCatalogReadModelEntity, UUID> {

}
