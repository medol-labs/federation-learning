package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.useraccountcatalogreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;


interface SpringDataUserAccountCatalogReadModelRepository : JpaRepository<UserAccountCatalogReadModelEntity, UUID>, JpaSpecificationExecutor<UserAccountCatalogReadModelEntity> {

}
