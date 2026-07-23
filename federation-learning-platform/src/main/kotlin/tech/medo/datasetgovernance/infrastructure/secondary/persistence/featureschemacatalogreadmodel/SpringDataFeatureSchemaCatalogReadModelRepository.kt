package tech.medo.datasetgovernance.infrastructure.secondary.persistence.featureschemacatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataFeatureSchemaCatalogReadModelRepository : JpaRepository<FeatureSchemaCatalogReadModelEntity, UUID> {

}
