package tech.medo.fileupload.infrastructure.secondary.persistence.stagedfilecatalogreadmodel

import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import tech.medo.fileupload.domain.states.StagedFileStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.fileupload.stagedfilecatalog.StagedFileCatalogReadModel
import tech.medo.fileupload.stagedfilecatalog.StagedFileCatalogReadModelCriteria
import tech.medo.fileupload.stagedfilecatalog.StagedFileCatalogReadModelProjection
import tech.medo.fileupload.stagedfilecatalog.StagedFileCatalogReadModelRepository
import tech.medo.fileupload.stagedfilecatalog.toReadModel

@Repository
class JpaStagedFileCatalogReadModelRepository(
    private val jpaRepository: SpringDataStagedFileCatalogReadModelRepository,
    private val queryService: StagedFileCatalogReadModelQueryService
) : StagedFileCatalogReadModelRepository {
    override fun findAllByFilter(purpose: String?, pageable: Pageable): Page<StagedFileCatalogReadModel> =
        jpaRepository.findAll(filters(purpose), pageable).map { it.toProjection().toReadModel() }

    override fun findAllByCriteria(criteria: StagedFileCatalogReadModelCriteria?, pageable: Pageable): Page<StagedFileCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): StagedFileCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): StagedFileCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: StagedFileCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun filters(purpose: String?): Specification<StagedFileCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            purpose?.let { predicates.add(criteriaBuilder.equal(root.get<String>("purpose"), it)) }
            criteriaBuilder.and(*predicates.toTypedArray())
        }


    private fun StagedFileCatalogReadModelEntity.toProjection(): StagedFileCatalogReadModelProjection =
        StagedFileCatalogReadModelProjection().also {
            it.stagedFileId = this@toProjection.stagedFileId
            it.originalFileName = this@toProjection.originalFileName
            it.contentType = this@toProjection.contentType
            it.sizeBytes = this@toProjection.sizeBytes
            it.purpose = this@toProjection.purpose
            it.stagedFileLocation = this@toProjection.stagedFileLocation
            it.checksum = this@toProjection.checksum
            it.state = this@toProjection.state
            it.stagedAt = this@toProjection.stagedAt
            it.consumedAt = this@toProjection.consumedAt
            it.consumedByContext = this@toProjection.consumedByContext
            it.consumedByCommand = this@toProjection.consumedByCommand
            it.consumedByCommandId = this@toProjection.consumedByCommandId
            it.discardedAt = this@toProjection.discardedAt
            it.discardReason = this@toProjection.discardReason
            it.expiresAt = this@toProjection.expiresAt
            it.expiredAt = this@toProjection.expiredAt
            it.expirationReason = this@toProjection.expirationReason
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun StagedFileCatalogReadModelProjection.toEntity(): StagedFileCatalogReadModelEntity =
        StagedFileCatalogReadModelEntity().also {
            it.stagedFileId = this@toEntity.stagedFileId
            it.originalFileName = this@toEntity.originalFileName
            it.contentType = this@toEntity.contentType
            it.sizeBytes = this@toEntity.sizeBytes
            it.purpose = this@toEntity.purpose
            it.stagedFileLocation = this@toEntity.stagedFileLocation
            it.checksum = this@toEntity.checksum
            it.state = this@toEntity.state
            it.stagedAt = this@toEntity.stagedAt
            it.consumedAt = this@toEntity.consumedAt
            it.consumedByContext = this@toEntity.consumedByContext
            it.consumedByCommand = this@toEntity.consumedByCommand
            it.consumedByCommandId = this@toEntity.consumedByCommandId
            it.discardedAt = this@toEntity.discardedAt
            it.discardReason = this@toEntity.discardReason
            it.expiresAt = this@toEntity.expiresAt
            it.expiredAt = this@toEntity.expiredAt
            it.expirationReason = this@toEntity.expirationReason
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
