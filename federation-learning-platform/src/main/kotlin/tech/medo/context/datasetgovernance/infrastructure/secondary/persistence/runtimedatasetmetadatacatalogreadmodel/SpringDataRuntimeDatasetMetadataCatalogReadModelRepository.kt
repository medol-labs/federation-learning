package tech.medo.datasetgovernance.infrastructure.secondary.persistence.runtimedatasetmetadatacatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataRuntimeDatasetMetadataCatalogReadModelRepository : JpaRepository<RuntimeDatasetMetadataCatalogReadModelEntity, UUID> {

}
