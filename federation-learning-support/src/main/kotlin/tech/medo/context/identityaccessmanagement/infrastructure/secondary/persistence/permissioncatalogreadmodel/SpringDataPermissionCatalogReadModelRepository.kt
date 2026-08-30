package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.permissioncatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;


interface SpringDataPermissionCatalogReadModelRepository : JpaRepository<PermissionCatalogReadModelEntity, UUID>, JpaSpecificationExecutor<PermissionCatalogReadModelEntity> {

}
