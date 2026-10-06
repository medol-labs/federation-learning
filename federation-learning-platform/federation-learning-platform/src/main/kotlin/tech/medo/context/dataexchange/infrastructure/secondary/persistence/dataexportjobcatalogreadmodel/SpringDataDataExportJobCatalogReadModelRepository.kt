package tech.medo.dataexchange.infrastructure.secondary.persistence.dataexportjobcatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;


interface SpringDataDataExportJobCatalogReadModelRepository : JpaRepository<DataExportJobCatalogReadModelEntity, UUID>, JpaSpecificationExecutor<DataExportJobCatalogReadModelEntity> {

}
