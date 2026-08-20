package tech.medo.federationmanagement.infrastructure.secondary.persistence.federationoverviewreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import tech.medo.federationmanagement.domain.states.FederationStateEnum;


interface SpringDataFederationOverviewReadModelRepository : JpaRepository<FederationOverviewReadModelEntity, UUID>, JpaSpecificationExecutor<FederationOverviewReadModelEntity> {

}
