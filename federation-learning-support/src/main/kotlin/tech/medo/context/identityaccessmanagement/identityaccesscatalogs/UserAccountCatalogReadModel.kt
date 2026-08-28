package tech.medo.identityaccessmanagement.identityaccesscatalogs

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;

import tech.jhipster.service.filter.BooleanFilter
import tech.jhipster.service.filter.StringFilter


class UserAccountCatalogReadModelQuery

class UserAccountCatalogReadModelCriteria {
    var userAccountId: StringFilter? = null
    var username: StringFilter? = null
    var providerSubject: StringFilter? = null
    var organizationId: StringFilter? = null
    var active: BooleanFilter? = null
}


class UserAccountCatalogReadModelProjection : MetadataProjection {
    var userAccountId: UUID? = null
    var username: String? = null
    var providerSubject: String? = null
    var organizationId: UUID? = null
    var active: Boolean? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun UserAccountCatalogReadModelProjection.toReadModel(): UserAccountCatalogReadModel =
    UserAccountCatalogReadModel(
    userAccountId = userAccountId,
    username = username,
    providerSubject = providerSubject,
    organizationId = organizationId,
    active = active,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface UserAccountCatalogReadModelRepository {
    fun findAllByFilter(username: String?, providerSubject: String?, organizationId: UUID?, active: Boolean?, pageable: Pageable): Page<UserAccountCatalogReadModel>
    fun findAllByCriteria(criteria: UserAccountCatalogReadModelCriteria?, pageable: Pageable): Page<UserAccountCatalogReadModel>
    fun findById(id: UUID): UserAccountCatalogReadModel?
    fun findProjectionById(id: UUID): UserAccountCatalogReadModelProjection?
    fun save(projection: UserAccountCatalogReadModelProjection)
}

data class UserAccountCatalogReadModel(
    val userAccountId: UUID?,
    val username: String?,
    val providerSubject: String?,
    val organizationId: UUID?,
    val active: Boolean?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
