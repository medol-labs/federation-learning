package tech.medo.fileupload.infrastructure.secondary.persistence.uploadedfilecatalogreadmodel

import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import tech.medo.fileupload.domain.states.UploadedFileStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.fileupload.uploadedfilecatalog.UploadedFileCatalogReadModel
import tech.medo.fileupload.uploadedfilecatalog.UploadedFileCatalogReadModelCriteria
import tech.medo.fileupload.uploadedfilecatalog.UploadedFileCatalogReadModelProjection
import tech.medo.fileupload.uploadedfilecatalog.UploadedFileCatalogReadModelRepository
import tech.medo.fileupload.uploadedfilecatalog.toReadModel

@Repository
class JpaUploadedFileCatalogReadModelRepository(
    private val jpaRepository: SpringDataUploadedFileCatalogReadModelRepository,
    private val queryService: UploadedFileCatalogReadModelQueryService
) : UploadedFileCatalogReadModelRepository {
    override fun findAllByFilter(purpose: String?, pageable: Pageable): Page<UploadedFileCatalogReadModel> =
        jpaRepository.findAll(filters(purpose), pageable).map { it.toProjection().toReadModel() }

    override fun findAllByCriteria(criteria: UploadedFileCatalogReadModelCriteria?, pageable: Pageable): Page<UploadedFileCatalogReadModel> =
        queryService.findByCriteria(criteria, pageable)

    override fun findById(id: UUID): UploadedFileCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): UploadedFileCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: UploadedFileCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun filters(purpose: String?): Specification<UploadedFileCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            purpose?.let { predicates.add(criteriaBuilder.equal(root.get<String>("purpose"), it)) }
            criteriaBuilder.and(*predicates.toTypedArray())
        }


    private fun UploadedFileCatalogReadModelEntity.toProjection(): UploadedFileCatalogReadModelProjection =
        UploadedFileCatalogReadModelProjection().also {
            it.fileId = this@toProjection.fileId
            it.originalFileName = this@toProjection.originalFileName
            it.contentType = this@toProjection.contentType
            it.sizeBytes = this@toProjection.sizeBytes
            it.purpose = this@toProjection.purpose
            it.fileLocation = this@toProjection.fileLocation
            it.checksum = this@toProjection.checksum
            it.state = this@toProjection.state
            it.uploadedAt = this@toProjection.uploadedAt
            it.referencedAt = this@toProjection.referencedAt
            it.referencedByContext = this@toProjection.referencedByContext
            it.referencedByCommand = this@toProjection.referencedByCommand
            it.referencedByCommandId = this@toProjection.referencedByCommandId
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

    private fun UploadedFileCatalogReadModelProjection.toEntity(): UploadedFileCatalogReadModelEntity =
        UploadedFileCatalogReadModelEntity().also {
            it.fileId = this@toEntity.fileId
            it.originalFileName = this@toEntity.originalFileName
            it.contentType = this@toEntity.contentType
            it.sizeBytes = this@toEntity.sizeBytes
            it.purpose = this@toEntity.purpose
            it.fileLocation = this@toEntity.fileLocation
            it.checksum = this@toEntity.checksum
            it.state = this@toEntity.state
            it.uploadedAt = this@toEntity.uploadedAt
            it.referencedAt = this@toEntity.referencedAt
            it.referencedByContext = this@toEntity.referencedByContext
            it.referencedByCommand = this@toEntity.referencedByCommand
            it.referencedByCommandId = this@toEntity.referencedByCommandId
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
