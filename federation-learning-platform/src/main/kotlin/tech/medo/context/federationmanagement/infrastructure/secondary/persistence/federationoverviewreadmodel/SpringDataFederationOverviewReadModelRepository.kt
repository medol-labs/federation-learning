package tech.medo.federationmanagement.infrastructure.secondary.persistence.federationoverviewreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataFederationOverviewReadModelRepository : JpaRepository<FederationOverviewReadModelEntity, UUID> {

}
