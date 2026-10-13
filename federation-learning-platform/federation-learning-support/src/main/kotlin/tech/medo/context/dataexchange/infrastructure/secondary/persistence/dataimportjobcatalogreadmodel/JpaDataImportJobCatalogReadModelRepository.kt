package tech.medo.dataexchange.infrastructure.secondary.persistence.dataimportjobcatalogreadmodel

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
import tech.medo.dataexchange.dataimportjob.DataImportJobCatalogReadModel
import tech.medo.dataexchange.dataimportjob.DataImportJobCatalogReadModelCriteria
import tech.medo.dataexchange.dataimportjob.DataImportJobCatalogReadModelProjection
import tech.medo.dataexchange.dataimportjob.DataImportJobCatalogReadModelRepository
import tech.medo.dataexchange.dataimportjob.toReadModel

@Repository
class JpaDataImportJobCatalogReadModelRepository(
    private val jpaRepository: SpringDataDataImportJobCatalogReadModelRepository,
    private val accessPolicyFilter: ReadModelAccessPolicyFilter,
    private val queryService: DataImportJobCatalogReadModelQueryService
) : DataImportJobCatalogReadModelRepository {
    override fun findAllByFilter(commandName: String?, resourceName: String?, status: String?, pageable: Pageable): Page<DataImportJobCatalogReadModel> =
        jpaRepository.findAll(filters(commandName, resourceName, status).and(accessSpecification()), pageable).map { it.toProjection().toReadModel() }

    override fun findAllByCriteria(criteria: DataImportJobCatalogReadModelCriteria?, pageable: Pageable): Page<DataImportJobCatalogReadModel> =
        jpaRepository.findAll(queryService.createSpecification(criteria).and(accessSpecification()), pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): DataImportJobCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findByIdWithAccessPolicy(id: UUID): DataImportJobCatalogReadModel? =
        jpaRepository.findOne(idSpecification(id).and(accessSpecification())).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): DataImportJobCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: DataImportJobCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun filters(commandName: String?, resourceName: String?, status: String?): Specification<DataImportJobCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            commandName?.let { predicates.add(criteriaBuilder.equal(root.get<String>("commandName"), it)) }
            resourceName?.let { predicates.add(criteriaBuilder.equal(root.get<String>("resourceName"), it)) }
            status?.let { predicates.add(criteriaBuilder.equal(root.get<String>("status"), it)) }
            criteriaBuilder.and(*predicates.toTypedArray())
        }


    private fun idSpecification(id: UUID): Specification<DataImportJobCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.get<UUID>("dataImportJobId"), id)
        }

    private fun accessSpecification(): Specification<DataImportJobCatalogReadModelEntity> =
        accessPolicyFilter.specification(
            contextName = "DataExchange",
            readModelName = "DataImportJobCatalog",
            policy = ReadModelAccessPolicy(
            name = "DataExchangePublicAccess",
            fields = emptyList<String>()
        )
        )

    private fun DataImportJobCatalogReadModelEntity.toProjection(): DataImportJobCatalogReadModelProjection =
        DataImportJobCatalogReadModelProjection().also {
            it.dataImportJobId = this@toProjection.dataImportJobId
            it.commandName = this@toProjection.commandName
            it.resourceName = this@toProjection.resourceName
            it.originalFileName = this@toProjection.originalFileName
            it.filePath = this@toProjection.filePath
            it.requestedLocale = this@toProjection.requestedLocale
            it.requestedAt = this@toProjection.requestedAt
            it.requestHash = this@toProjection.requestHash
            it.status = this@toProjection.status
            it.totalRows = this@toProjection.totalRows
            it.successRows = this@toProjection.successRows
            it.failedRows = this@toProjection.failedRows
            it.failureIndexPath = this@toProjection.failureIndexPath
            it.errorMessage = this@toProjection.errorMessage
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun DataImportJobCatalogReadModelProjection.toEntity(): DataImportJobCatalogReadModelEntity =
        DataImportJobCatalogReadModelEntity().also {
            it.dataImportJobId = this@toEntity.dataImportJobId
            it.commandName = this@toEntity.commandName
            it.resourceName = this@toEntity.resourceName
            it.originalFileName = this@toEntity.originalFileName
            it.filePath = this@toEntity.filePath
            it.requestedLocale = this@toEntity.requestedLocale
            it.requestedAt = this@toEntity.requestedAt
            it.requestHash = this@toEntity.requestHash
            it.status = this@toEntity.status
            it.totalRows = this@toEntity.totalRows
            it.successRows = this@toEntity.successRows
            it.failedRows = this@toEntity.failedRows
            it.failureIndexPath = this@toEntity.failureIndexPath
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
