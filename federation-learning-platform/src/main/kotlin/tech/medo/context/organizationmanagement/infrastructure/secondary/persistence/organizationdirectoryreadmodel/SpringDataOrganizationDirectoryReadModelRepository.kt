package tech.medo.organizationmanagement.infrastructure.secondary.persistence.organizationdirectoryreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID;
import tech.medo.organizationmanagement.domain.types.OrganizationType;
import tech.medo.organizationmanagement.domain.states.OrganizationStateEnum;


interface SpringDataOrganizationDirectoryReadModelRepository : JpaRepository<OrganizationDirectoryReadModelEntity, UUID>, JpaSpecificationExecutor<OrganizationDirectoryReadModelEntity> {

}
