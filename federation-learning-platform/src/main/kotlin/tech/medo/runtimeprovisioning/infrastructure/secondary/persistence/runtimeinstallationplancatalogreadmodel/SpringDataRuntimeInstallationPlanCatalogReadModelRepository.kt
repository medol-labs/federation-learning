package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinstallationplancatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataRuntimeInstallationPlanCatalogReadModelRepository : JpaRepository<RuntimeInstallationPlanCatalogReadModelEntity, UUID> {

}
