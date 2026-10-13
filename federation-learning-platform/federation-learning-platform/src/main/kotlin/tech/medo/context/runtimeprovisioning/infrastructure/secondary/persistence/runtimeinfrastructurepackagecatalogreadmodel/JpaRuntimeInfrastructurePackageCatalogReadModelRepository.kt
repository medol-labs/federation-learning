package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinfrastructurepackagecatalogreadmodel

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import java.util.UUID;
import tech.medo.runtimeprovisioning.domain.states.RuntimeInfrastructurePackageStateEnum;

import tech.medo.shared.application.access.ReadModelAccessPolicy
import tech.medo.shared.application.access.ReadModelAccessPolicyFilter
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModel
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModelCriteria
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModelProjection
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModelRepository
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.toReadModel

@Repository
class JpaRuntimeInfrastructurePackageCatalogReadModelRepository(
    private val jpaRepository: SpringDataRuntimeInfrastructurePackageCatalogReadModelRepository,
    private val accessPolicyFilter: ReadModelAccessPolicyFilter,
    private val queryService: RuntimeInfrastructurePackageCatalogReadModelQueryService
) : RuntimeInfrastructurePackageCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RuntimeInfrastructurePackageCatalogReadModel> =
        findAllByCriteria(null, pageable)

    override fun findAllByCriteria(criteria: RuntimeInfrastructurePackageCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeInfrastructurePackageCatalogReadModel> =
        jpaRepository.findAll(queryService.createSpecification(criteria).and(accessSpecification()), pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): RuntimeInfrastructurePackageCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findByIdWithAccessPolicy(id: UUID): RuntimeInfrastructurePackageCatalogReadModel? =
        jpaRepository.findOne(idSpecification(id).and(accessSpecification())).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RuntimeInfrastructurePackageCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RuntimeInfrastructurePackageCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun idSpecification(id: UUID): Specification<RuntimeInfrastructurePackageCatalogReadModelEntity> =
        Specification { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.get<UUID>("runtimeInfrastructurePackageId"), id)
        }

    private fun accessSpecification(): Specification<RuntimeInfrastructurePackageCatalogReadModelEntity> =
        accessPolicyFilter.specification(
            contextName = "RuntimeProvisioning",
            readModelName = "RuntimeInfrastructurePackageCatalog",
            policy = ReadModelAccessPolicy(
            name = null,
            fields = emptyList<String>()
        )
        )

    private fun RuntimeInfrastructurePackageCatalogReadModelEntity.toProjection(): RuntimeInfrastructurePackageCatalogReadModelProjection =
        RuntimeInfrastructurePackageCatalogReadModelProjection().also {
            it.runtimeInfrastructurePackageId = this@toProjection.runtimeInfrastructurePackageId
            it.packageName = this@toProjection.packageName
            it.packageVersion = this@toProjection.packageVersion
            it.runtimeEnvironmentType = this@toProjection.runtimeEnvironmentType
            it.state = this@toProjection.state
            it.projectionUpdatedAt = this@toProjection.projectionUpdatedAt
            it.userId = this@toProjection.userId
            it.sessionId = this@toProjection.sessionId
            it.correlationId = this@toProjection.correlationId
            it.causationId = this@toProjection.causationId
            it.traceId = this@toProjection.traceId
            it.tenantId = this@toProjection.tenantId
        }

    private fun RuntimeInfrastructurePackageCatalogReadModelProjection.toEntity(): RuntimeInfrastructurePackageCatalogReadModelEntity =
        RuntimeInfrastructurePackageCatalogReadModelEntity().also {
            it.runtimeInfrastructurePackageId = this@toEntity.runtimeInfrastructurePackageId
            it.packageName = this@toEntity.packageName
            it.packageVersion = this@toEntity.packageVersion
            it.runtimeEnvironmentType = this@toEntity.runtimeEnvironmentType
            it.state = this@toEntity.state
            it.projectionUpdatedAt = this@toEntity.projectionUpdatedAt
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
        }
