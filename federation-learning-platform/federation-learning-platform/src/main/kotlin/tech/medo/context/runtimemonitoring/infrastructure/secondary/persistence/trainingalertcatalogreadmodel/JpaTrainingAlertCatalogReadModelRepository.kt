package tech.medo.runtimemonitoring.infrastructure.secondary.persistence.trainingalertcatalogreadmodel

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import tech.medo.runtimemonitoring.domain.states.TrainingAlertStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.medo.shared.application.access.ReadModelAccessPolicy
import tech.medo.shared.application.access.ReadModelAccessPolicyFilter
import tech.medo.runtimemonitoring.trainingalertcatalog.TrainingAlertCatalogReadModel
import tech.medo.runtimemonitoring.trainingalertcatalog.TrainingAlertCatalogReadModelCriteria
import tech.medo.runtimemonitoring.trainingalertcatalog.TrainingAlertCatalogReadModelProjection
import tech.medo.runtimemonitoring.trainingalertcatalog.TrainingAlertCatalogReadModelRepository
import tech.medo.runtimemonitoring.trainingalertcatalog.toReadModel

@Repository
class JpaTrainingAlertCatalogReadModelRepository(
    private val jpaRepository: SpringDataTrainingAlertCatalogReadModelRepository,
    private val accessPolicyFilter: ReadModelAccessPolicyFilter,
    private val queryService: TrainingAlertCatalogReadModelQueryService
) : TrainingAlertCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<TrainingAlertCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: TrainingAlertCatalogReadModelCriteria?, pageable: Pageable): Page<TrainingAlertCatalogReadModel> =
        jpaRepository.findAll(queryService.createSpecification(criteria).and(accessSpecification()), pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): TrainingAlertCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findByIdWithAccessPolicy(id: UUID): TrainingAlertCatalogReadModel? =
        jpaRepository.findOne(idSpecification(id).and(accessSpecification())).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): TrainingAlertCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: TrainingAlertCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun idSpecification(id: UUID): Specification<TrainingAlertCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.get<UUID>("alertId"), id)
        }

    private fun accessSpecification(): Specification<TrainingAlertCatalogReadModelEntity> =
        accessPolicyFilter.specification(
            contextName = "RuntimeMonitoring",
            readModelName = "TrainingAlertCatalog",
            policy = ReadModelAccessPolicy(
            name = null,
            fields = emptyList<String>()
        )
        )

    private fun TrainingAlertCatalogReadModelEntity.toProjection(): TrainingAlertCatalogReadModelProjection =
        TrainingAlertCatalogReadModelProjection().also {
            it.alertId = this@toProjection.alertId
            it.nodeId = this@toProjection.nodeId
            it.trainingJobId = this@toProjection.trainingJobId
            it.runtimeNodeName = this@toProjection.runtimeNodeName
            it.trainingJobObjective = this@toProjection.trainingJobObjective
            it.severity = this@toProjection.severity
            it.message = this@toProjection.message
            it.state = this@toProjection.state
            it.acknowledgedAt = this@toProjection.acknowledgedAt
            it.resolvedAt = this@toProjection.resolvedAt
            it.resolutionSummary = this@toProjection.resolutionSummary
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun TrainingAlertCatalogReadModelProjection.toEntity(): TrainingAlertCatalogReadModelEntity =
        TrainingAlertCatalogReadModelEntity().also {
            it.alertId = this@toEntity.alertId
            it.nodeId = this@toEntity.nodeId
            it.trainingJobId = this@toEntity.trainingJobId
            it.runtimeNodeName = this@toEntity.runtimeNodeName
            it.trainingJobObjective = this@toEntity.trainingJobObjective
            it.severity = this@toEntity.severity
            it.message = this@toEntity.message
            it.state = this@toEntity.state
            it.acknowledgedAt = this@toEntity.acknowledgedAt
            it.resolvedAt = this@toEntity.resolvedAt
            it.resolutionSummary = this@toEntity.resolutionSummary
            it.projectionUpdatedAt = this@toEntity.projectionUpdatedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
