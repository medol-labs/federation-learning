package tech.medo.datasetgovernance.infrastructure.secondary.persistence.featureschemacatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;
import tech.medo.datasetgovernance.domain.types.FeatureDefinition;
import tech.medo.datasetgovernance.domain.types.LabelDefinition;


interface SpringDataFeatureSchemaCatalogReadModelRepository : JpaRepository<FeatureSchemaCatalogReadModelEntity, UUID> {

}
