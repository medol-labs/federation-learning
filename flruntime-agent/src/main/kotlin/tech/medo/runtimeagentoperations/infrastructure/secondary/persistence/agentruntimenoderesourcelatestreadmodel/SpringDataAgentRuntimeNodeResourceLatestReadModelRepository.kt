package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentruntimenoderesourcelatestreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataAgentRuntimeNodeResourceLatestReadModelRepository : JpaRepository<AgentRuntimeNodeResourceLatestReadModelEntity, UUID> {

}
