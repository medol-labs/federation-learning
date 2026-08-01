package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.datasetcapabilityreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;
import tech.medo.runtimeagentoperations.domain.types.FeatureDefinition;
import tech.medo.runtimeagentoperations.domain.types.LabelDefinition;


interface SpringDataDatasetCapabilityReadModelRepository : JpaRepository<DatasetCapabilityReadModelEntity, UUID> {

}
