package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.datasetreadinessreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataDatasetReadinessReadModelRepository : JpaRepository<DatasetReadinessReadModelEntity, UUID> {

}
