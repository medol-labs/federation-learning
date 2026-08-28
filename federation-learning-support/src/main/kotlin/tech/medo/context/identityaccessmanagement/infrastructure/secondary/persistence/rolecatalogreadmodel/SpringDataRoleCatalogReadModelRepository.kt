package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.rolecatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor


interface SpringDataRoleCatalogReadModelRepository : JpaRepository<RoleCatalogReadModelEntity, String>, JpaSpecificationExecutor<RoleCatalogReadModelEntity> {

}
