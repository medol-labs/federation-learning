package tech.medo.identityaccessmanagement.serviceaccountapitokencatalogs

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;

import tech.jhipster.service.filter.StringFilter


class ServiceAccountApiTokenCatalogReadModelQuery

class ServiceAccountApiTokenCatalogReadModelCriteria {
    var apiTokenId: StringFilter? = null
    var userAccountId: StringFilter? = null
    var username: StringFilter? = null
    var tokenName: StringFilter? = null
    var tokenPrefix: StringFilter? = null
    var issuedAt: StringFilter? = null
}


class ServiceAccountApiTokenCatalogReadModelProjection : MetadataProjection {
    var apiTokenId: UUID? = null
    var userAccountId: UUID? = null
    var username: String? = null
    var tokenName: String? = null
    var tokenPrefix: String? = null
    var issuedAt: String? = null
    var roles: List<String> = emptyList()
    var permissions: List<String> = emptyList()
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun ServiceAccountApiTokenCatalogReadModelProjection.toReadModel(): ServiceAccountApiTokenCatalogReadModel =
    ServiceAccountApiTokenCatalogReadModel(
    apiTokenId = apiTokenId,
    userAccountId = userAccountId,
    username = username,
    tokenName = tokenName,
    tokenPrefix = tokenPrefix,
    issuedAt = issuedAt,
    roles = roles,
    permissions = permissions,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface ServiceAccountApiTokenCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<ServiceAccountApiTokenCatalogReadModel>
    fun findAllByCriteria(criteria: ServiceAccountApiTokenCatalogReadModelCriteria?, pageable: Pageable): Page<ServiceAccountApiTokenCatalogReadModel>
    fun findById(id: UUID): ServiceAccountApiTokenCatalogReadModel?
    fun findProjectionById(id: UUID): ServiceAccountApiTokenCatalogReadModelProjection?
    fun save(projection: ServiceAccountApiTokenCatalogReadModelProjection)
}

data class ServiceAccountApiTokenCatalogReadModel(
    val apiTokenId: UUID?,
    val userAccountId: UUID?,
    val username: String?,
    val tokenName: String?,
    val tokenPrefix: String?,
    val issuedAt: String?,
    val roles: List<String>,
    val permissions: List<String>,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
