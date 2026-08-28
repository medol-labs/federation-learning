package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.rolecatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Root
import org.hibernate.query.criteria.JpaExpression
import tech.jhipster.service.QueryService
import java.util.function.Function

import tech.medo.identityaccessmanagement.identityaccesscatalogs.RoleCatalogReadModel
import tech.medo.identityaccessmanagement.identityaccesscatalogs.RoleCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.identityaccesscatalogs.RoleCatalogReadModelProjection
import tech.medo.identityaccessmanagement.identityaccesscatalogs.toReadModel

@Service
class RoleCatalogReadModelQueryService(
    private val repository: SpringDataRoleCatalogReadModelRepository,
    private val objectMapper: com.fasterxml.jackson.databind.ObjectMapper
) : QueryService<RoleCatalogReadModelEntity>() {
    fun findByCriteria(criteria: RoleCatalogReadModelCriteria?, pageable: Pageable): Page<RoleCatalogReadModel> =
        repository.findAll(createSpecification(criteria), pageable).map { it.toProjection().toReadModel() }

    private fun createSpecification(criteria: RoleCatalogReadModelCriteria?): Specification<RoleCatalogReadModelEntity> {
        var specification = Specification.where<RoleCatalogReadModelEntity>(null)
        if (criteria != null) {
            criteria.roleCode?.let { specification = specification.and(buildSpecification(it, Function<Root<RoleCatalogReadModelEntity>, Expression<String>> { root -> root.get("roleCode") })) }
            criteria.roleName?.let { specification = specification.and(buildSpecification(it, Function<Root<RoleCatalogReadModelEntity>, Expression<String>> { root -> root.get("roleName") })) }
        }
        return specification
    }

    private fun RoleCatalogReadModelEntity.toProjection(): RoleCatalogReadModelProjection =
        RoleCatalogReadModelProjection().also {
            it.roleCode = this@toProjection.roleCode
            it.roleName = this@toProjection.roleName
            it.permissionCodes = this@toProjection.permissionCodes?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) } ?: emptyList()
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }
}
