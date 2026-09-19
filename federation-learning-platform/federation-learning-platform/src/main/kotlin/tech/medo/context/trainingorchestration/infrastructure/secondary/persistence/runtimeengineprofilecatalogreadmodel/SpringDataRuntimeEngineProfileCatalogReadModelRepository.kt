package tech.medo.trainingorchestration.infrastructure.secondary.persistence.runtimeengineprofilecatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import tech.medo.trainingorchestration.domain.states.RuntimeEngineProfileStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


interface SpringDataRuntimeEngineProfileCatalogReadModelRepository : JpaRepository<RuntimeEngineProfileCatalogReadModelEntity, UUID>, JpaSpecificationExecutor<RuntimeEngineProfileCatalogReadModelEntity> {

}
