package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.runtimenoderesourcelatestreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataRuntimeNodeResourceLatestReadModelRepository : JpaRepository<RuntimeNodeResourceLatestReadModelEntity, UUID> {

}
