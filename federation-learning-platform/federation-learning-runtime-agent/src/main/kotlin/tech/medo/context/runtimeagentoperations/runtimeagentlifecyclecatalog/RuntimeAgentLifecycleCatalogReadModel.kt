package tech.medo.runtimeagentoperations.runtimeagentlifecyclecatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import tech.jhipster.service.filter.BooleanFilter
import tech.jhipster.service.filter.RangeFilter
import tech.jhipster.service.filter.StringFilter


class RuntimeAgentLifecycleCatalogReadModelQuery

class RuntimeAgentLifecycleCatalogReadModelCriteria {
    var runtimeAgentId: StringFilter? = null
    var runtimeInfrastructureId: StringFilter? = null
    var agentVersion: StringFilter? = null
    var lifecycleStatus: StringFilter? = null
    var bootstrapConfigurationLoaded: BooleanFilter? = null
    var bootstrapFailureReason: StringFilter? = null
    var runtimeAgentSelfCheckPassed: BooleanFilter? = null
    var configurationLoaded: BooleanFilter? = null
    var secretStoreAccessible: BooleanFilter? = null
    var runtimeEngineAdapterReady: BooleanFilter? = null
    var modelRepositoryClientReady: BooleanFilter? = null
    var localDatasetBindingStoreReady: BooleanFilter? = null
    var workingDirectoryWritable: BooleanFilter? = null
    var bootstrappedAt: RangeFilter<LocalDateTime>? = null
    var bootstrapFailedAt: RangeFilter<LocalDateTime>? = null
    var startedAt: RangeFilter<LocalDateTime>? = null
    var readyAt: RangeFilter<LocalDateTime>? = null
}


class RuntimeAgentLifecycleCatalogReadModelProjection : MetadataProjection {
    var runtimeAgentId: UUID? = null
    var runtimeInfrastructureId: UUID? = null
    var agentVersion: String? = null
    var lifecycleStatus: String? = null
    var bootstrapConfigurationLoaded: Boolean? = null
    var bootstrapFailureReason: String? = null
    var runtimeAgentSelfCheckPassed: Boolean? = null
    var configurationLoaded: Boolean? = null
    var secretStoreAccessible: Boolean? = null
    var runtimeEngineAdapterReady: Boolean? = null
    var modelRepositoryClientReady: Boolean? = null
    var localDatasetBindingStoreReady: Boolean? = null
    var workingDirectoryWritable: Boolean? = null
    var bootstrappedAt: LocalDateTime? = null
    var bootstrapFailedAt: LocalDateTime? = null
    var startedAt: LocalDateTime? = null
    var readyAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RuntimeAgentLifecycleCatalogReadModelProjection.toReadModel(): RuntimeAgentLifecycleCatalogReadModel =
    RuntimeAgentLifecycleCatalogReadModel(
    runtimeAgentId = runtimeAgentId,
    runtimeInfrastructureId = runtimeInfrastructureId,
    agentVersion = agentVersion,
    lifecycleStatus = lifecycleStatus,
    bootstrapConfigurationLoaded = bootstrapConfigurationLoaded,
    bootstrapFailureReason = bootstrapFailureReason,
    runtimeAgentSelfCheckPassed = runtimeAgentSelfCheckPassed,
    configurationLoaded = configurationLoaded,
    secretStoreAccessible = secretStoreAccessible,
    runtimeEngineAdapterReady = runtimeEngineAdapterReady,
    modelRepositoryClientReady = modelRepositoryClientReady,
    localDatasetBindingStoreReady = localDatasetBindingStoreReady,
    workingDirectoryWritable = workingDirectoryWritable,
    bootstrappedAt = bootstrappedAt,
    bootstrapFailedAt = bootstrapFailedAt,
    startedAt = startedAt,
    readyAt = readyAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface RuntimeAgentLifecycleCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<RuntimeAgentLifecycleCatalogReadModel>
    fun findAllByCriteria(criteria: RuntimeAgentLifecycleCatalogReadModelCriteria?, pageable: Pageable): Page<RuntimeAgentLifecycleCatalogReadModel>
    fun findById(id: UUID): RuntimeAgentLifecycleCatalogReadModel?
    fun findProjectionById(id: UUID): RuntimeAgentLifecycleCatalogReadModelProjection?
    fun save(projection: RuntimeAgentLifecycleCatalogReadModelProjection)
}

data class RuntimeAgentLifecycleCatalogReadModel(
    val runtimeAgentId: UUID?,
    val runtimeInfrastructureId: UUID?,
    val agentVersion: String?,
    val lifecycleStatus: String?,
    val bootstrapConfigurationLoaded: Boolean?,
    val bootstrapFailureReason: String?,
    val runtimeAgentSelfCheckPassed: Boolean?,
    val configurationLoaded: Boolean?,
    val secretStoreAccessible: Boolean?,
    val runtimeEngineAdapterReady: Boolean?,
    val modelRepositoryClientReady: Boolean?,
    val localDatasetBindingStoreReady: Boolean?,
    val workingDirectoryWritable: Boolean?,
    val bootstrappedAt: LocalDateTime?,
    val bootstrapFailedAt: LocalDateTime?,
    val startedAt: LocalDateTime?,
    val readyAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
