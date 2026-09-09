package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.serviceaccountapitokencatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

import java.util.UUID;

import tech.medo.identityaccessmanagement.serviceaccountapitokencatalogs.ServiceAccountApiTokenCatalogReadModel
import tech.medo.identityaccessmanagement.serviceaccountapitokencatalogs.ServiceAccountApiTokenCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.serviceaccountapitokencatalogs.ServiceAccountApiTokenCatalogReadModelProjection
import tech.medo.identityaccessmanagement.serviceaccountapitokencatalogs.ServiceAccountApiTokenCatalogReadModelRepository
import tech.medo.identityaccessmanagement.serviceaccountapitokencatalogs.toReadModel

@Repository
class JpaServiceAccountApiTokenCatalogReadModelRepository(
    private val jpaRepository: SpringDataServiceAccountApiTokenCatalogReadModelRepository,
    private val queryService: ServiceAccountApiTokenCatalogReadModelQueryService,
    private val objectMapper: ObjectMapper
) : ServiceAccountApiTokenCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<ServiceAccountApiTokenCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: ServiceAccountApiTokenCatalogReadModelCriteria?, pageable: Pageable): Page<ServiceAccountApiTokenCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): ServiceAccountApiTokenCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): ServiceAccountApiTokenCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: ServiceAccountApiTokenCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
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

    private fun ServiceAccountApiTokenCatalogReadModelProjection.toEntity(): ServiceAccountApiTokenCatalogReadModelEntity =
        ServiceAccountApiTokenCatalogReadModelEntity().also {
            it.apiTokenId = this@toEntity.apiTokenId
            it.userAccountId = this@toEntity.userAccountId
            it.username = this@toEntity.username
            it.tokenName = this@toEntity.tokenName
            it.tokenPrefix = this@toEntity.tokenPrefix
            it.issuedAt = this@toEntity.issuedAt
            it.roles = objectMapper.writeValueAsString(this@toEntity.roles)
            it.permissions = objectMapper.writeValueAsString(this@toEntity.permissions)
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
