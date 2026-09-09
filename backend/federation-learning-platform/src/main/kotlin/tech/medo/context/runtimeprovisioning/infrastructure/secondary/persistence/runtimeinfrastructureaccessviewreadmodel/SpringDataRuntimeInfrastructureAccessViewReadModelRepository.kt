package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinfrastructureaccessviewreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructureStateEnum;


interface SpringDataRuntimeInfrastructureAccessViewReadModelRepository : JpaRepository<RuntimeInfrastructureAccessViewReadModelEntity, UUID>, JpaSpecificationExecutor<RuntimeInfrastructureAccessViewReadModelEntity> {

}
