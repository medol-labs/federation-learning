package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.runtimehealthdashboardreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataRuntimeHealthDashboardReadModelRepository : JpaRepository<RuntimeHealthDashboardReadModelEntity, UUID> {

}
