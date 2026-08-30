package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.permissioncatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor


interface SpringDataPermissionCatalogReadModelRepository : JpaRepository<PermissionCatalogReadModelEntity, String>, JpaSpecificationExecutor<PermissionCatalogReadModelEntity> {

}
