package tech.medo.trainingorchestration.infrastructure.secondary.persistence.personalizedmodelcatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import java.math.BigDecimal;
import tech.medo.trainingorchestration.domain.states.PersonalizedModelResultStateEnum;


interface SpringDataPersonalizedModelCatalogReadModelRepository : JpaRepository<PersonalizedModelCatalogReadModelEntity, UUID>, JpaSpecificationExecutor<PersonalizedModelCatalogReadModelEntity> {

}
