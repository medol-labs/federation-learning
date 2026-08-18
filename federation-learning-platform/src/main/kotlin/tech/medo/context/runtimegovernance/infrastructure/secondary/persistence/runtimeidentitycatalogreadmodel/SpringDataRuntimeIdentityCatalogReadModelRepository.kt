package tech.medo.runtimegovernance.infrastructure.secondary.persistence.runtimeidentitycatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;


interface SpringDataRuntimeIdentityCatalogReadModelRepository : JpaRepository<RuntimeIdentityCatalogReadModelEntity, UUID>, JpaSpecificationExecutor<RuntimeIdentityCatalogReadModelEntity> {

}
