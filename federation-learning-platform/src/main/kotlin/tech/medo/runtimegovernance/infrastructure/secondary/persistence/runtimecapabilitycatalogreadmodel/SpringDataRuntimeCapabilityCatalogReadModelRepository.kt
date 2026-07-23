package tech.medo.runtimegovernance.infrastructure.secondary.persistence.runtimecapabilitycatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataRuntimeCapabilityCatalogReadModelRepository : JpaRepository<RuntimeCapabilityCatalogReadModelEntity, UUID> {

}
