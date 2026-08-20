package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinstallationguidereadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;


interface SpringDataRuntimeInstallationGuideReadModelRepository : JpaRepository<RuntimeInstallationGuideReadModelEntity, UUID>, JpaSpecificationExecutor<RuntimeInstallationGuideReadModelEntity> {

}
