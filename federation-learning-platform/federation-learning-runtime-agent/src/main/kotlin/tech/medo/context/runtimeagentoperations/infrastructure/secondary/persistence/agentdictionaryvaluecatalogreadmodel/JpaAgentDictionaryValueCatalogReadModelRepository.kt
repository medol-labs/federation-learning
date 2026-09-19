package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentdictionaryvaluecatalogreadmodel

import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.runtimeagentoperations.agentdictionaryvaluecatalog.AgentDictionaryValueCatalogReadModel
import tech.medo.runtimeagentoperations.agentdictionaryvaluecatalog.AgentDictionaryValueCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.agentdictionaryvaluecatalog.AgentDictionaryValueCatalogReadModelProjection
import tech.medo.runtimeagentoperations.agentdictionaryvaluecatalog.AgentDictionaryValueCatalogReadModelRepository
import tech.medo.runtimeagentoperations.agentdictionaryvaluecatalog.toReadModel

@Repository
class JpaAgentDictionaryValueCatalogReadModelRepository(
    private val jpaRepository: SpringDataAgentDictionaryValueCatalogReadModelRepository,
    private val queryService: AgentDictionaryValueCatalogReadModelQueryService
) : AgentDictionaryValueCatalogReadModelRepository {
    override fun findAllByFilter(dictionaryCode: String?, active: Boolean?, state: String?, pageable: Pageable): Page<AgentDictionaryValueCatalogReadModel> =
        jpaRepository.findAll(filters(dictionaryCode, active, state), pageable).map { it.toProjection().toReadModel() }

    override fun findAllByCriteria(criteria: AgentDictionaryValueCatalogReadModelCriteria?, pageable: Pageable): Page<AgentDictionaryValueCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): AgentDictionaryValueCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): AgentDictionaryValueCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: AgentDictionaryValueCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun filters(dictionaryCode: String?, active: Boolean?, state: String?): Specification<AgentDictionaryValueCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            dictionaryCode?.let { predicates.add(criteriaBuilder.equal(root.get<String>("dictionaryCode"), it)) }
            active?.let { predicates.add(criteriaBuilder.equal(root.get<Boolean>("active"), it)) }
            state?.let { predicates.add(criteriaBuilder.equal(root.get<String>("state"), it)) }
            criteriaBuilder.and(*predicates.toTypedArray())
        }


    private fun AgentDictionaryValueCatalogReadModelEntity.toProjection(): AgentDictionaryValueCatalogReadModelProjection =
        AgentDictionaryValueCatalogReadModelProjection().also {
            it.dictionaryValueId = this@toProjection.dictionaryValueId
            it.dictionaryId = this@toProjection.dictionaryId
            it.dictionaryCode = this@toProjection.dictionaryCode
            it.valueCode = this@toProjection.valueCode
            it.displayName = this@toProjection.displayName
            it.displayOrder = this@toProjection.displayOrder
            it.active = this@toProjection.active
            it.state = this@toProjection.state
            it.syncedAt = this@toProjection.syncedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun AgentDictionaryValueCatalogReadModelProjection.toEntity(): AgentDictionaryValueCatalogReadModelEntity =
        AgentDictionaryValueCatalogReadModelEntity().also {
            it.dictionaryValueId = this@toEntity.dictionaryValueId
            it.dictionaryId = this@toEntity.dictionaryId
            it.dictionaryCode = this@toEntity.dictionaryCode
            it.valueCode = this@toEntity.valueCode
            it.displayName = this@toEntity.displayName
            it.displayOrder = this@toEntity.displayOrder
            it.active = this@toEntity.active
            it.state = this@toEntity.state
            it.syncedAt = this@toEntity.syncedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
