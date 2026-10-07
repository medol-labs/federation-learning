package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentdictionaryvaluetranslationcatalogreadmodel

import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.runtimeagentoperations.agentdictionaryvaluetranslationcatalog.AgentDictionaryValueTranslationCatalogReadModel
import tech.medo.runtimeagentoperations.agentdictionaryvaluetranslationcatalog.AgentDictionaryValueTranslationCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.agentdictionaryvaluetranslationcatalog.AgentDictionaryValueTranslationCatalogReadModelProjection
import tech.medo.runtimeagentoperations.agentdictionaryvaluetranslationcatalog.AgentDictionaryValueTranslationCatalogReadModelRepository
import tech.medo.runtimeagentoperations.agentdictionaryvaluetranslationcatalog.toReadModel

@Repository
class JpaAgentDictionaryValueTranslationCatalogReadModelRepository(
    private val jpaRepository: SpringDataAgentDictionaryValueTranslationCatalogReadModelRepository,
    private val queryService: AgentDictionaryValueTranslationCatalogReadModelQueryService
) : AgentDictionaryValueTranslationCatalogReadModelRepository {
    override fun findAllByFilter(dictionaryCode: String?, valueCode: String?, locale: String?, pageable: Pageable): Page<AgentDictionaryValueTranslationCatalogReadModel> =
        jpaRepository.findAll(filters(dictionaryCode, valueCode, locale), pageable).map { it.toProjection().toReadModel() }

    override fun findAllByCriteria(criteria: AgentDictionaryValueTranslationCatalogReadModelCriteria?, pageable: Pageable): Page<AgentDictionaryValueTranslationCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): AgentDictionaryValueTranslationCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): AgentDictionaryValueTranslationCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: AgentDictionaryValueTranslationCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun filters(dictionaryCode: String?, valueCode: String?, locale: String?): Specification<AgentDictionaryValueTranslationCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            dictionaryCode?.let { predicates.add(criteriaBuilder.equal(root.get<String>("dictionaryCode"), it)) }
            valueCode?.let { predicates.add(criteriaBuilder.equal(root.get<String>("valueCode"), it)) }
            locale?.let { predicates.add(criteriaBuilder.equal(root.get<String>("locale"), it)) }
            criteriaBuilder.and(*predicates.toTypedArray())
        }


    private fun AgentDictionaryValueTranslationCatalogReadModelEntity.toProjection(): AgentDictionaryValueTranslationCatalogReadModelProjection =
        AgentDictionaryValueTranslationCatalogReadModelProjection().also {
            it.dictionaryValueTranslationId = this@toProjection.dictionaryValueTranslationId
            it.dictionaryValueId = this@toProjection.dictionaryValueId
            it.dictionaryCode = this@toProjection.dictionaryCode
            it.valueCode = this@toProjection.valueCode
            it.locale = this@toProjection.locale
            it.displayName = this@toProjection.displayName
            it.description = this@toProjection.description
            it.syncedAt = this@toProjection.syncedAt
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun AgentDictionaryValueTranslationCatalogReadModelProjection.toEntity(): AgentDictionaryValueTranslationCatalogReadModelEntity =
        AgentDictionaryValueTranslationCatalogReadModelEntity().also {
            it.dictionaryValueTranslationId = this@toEntity.dictionaryValueTranslationId
            it.dictionaryValueId = this@toEntity.dictionaryValueId
            it.dictionaryCode = this@toEntity.dictionaryCode
            it.valueCode = this@toEntity.valueCode
            it.locale = this@toEntity.locale
            it.displayName = this@toEntity.displayName
            it.description = this@toEntity.description
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
