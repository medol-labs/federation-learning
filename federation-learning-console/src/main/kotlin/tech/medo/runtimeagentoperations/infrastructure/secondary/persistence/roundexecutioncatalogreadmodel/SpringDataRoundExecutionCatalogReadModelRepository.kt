package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.roundexecutioncatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataRoundExecutionCatalogReadModelRepository : JpaRepository<RoundExecutionCatalogReadModelEntity, UUID> {

}
