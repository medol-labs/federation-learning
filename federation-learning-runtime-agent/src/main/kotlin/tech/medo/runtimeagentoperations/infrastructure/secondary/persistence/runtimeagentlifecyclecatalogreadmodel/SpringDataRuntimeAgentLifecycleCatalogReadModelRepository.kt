package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.runtimeagentlifecyclecatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataRuntimeAgentLifecycleCatalogReadModelRepository : JpaRepository<RuntimeAgentLifecycleCatalogReadModelEntity, UUID> {

}
