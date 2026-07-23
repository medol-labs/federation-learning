package tech.medo.federationmanagement.infrastructure.secondary.persistence.federationmembershipdirectoryreadmodel

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID;

import tech.medo.federationmanagement.federationmembershipdirectory.FederationMembershipDirectoryReadModelKey

interface SpringDataFederationMembershipDirectoryReadModelRepository : JpaRepository<FederationMembershipDirectoryReadModelEntity, FederationMembershipDirectoryReadModelKey> {
    fun findAllByFederationId(federationId: UUID): List<FederationMembershipDirectoryReadModelEntity>
    fun findAllByOrganizationId(organizationId: UUID): List<FederationMembershipDirectoryReadModelEntity>
}
