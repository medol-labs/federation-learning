package tech.medo.runtimegovernance.infrastructure.secondary.persistence.runtimeidentitycatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataRuntimeIdentityCatalogReadModelRepository : JpaRepository<RuntimeIdentityCatalogReadModelEntity, UUID> {

}
