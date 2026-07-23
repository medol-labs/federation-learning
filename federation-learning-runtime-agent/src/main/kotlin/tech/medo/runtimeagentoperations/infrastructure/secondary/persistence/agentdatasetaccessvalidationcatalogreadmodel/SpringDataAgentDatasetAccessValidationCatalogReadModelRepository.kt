package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentdatasetaccessvalidationcatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataAgentDatasetAccessValidationCatalogReadModelRepository : JpaRepository<AgentDatasetAccessValidationCatalogReadModelEntity, UUID> {

}
