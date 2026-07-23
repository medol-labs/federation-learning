package tech.medo.runtimeprovisioning.infrastructure.secondary.persistence.runtimeinfrastructurepackagecatalogreadmodel

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.UUID;

import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModel
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModelProjection
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.RuntimeInfrastructurePackageCatalogReadModelRepository
import tech.medo.runtimeprovisioning.runtimeinfrastructurepackagecatalog.toReadModel

@Repository
class JpaRuntimeInfrastructurePackageCatalogReadModelRepository(private val jpaRepository: SpringDataRuntimeInfrastructurePackageCatalogReadModelRepository) : RuntimeInfrastructurePackageCatalogReadModelRepository {
    override fun findAll(pageable: Pageable): Page<RuntimeInfrastructurePackageCatalogReadModel> =
        jpaRepository.findAll(pageable).map { it.toProjection().toReadModel() }

    override fun findById(id: UUID): RuntimeInfrastructurePackageCatalogReadModel? =
        jpaRepository.findById(id).map { it.toProjection().toReadModel() }.orElse(null)

    override fun findProjectionById(id: UUID): RuntimeInfrastructurePackageCatalogReadModelProjection? =
        jpaRepository.findById(id).map { it.toProjection() }.orElse(null)

    override fun save(projection: RuntimeInfrastructurePackageCatalogReadModelProjection) {
        jpaRepository.save(projection.toEntity())
    }

    private fun RuntimeInfrastructurePackageCatalogReadModelEntity.toProjection(): RuntimeInfrastructurePackageCatalogReadModelProjection =
        RuntimeInfrastructurePackageCatalogReadModelProjection().also {
            it.runtimeInfrastructurePackageId = this@toProjection.runtimeInfrastructurePackageId
            it.packageName = this@toProjection.packageName
            it.packageVersion = this@toProjection.packageVersion
            it.runtimeEnvironmentType = this@toProjection.runtimeEnvironmentType
            it.runtimeDeploymentTargetType = this@toProjection.runtimeDeploymentTargetType
            it.installProfile = this@toProjection.installProfile
            it.architecture = this@toProjection.architecture
            it.installGuide = this@toProjection.installGuide
            it.state = this@toProjection.state
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
            it.runtimeDeploymentTargetType = this@toEntity.runtimeDeploymentTargetType
            it.installProfile = this@toEntity.installProfile
            it.architecture = this@toEntity.architecture
            it.installGuide = this@toEntity.installGuide
            it.state = this@toEntity.state
            it.userId = this@toEntity.userId
            it.sessionId = this@toEntity.sessionId
            it.correlationId = this@toEntity.correlationId
            it.causationId = this@toEntity.causationId
            it.traceId = this@toEntity.traceId
            it.tenantId = this@toEntity.tenantId
        }
}
