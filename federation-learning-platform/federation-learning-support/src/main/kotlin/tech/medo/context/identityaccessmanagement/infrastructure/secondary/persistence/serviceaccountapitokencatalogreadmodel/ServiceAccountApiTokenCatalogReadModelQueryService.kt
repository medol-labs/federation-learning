package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.serviceaccountapitokencatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Root
import org.hibernate.query.criteria.JpaExpression
import tech.jhipster.service.QueryService
import java.util.function.Function
import java.util.UUID;

import tech.medo.identityaccessmanagement.serviceaccountapitokencatalogs.ServiceAccountApiTokenCatalogReadModel
import tech.medo.identityaccessmanagement.serviceaccountapitokencatalogs.ServiceAccountApiTokenCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.serviceaccountapitokencatalogs.ServiceAccountApiTokenCatalogReadModelProjection
import tech.medo.identityaccessmanagement.serviceaccountapitokencatalogs.toReadModel

@Service
class ServiceAccountApiTokenCatalogReadModelQueryService(
    private val repository: SpringDataServiceAccountApiTokenCatalogReadModelRepository,
    private val objectMapper: com.fasterxml.jackson.databind.ObjectMapper
) : QueryService<ServiceAccountApiTokenCatalogReadModelEntity>() {
    fun findByCriteria(criteria: ServiceAccountApiTokenCatalogReadModelCriteria?, pageable: Pageable): Page<ServiceAccountApiTokenCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: ServiceAccountApiTokenCatalogReadModelCriteria?): Specification<ServiceAccountApiTokenCatalogReadModelEntity> {
        var specification = Specification.where<ServiceAccountApiTokenCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.apiTokenId?.let { specification = specification.and(buildSpecification(it, Function<Root<ServiceAccountApiTokenCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("apiTokenId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.userAccountId?.let { specification = specification.and(buildSpecification(it, Function<Root<ServiceAccountApiTokenCatalogReadModelEntity>, Expression<String>> { root -> (root.get<UUID>("userAccountId") as JpaExpression<UUID>).cast(String::class.java) })) }
            criteria.username?.let { specification = specification.and(buildSpecification(it, Function<Root<ServiceAccountApiTokenCatalogReadModelEntity>, Expression<String>> { root -> root.get("username") })) }
            criteria.tokenName?.let { specification = specification.and(buildSpecification(it, Function<Root<ServiceAccountApiTokenCatalogReadModelEntity>, Expression<String>> { root -> root.get("tokenName") })) }
            criteria.tokenPrefix?.let { specification = specification.and(buildSpecification(it, Function<Root<ServiceAccountApiTokenCatalogReadModelEntity>, Expression<String>> { root -> root.get("tokenPrefix") })) }
            criteria.issuedAt?.let { specification = specification.and(buildSpecification(it, Function<Root<ServiceAccountApiTokenCatalogReadModelEntity>, Expression<String>> { root -> root.get("issuedAt") })) }
        }
        return specification
    }

    private fun ServiceAccountApiTokenCatalogReadModelEntity.toProjection(): ServiceAccountApiTokenCatalogReadModelProjection =
        ServiceAccountApiTokenCatalogReadModelProjection().also {
            it.apiTokenId = this@toProjection.apiTokenId
            it.userAccountId = this@toProjection.userAccountId
            it.username = this@toProjection.username
            it.tokenName = this@toProjection.tokenName
            it.tokenPrefix = this@toProjection.tokenPrefix
            it.issuedAt = this@toProjection.issuedAt
            it.roles = this@toProjection.roles?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) } ?: emptyList()
            it.permissions = this@toProjection.permissions?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) } ?: emptyList()
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
