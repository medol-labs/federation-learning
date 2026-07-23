package tech.medo.trainingorchestration.infrastructure.secondary.persistence.trainingrunconfigurationcatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataTrainingRunConfigurationCatalogReadModelRepository : JpaRepository<TrainingRunConfigurationCatalogReadModelEntity, UUID> {

}
