package tech.medo.modellifecycle.infrastructure.secondary.persistence.modelversioncatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataModelVersionCatalogReadModelRepository : JpaRepository<ModelVersionCatalogReadModelEntity, UUID> {

}
