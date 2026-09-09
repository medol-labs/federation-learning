package tech.medo.modelrepository.infrastructure.secondary.persistence.modelartifactcatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import tech.medo.modelrepository.domain.states.ModelArtifactStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


interface SpringDataModelArtifactCatalogReadModelRepository : JpaRepository<ModelArtifactCatalogReadModelEntity, UUID>, JpaSpecificationExecutor<ModelArtifactCatalogReadModelEntity> {

}
