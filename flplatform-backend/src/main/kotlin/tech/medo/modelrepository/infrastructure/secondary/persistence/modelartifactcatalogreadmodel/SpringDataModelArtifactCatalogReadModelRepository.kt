package tech.medo.modelrepository.infrastructure.secondary.persistence.modelartifactcatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataModelArtifactCatalogReadModelRepository : JpaRepository<ModelArtifactCatalogReadModelEntity, UUID> {

}
