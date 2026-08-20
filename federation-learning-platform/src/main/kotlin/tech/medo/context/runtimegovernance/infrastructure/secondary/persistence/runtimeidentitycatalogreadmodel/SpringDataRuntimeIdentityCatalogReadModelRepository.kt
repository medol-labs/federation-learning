package tech.medo.runtimegovernance.infrastructure.secondary.persistence.runtimeidentitycatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


interface SpringDataRuntimeIdentityCatalogReadModelRepository : JpaRepository<RuntimeIdentityCatalogReadModelEntity, UUID>, JpaSpecificationExecutor<RuntimeIdentityCatalogReadModelEntity> {

}
