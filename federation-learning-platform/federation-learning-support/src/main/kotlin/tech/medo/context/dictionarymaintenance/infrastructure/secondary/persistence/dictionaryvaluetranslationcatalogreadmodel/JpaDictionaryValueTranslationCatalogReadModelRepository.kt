package tech.medo.dictionarymaintenance.infrastructure.secondary.persistence.dictionaryvaluetranslationcatalogreadmodel

import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.dictionarymaintenance.dictionaryvaluetranslationcatalog.DictionaryValueTranslationCatalogReadModel
import tech.medo.dictionarymaintenance.dictionaryvaluetranslationcatalog.DictionaryValueTranslationCatalogReadModelCriteria
import tech.medo.dictionarymaintenance.dictionaryvaluetranslationcatalog.DictionaryValueTranslationCatalogReadModelProjection
import tech.medo.dictionarymaintenance.dictionaryvaluetranslationcatalog.DictionaryValueTranslationCatalogReadModelRepository
import tech.medo.dictionarymaintenance.dictionaryvaluetranslationcatalog.toReadModel

@Repository
class JpaDictionaryValueTranslationCatalogReadModelRepository(
    private val jpaRepository: SpringDataDictionaryValueTranslationCatalogReadModelRepository,
    private val queryService: DictionaryValueTranslationCatalogReadModelQueryService
) : DictionaryValueTranslationCatalogReadModelRepository {
    override fun findAllByFilter(dictionaryCode: String?, valueCode: String?, locale: String?, pageable: Pageable): Page<DictionaryValueTranslationCatalogReadModel> =
        jpaRepository.findAll(filters(dictionaryCode, valueCode, locale), pageable).map { it.toProjection().toReadModel() }

    override fun findAllByCriteria(criteria: DictionaryValueTranslationCatalogReadModelCriteria?, pageable: Pageable): Page<DictionaryValueTranslationCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): DictionaryValueTranslationCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): DictionaryValueTranslationCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: DictionaryValueTranslationCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun filters(dictionaryCode: String?, valueCode: String?, locale: String?): Specification<DictionaryValueTranslationCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            dictionaryCode?.let { predicates.add(criteriaBuilder.equal(root.get<String>("dictionaryCode"), it)) }
            valueCode?.let { predicates.add(criteriaBuilder.equal(root.get<String>("valueCode"), it)) }
            locale?.let { predicates.add(criteriaBuilder.equal(root.get<String>("locale"), it)) }
            criteriaBuilder.and(*predicates.toTypedArray())
        }


    private fun DictionaryValueTranslationCatalogReadModelEntity.toProjection(): DictionaryValueTranslationCatalogReadModelProjection =
        DictionaryValueTranslationCatalogReadModelProjection().also {
            it.dictionaryValueTranslationId = this@toProjection.dictionaryValueTranslationId
            it.dictionaryValueId = this@toProjection.dictionaryValueId
            it.dictionaryCode = this@toProjection.dictionaryCode
            it.valueCode = this@toProjection.valueCode
            it.locale = this@toProjection.locale
            it.displayName = this@toProjection.displayName
            it.description = this@toProjection.description
            it.updatedAt = this@toProjection.updatedAt
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun DictionaryValueTranslationCatalogReadModelProjection.toEntity(): DictionaryValueTranslationCatalogReadModelEntity =
        DictionaryValueTranslationCatalogReadModelEntity().also {
            it.dictionaryValueTranslationId = this@toEntity.dictionaryValueTranslationId
            it.dictionaryValueId = this@toEntity.dictionaryValueId
            it.dictionaryCode = this@toEntity.dictionaryCode
            it.valueCode = this@toEntity.valueCode
            it.locale = this@toEntity.locale
            it.displayName = this@toEntity.displayName
            it.description = this@toEntity.description
            it.updatedAt = this@toEntity.updatedAt
            it.projectionUpdatedAt = this@toEntity.projectionUpdatedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
