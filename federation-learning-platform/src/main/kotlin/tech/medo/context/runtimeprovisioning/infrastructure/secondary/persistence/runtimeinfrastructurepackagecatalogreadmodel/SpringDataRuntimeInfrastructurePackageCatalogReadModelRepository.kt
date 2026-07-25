package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinfrastructurepackagecatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataRuntimeInfrastructurePackageCatalogReadModelRepository : JpaRepository<RuntimeInfrastructurePackageCatalogReadModelEntity, UUID> {

}
