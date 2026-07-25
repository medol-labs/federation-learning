package tech.medo.organizationmanagement.infrastructure.secondary.persistence.organizationdirectoryreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;


interface SpringDataOrganizationDirectoryReadModelRepository : JpaRepository<OrganizationDirectoryReadModelEntity, UUID> {

}
