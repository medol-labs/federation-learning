package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentruntimenodeinventorycatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataAgentRuntimeNodeInventoryCatalogReadModelRepository : JpaRepository<AgentRuntimeNodeInventoryCatalogReadModelEntity, UUID> {

}
