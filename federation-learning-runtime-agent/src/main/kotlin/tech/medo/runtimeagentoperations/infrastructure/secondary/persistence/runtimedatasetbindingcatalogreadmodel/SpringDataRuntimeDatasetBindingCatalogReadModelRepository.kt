package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.runtimedatasetbindingcatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataRuntimeDatasetBindingCatalogReadModelRepository : JpaRepository<RuntimeDatasetBindingCatalogReadModelEntity, UUID> {

}
