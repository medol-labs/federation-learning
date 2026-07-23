package tech.medo.dictionarymaintenance.infrastructure.secondary.persistence.dictionaryvaluecatalogreadmodel

import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.states.DictionaryValueStateEnum;

import tech.medo.dictionarymaintenance.dictionaryvaluecatalog.DictionaryValueCatalogReadModel
import tech.medo.dictionarymaintenance.dictionaryvaluecatalog.DictionaryValueCatalogReadModelProjection
import tech.medo.dictionarymaintenance.dictionaryvaluecatalog.DictionaryValueCatalogReadModelRepository
import tech.medo.dictionarymaintenance.dictionaryvaluecatalog.toReadModel

@Repository
class JpaDictionaryValueCatalogReadModelRepository(private val jpaRepository: SpringDataDictionaryValueCatalogReadModelRepository) : DictionaryValueCatalogReadModelRepository {
    override fun findAllByFilter(dictionaryCode: String?, active: Boolean?, state: DictionaryValueStateEnum?, pageable: Pageable): Page<DictionaryValueCatalogReadModel> =
        jpaRepository.findAll(filters(dictionaryCode, active, state), pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): DictionaryValueCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): DictionaryValueCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: DictionaryValueCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun filters(dictionaryCode: String?, active: Boolean?, state: DictionaryValueStateEnum?): Specification<DictionaryValueCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            dictionaryCode?.let { predicates.add(criteriaBuilder.equal(root.get<String>("dictionaryCode"), it)) }
            active?.let { predicates.add(criteriaBuilder.equal(root.get<Boolean>("active"), it)) }
            state?.let { predicates.add(criteriaBuilder.equal(root.get<DictionaryValueStateEnum>("state"), it)) }
            criteriaBuilder.and(*predicates.toTypedArray())
        }


    private fun DictionaryValueCatalogReadModelEntity.toProjection(): DictionaryValueCatalogReadModelProjection =
        DictionaryValueCatalogReadModelProjection().also {
            it.dictionaryValueId = this@toProjection.dictionaryValueId
            it.dictionaryId = this@toProjection.dictionaryId
            it.dictionaryCode = this@toProjection.dictionaryCode
            it.valueCode = this@toProjection.valueCode
            it.displayName = this@toProjection.displayName
            it.displayOrder = this@toProjection.displayOrder
            it.description = this@toProjection.description
            it.active = this@toProjection.active
            it.state = this@toProjection.state
            it.addedAt = this@toProjection.addedAt
            it.updatedAt = this@toProjection.updatedAt
            it.disabledAt = this@toProjection.disabledAt
            it.disabledReason = this@toProjection.disabledReason
            it.enabledAt = this@toProjection.enabledAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun DictionaryValueCatalogReadModelProjection.toEntity(): DictionaryValueCatalogReadModelEntity =
        DictionaryValueCatalogReadModelEntity().also {
            it.dictionaryValueId = this@toEntity.dictionaryValueId
            it.dictionaryId = this@toEntity.dictionaryId
            it.dictionaryCode = this@toEntity.dictionaryCode
            it.valueCode = this@toEntity.valueCode
            it.displayName = this@toEntity.displayName
            it.displayOrder = this@toEntity.displayOrder
            it.description = this@toEntity.description
            it.active = this@toEntity.active
            it.state = this@toEntity.state
            it.addedAt = this@toEntity.addedAt
            it.updatedAt = this@toEntity.updatedAt
            it.disabledAt = this@toEntity.disabledAt
            it.disabledReason = this@toEntity.disabledReason
            it.enabledAt = this@toEntity.enabledAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}
