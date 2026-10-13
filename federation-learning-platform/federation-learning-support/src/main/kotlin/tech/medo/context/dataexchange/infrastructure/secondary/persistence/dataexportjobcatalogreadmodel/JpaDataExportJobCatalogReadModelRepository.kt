package tech.medo.dataexchange.infrastructure.secondary.persistence.dataexportjobcatalogreadmodel

import jakarta.persistence.criteria.Predicate
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
import tech.medo.dataexchange.dataexportjob.DataExportJobCatalogReadModel
import tech.medo.dataexchange.dataexportjob.DataExportJobCatalogReadModelCriteria
import tech.medo.dataexchange.dataexportjob.DataExportJobCatalogReadModelProjection
import tech.medo.dataexchange.dataexportjob.DataExportJobCatalogReadModelRepository
import tech.medo.dataexchange.dataexportjob.toReadModel

@Repository
class JpaDataExportJobCatalogReadModelRepository(
    private val jpaRepository: SpringDataDataExportJobCatalogReadModelRepository,
    private val accessPolicyFilter: ReadModelAccessPolicyFilter,
    private val queryService: DataExportJobCatalogReadModelQueryService
) : DataExportJobCatalogReadModelRepository {
    override fun findAllByFilter(status: String?, pageable: Pageable): Page<DataExportJobCatalogReadModel> =
        jpaRepository.findAll(filters(status).and(accessSpecification()), pageable).map { it.toProjection().toReadModel() }

    override fun findAllByCriteria(criteria: DataExportJobCatalogReadModelCriteria?, pageable: Pageable): Page<DataExportJobCatalogReadModel> =
        jpaRepository.findAll(queryService.createSpecification(criteria).and(accessSpecification()), pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): DataExportJobCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findByIdWithAccessPolicy(id: UUID): DataExportJobCatalogReadModel? =
        jpaRepository.findOne(idSpecification(id).and(accessSpecification())).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): DataExportJobCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: DataExportJobCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun filters(status: String?): Specification<DataExportJobCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            status?.let { predicates.add(criteriaBuilder.equal(root.get<String>("status"), it)) }
            criteriaBuilder.and(*predicates.toTypedArray())
        }


    private fun idSpecification(id: UUID): Specification<DataExportJobCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.get<UUID>("dataExportJobId"), id)
        }

    private fun accessSpecification(): Specification<DataExportJobCatalogReadModelEntity> =
        accessPolicyFilter.specification(
            contextName = "DataExchange",
            readModelName = "DataExportJobCatalog",
            policy = ReadModelAccessPolicy(
            name = "DataExchangePublicAccess",
            fields = emptyList<String>()
        )
        )

    private fun DataExportJobCatalogReadModelEntity.toProjection(): DataExportJobCatalogReadModelProjection =
        DataExportJobCatalogReadModelProjection().also {
            it.dataExportJobId = this@toProjection.dataExportJobId
            it.resourceName = this@toProjection.resourceName
            it.criteriaJson = this@toProjection.criteriaJson
            it.sortJson = this@toProjection.sortJson
            it.columnsJson = this@toProjection.columnsJson
            it.requestedLocale = this@toProjection.requestedLocale
            it.requestedAt = this@toProjection.requestedAt
            it.snapshotUpperBound = this@toProjection.snapshotUpperBound
            it.requestHash = this@toProjection.requestHash
            it.status = this@toProjection.status
            it.fileName = this@toProjection.fileName
            it.filePath = this@toProjection.filePath
            it.downloadUri = this@toProjection.downloadUri
            it.rowCount = this@toProjection.rowCount
            it.errorMessage = this@toProjection.errorMessage
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun DataExportJobCatalogReadModelProjection.toEntity(): DataExportJobCatalogReadModelEntity =
        DataExportJobCatalogReadModelEntity().also {
            it.dataExportJobId = this@toEntity.dataExportJobId
            it.resourceName = this@toEntity.resourceName
            it.criteriaJson = this@toEntity.criteriaJson
            it.sortJson = this@toEntity.sortJson
            it.columnsJson = this@toEntity.columnsJson
            it.requestedLocale = this@toEntity.requestedLocale
            it.requestedAt = this@toEntity.requestedAt
            it.snapshotUpperBound = this@toEntity.snapshotUpperBound
            it.requestHash = this@toEntity.requestHash
            it.status = this@toEntity.status
            it.fileName = this@toEntity.fileName
            it.filePath = this@toEntity.filePath
            it.downloadUri = this@toEntity.downloadUri
            it.rowCount = this@toEntity.rowCount
            it.errorMessage = this@toEntity.errorMessage
            it.projectionUpdatedAt = this@toEntity.projectionUpdatedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
