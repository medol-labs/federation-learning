package tech.medo.identityaccessmanagement.infrastructure.secondary.persistence.useraccountcatalogreadmodel

import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;

import tech.medo.identityaccessmanagement.identityaccesscatalogs.UserAccountCatalogReadModel
import tech.medo.identityaccessmanagement.identityaccesscatalogs.UserAccountCatalogReadModelCriteria
import tech.medo.identityaccessmanagement.identityaccesscatalogs.UserAccountCatalogReadModelProjection
import tech.medo.identityaccessmanagement.identityaccesscatalogs.UserAccountCatalogReadModelRepository
import tech.medo.identityaccessmanagement.identityaccesscatalogs.toReadModel

@Repository
class JpaUserAccountCatalogReadModelRepository(
    private val jpaRepository: SpringDataUserAccountCatalogReadModelRepository,
    private val queryService: UserAccountCatalogReadModelQueryService
) : UserAccountCatalogReadModelRepository {
    override fun findAllByFilter(username: String?, providerSubject: String?, organizationId: UUID?, active: Boolean?, pageable: Pageable): Page<UserAccountCatalogReadModel> =
        jpaRepository.findAll(filters(username, providerSubject, organizationId, active), pageable).map { it.toProjection().toReadModel() }

    override fun findAllByCriteria(criteria: UserAccountCatalogReadModelCriteria?, pageable: Pageable): Page<UserAccountCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): UserAccountCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): UserAccountCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: UserAccountCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun filters(username: String?, providerSubject: String?, organizationId: UUID?, active: Boolean?): Specification<UserAccountCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            username?.let { predicates.add(criteriaBuilder.equal(root.get<String>("username"), it)) }
            providerSubject?.let { predicates.add(criteriaBuilder.equal(root.get<String>("providerSubject"), it)) }
            organizationId?.let { predicates.add(criteriaBuilder.equal(root.get<UUID>("organizationId"), it)) }
            active?.let { predicates.add(criteriaBuilder.equal(root.get<Boolean>("active"), it)) }
            criteriaBuilder.and(*predicates.toTypedArray())
        }


    private fun UserAccountCatalogReadModelEntity.toProjection(): UserAccountCatalogReadModelProjection =
        UserAccountCatalogReadModelProjection().also {
            it.userAccountId = this@toProjection.userAccountId
            it.username = this@toProjection.username
            it.providerSubject = this@toProjection.providerSubject
            it.organizationId = this@toProjection.organizationId
            it.active = this@toProjection.active
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
            it.organizationId = this@toEntity.organizationId
            it.active = this@toEntity.active
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
