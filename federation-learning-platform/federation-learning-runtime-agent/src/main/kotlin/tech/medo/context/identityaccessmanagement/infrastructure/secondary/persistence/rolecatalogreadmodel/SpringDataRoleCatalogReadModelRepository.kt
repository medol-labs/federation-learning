package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.rolecatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;


interface SpringDataRoleCatalogReadModelRepository : JpaRepository<RoleCatalogReadModelEntity, UUID>, JpaSpecificationExecutor<RoleCatalogReadModelEntity> {

}
