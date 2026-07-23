package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.auditrecordlogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataAuditRecordLogReadModelRepository : JpaRepository<AuditRecordLogReadModelEntity, UUID> {

}
