package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.useraccountcatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

import java.util.UUID;

import tech.medo.identityaccessmanagement.identityaccesscatalogs.UserAccountCatalogReadModel
import tech.medo.identityaccessmanagement.identityaccesscatalogs.UserAccountCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.identityaccesscatalogs.UserAccountCatalogReadModelProjection
import tech.medo.identityaccessmanagement.identityaccesscatalogs.UserAccountCatalogReadModelRepository
import tech.medo.identityaccessmanagement.identityaccesscatalogs.toReadModel

@Repository
class JpaUserAccountCatalogReadModelRepository(
    private val jpaRepository: SpringDataUserAccountCatalogReadModelRepository,
    private val queryService: UserAccountCatalogReadModelQueryService,
    private val objectMapper: ObjectMapper
) : UserAccountCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<UserAccountCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: UserAccountCatalogReadModelCriteria?, pageable: Pageable): Page<UserAccountCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): UserAccountCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): UserAccountCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: UserAccountCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun UserAccountCatalogReadModelEntity.toProjection(): UserAccountCatalogReadModelProjection =
        UserAccountCatalogReadModelProjection().also {
            it.userAccountId = this@toProjection.userAccountId
            it.username = this@toProjection.username
            it.providerSubject = this@toProjection.providerSubject
            it.passwordHash = this@toProjection.passwordHash
            it.active = this@toProjection.active
            it.roleCodes = this@toProjection.roleCodes?.let { json -> objectMapper.readValue(json, object : com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) } ?: emptyList()
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun UserAccountCatalogReadModelProjection.toEntity(): UserAccountCatalogReadModelEntity =
        UserAccountCatalogReadModelEntity().also {
            it.userAccountId = this@toEntity.userAccountId
            it.username = this@toEntity.username
            it.providerSubject = this@toEntity.providerSubject
            it.passwordHash = this@toEntity.passwordHash
            it.active = this@toEntity.active
            it.roleCodes = objectMapper.writeValueAsString(this@toEntity.roleCodes)
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
