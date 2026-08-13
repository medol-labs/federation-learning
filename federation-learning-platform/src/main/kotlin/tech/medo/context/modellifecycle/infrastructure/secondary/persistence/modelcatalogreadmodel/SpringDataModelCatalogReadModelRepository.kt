package tech.medo.modellifecycle.infrastructure.secondary.persistence.modelcatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataModelCatalogReadModelRepository : JpaRepository<ModelCatalogReadModelEntity, UUID> {

}
