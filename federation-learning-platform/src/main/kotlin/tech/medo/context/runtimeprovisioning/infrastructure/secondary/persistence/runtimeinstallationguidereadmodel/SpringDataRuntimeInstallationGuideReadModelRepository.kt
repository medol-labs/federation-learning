package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinstallationguidereadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataRuntimeInstallationGuideReadModelRepository : JpaRepository<RuntimeInstallationGuideReadModelEntity, UUID> {

}
