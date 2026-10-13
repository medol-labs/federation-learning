package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentorganizationdirectoryreadmodel

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.shared.application.access.ReadModelAccessPolicy
import tech.medo.shared.application.access.ReadModelAccessPolicyFilter
import tech.medo.runtimeagentoperations.agentorganizationdirectory.AgentOrganizationDirectoryReadModel
import tech.medo.runtimeagentoperations.agentorganizationdirectory.AgentOrganizationDirectoryReadModelCriteria
import tech.medo.runtimeagentoperations.agentorganizationdirectory.AgentOrganizationDirectoryReadModelProjection
import tech.medo.runtimeagentoperations.agentorganizationdirectory.AgentOrganizationDirectoryReadModelRepository
import tech.medo.runtimeagentoperations.agentorganizationdirectory.toReadModel

@Repository
class JpaAgentOrganizationDirectoryReadModelRepository(
    private val jpaRepository: SpringDataAgentOrganizationDirectoryReadModelRepository,
    private val accessPolicyFilter: ReadModelAccessPolicyFilter,
    private val queryService: AgentOrganizationDirectoryReadModelQueryService
) : AgentOrganizationDirectoryReadModelRepository {
    override fun findAll(pageable: Pageable): Page<AgentOrganizationDirectoryReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: AgentOrganizationDirectoryReadModelCriteria?, pageable: Pageable): Page<AgentOrganizationDirectoryReadModel> =
        jpaRepository.findAll(queryService.createSpecification(criteria).and(accessSpecification()), pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): AgentOrganizationDirectoryReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findByIdWithAccessPolicy(id: UUID): AgentOrganizationDirectoryReadModel? =
        jpaRepository.findOne(idSpecification(id).and(accessSpecification())).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): AgentOrganizationDirectoryReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: AgentOrganizationDirectoryReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun idSpecification(id: UUID): Specification<AgentOrganizationDirectoryReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.get<UUID>("organizationId"), id)
        }

    private fun accessSpecification(): Specification<AgentOrganizationDirectoryReadModelEntity> =
        accessPolicyFilter.specification(
            contextName = "RuntimeAgentOperations",
            readModelName = "AgentOrganizationDirectory",
            policy = ReadModelAccessPolicy(
            name = null,
            fields = emptyList<String>()
        )
        )

    private fun AgentOrganizationDirectoryReadModelEntity.toProjection(): AgentOrganizationDirectoryReadModelProjection =
        AgentOrganizationDirectoryReadModelProjection().also {
            it.organizationId = this@toProjection.organizationId
            it.organizationName = this@toProjection.organizationName
            it.organizationType = this@toProjection.organizationType
            it.state = this@toProjection.state
            it.syncedAt = this@toProjection.syncedAt
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun AgentOrganizationDirectoryReadModelProjection.toEntity(): AgentOrganizationDirectoryReadModelEntity =
        AgentOrganizationDirectoryReadModelEntity().also {
            it.organizationId = this@toEntity.organizationId
            it.organizationName = this@toEntity.organizationName
            it.organizationType = this@toEntity.organizationType
            it.state = this@toEntity.state
            it.syncedAt = this@toEntity.syncedAt
            it.projectionUpdatedAt = this@toEntity.projectionUpdatedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
