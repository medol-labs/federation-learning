package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.rolecatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

import java.util.UUID;

import tech.medo.identityaccessmanagement.identityaccesscatalogs.RoleCatalogReadModel
import tech.medo.identityaccessmanagement.identityaccesscatalogs.RoleCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.identityaccesscatalogs.RoleCatalogReadModelProjection
import tech.medo.identityaccessmanagement.identityaccesscatalogs.RoleCatalogReadModelRepository
import tech.medo.identityaccessmanagement.identityaccesscatalogs.toReadModel

@Repository
class JpaRoleCatalogReadModelRepository(
    private val jpaRepository: SpringDataRoleCatalogReadModelRepository,
    private val queryService: RoleCatalogReadModelQueryService,
    private val objectMapper: ObjectMapper
) : RoleCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RoleCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: RoleCatalogReadModelCriteria?, pageable: Pageable): Page<RoleCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): RoleCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RoleCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RoleCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun RoleCatalogReadModelEntity.toProjection(): RoleCatalogReadModelProjection =
        RoleCatalogReadModelProjection().also {
            it.roleId = this@toProjection.roleId
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

    private fun RoleCatalogReadModelProjection.toEntity(): RoleCatalogReadModelEntity =
        RoleCatalogReadModelEntity().also {
            it.roleId = this@toEntity.roleId
            it.roleCode = this@toEntity.roleCode
            it.roleName = this@toEntity.roleName
            it.permissionCodes = objectMapper.writeValueAsString(this@toEntity.permissionCodes)
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
