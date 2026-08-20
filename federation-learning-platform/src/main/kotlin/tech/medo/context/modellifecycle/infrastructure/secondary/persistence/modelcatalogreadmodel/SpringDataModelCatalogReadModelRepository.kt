package tech.medo.modellifecycle.infrastructure.secondary.persistence.modelcatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import java.math.BigDecimal;
import tech.medo.modellifecycle.domain.states.ModelStateEnum;


interface SpringDataModelCatalogReadModelRepository : JpaRepository<ModelCatalogReadModelEntity, UUID>, JpaSpecificationExecutor<ModelCatalogReadModelEntity> {

}
