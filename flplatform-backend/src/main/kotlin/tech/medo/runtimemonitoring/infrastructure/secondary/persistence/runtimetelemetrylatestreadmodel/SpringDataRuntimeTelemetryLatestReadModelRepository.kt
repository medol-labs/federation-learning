package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.runtimetelemetrylatestreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataRuntimeTelemetryLatestReadModelRepository : JpaRepository<RuntimeTelemetryLatestReadModelEntity, UUID> {

}
