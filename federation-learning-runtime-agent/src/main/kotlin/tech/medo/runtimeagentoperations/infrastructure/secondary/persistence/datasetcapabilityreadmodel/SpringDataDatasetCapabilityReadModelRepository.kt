package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.datasetcapabilityreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataDatasetCapabilityReadModelRepository : JpaRepository<DatasetCapabilityReadModelEntity, UUID> {

}
