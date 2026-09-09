package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.userroleassignmentcatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;

import tech.medo.identityaccessmanagement.userroleassignmentcatalog.UserRoleAssignmentCatalogReadModelKey

interface SpringDataUserRoleAssignmentCatalogReadModelRepository : JpaRepository<UserRoleAssignmentCatalogReadModelEntity, UserRoleAssignmentCatalogReadModelKey>, JpaSpecificationExecutor<UserRoleAssignmentCatalogReadModelEntity> {
    fun findAllByUserAccountId(userAccountId: UUID): List<UserRoleAssignmentCatalogReadModelEntity>
    fun findAllByRoleCode(roleCode: String): List<UserRoleAssignmentCatalogReadModelEntity>
}
