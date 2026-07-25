package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.runtimenodeinventoryviewreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataRuntimeNodeInventoryViewReadModelRepository : JpaRepository<RuntimeNodeInventoryViewReadModelEntity, UUID> {

}
