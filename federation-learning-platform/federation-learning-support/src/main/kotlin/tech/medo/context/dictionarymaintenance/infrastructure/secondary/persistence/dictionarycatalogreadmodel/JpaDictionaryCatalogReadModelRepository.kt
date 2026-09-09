package tech.medo.dictionarymaintenance.infrastructure.secondary.persistence.dictionarycatalogreadmodel

import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.states.DictionaryStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.dictionarymaintenance.dictionarycatalog.DictionaryCatalogReadModel
import tech.medo.dictionarymaintenance.dictionarycatalog.DictionaryCatalogReadModelCriteria
import tech.medo.dictionarymaintenance.dictionarycatalog.DictionaryCatalogReadModelProjection
import tech.medo.dictionarymaintenance.dictionarycatalog.DictionaryCatalogReadModelRepository
import tech.medo.dictionarymaintenance.dictionarycatalog.toReadModel

@Repository
class JpaDictionaryCatalogReadModelRepository(
    private val jpaRepository: SpringDataDictionaryCatalogReadModelRepository,
    private val queryService: DictionaryCatalogReadModelQueryService
) : DictionaryCatalogReadModelRepository {
    override fun findAllByFilter(dictionaryCode: String?, pageable: Pageable): Page<DictionaryCatalogReadModel> =
        jpaRepository.findAll(filters(dictionaryCode), pageable).map { it.toProjection().toReadModel() }

    override fun findAllByCriteria(criteria: DictionaryCatalogReadModelCriteria?, pageable: Pageable): Page<DictionaryCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): DictionaryCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): DictionaryCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: DictionaryCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun filters(dictionaryCode: String?): Specification<DictionaryCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            dictionaryCode?.let { predicates.add(criteriaBuilder.equal(root.get<String>("dictionaryCode"), it)) }
            criteriaBuilder.and(*predicates.toTypedArray())
        }


    private fun DictionaryCatalogReadModelEntity.toProjection(): DictionaryCatalogReadModelProjection =
        DictionaryCatalogReadModelProjection().also {
            it.dictionaryId = this@toProjection.dictionaryId
            it.dictionaryCode = this@toProjection.dictionaryCode
            it.dictionaryName = this@toProjection.dictionaryName
            it.description = this@toProjection.description
            it.state = this@toProjection.state
            it.registeredAt = this@toProjection.registeredAt
            it.updatedAt = this@toProjection.updatedAt
            it.archivedAt = this@toProjection.archivedAt
            it.archiveReason = this@toProjection.archiveReason
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun DictionaryCatalogReadModelProjection.toEntity(): DictionaryCatalogReadModelEntity =
        DictionaryCatalogReadModelEntity().also {
            it.dictionaryId = this@toEntity.dictionaryId
            it.dictionaryCode = this@toEntity.dictionaryCode
            it.dictionaryName = this@toEntity.dictionaryName
            it.description = this@toEntity.description
            it.state = this@toEntity.state
            it.registeredAt = this@toEntity.registeredAt
            it.updatedAt = this@toEntity.updatedAt
            it.archivedAt = this@toEntity.archivedAt
            it.archiveReason = this@toEntity.archiveReason
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
