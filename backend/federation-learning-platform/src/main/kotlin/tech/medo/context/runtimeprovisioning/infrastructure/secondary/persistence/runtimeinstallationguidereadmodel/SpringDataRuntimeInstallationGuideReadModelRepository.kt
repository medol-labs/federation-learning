package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinstallationguidereadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum;


interface SpringDataRuntimeInstallationGuideReadModelRepository : JpaRepository<RuntimeInstallationGuideReadModelEntity, UUID>, JpaSpecificationExecutor<RuntimeInstallationGuideReadModelEntity> {

}
