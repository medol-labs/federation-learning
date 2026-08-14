package tech.medo.fileupload.infrastructure.secondary.persistence.stagedfilecatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;


interface SpringDataStagedFileCatalogReadModelRepository : JpaRepository<StagedFileCatalogReadModelEntity, UUID>, JpaSpecificationExecutor<StagedFileCatalogReadModelEntity> {

}
