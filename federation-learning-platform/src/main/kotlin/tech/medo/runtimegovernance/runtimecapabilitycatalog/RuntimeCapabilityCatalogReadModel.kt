package tech.medo.runtimegovernance.runtimecapabilitycatalog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


class RuntimeCapabilityCatalogReadModelQuery

class RuntimeCapabilityCatalogReadModelProjection : MetadataProjection {
    var runtimeId: UUID? = null
    var capabilityTypes: List<String> = emptyList()
    var capabilityStatus: String? = null
    var detectedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}

fun RuntimeCapabilityCatalogReadModelProjection.toReadModel(): RuntimeCapabilityCatalogReadModel =
    RuntimeCapabilityCatalogReadModel(
    runtimeId = runtimeId,
    capabilityTypes = capabilityTypes,
    capabilityStatus = capabilityStatus,
    detectedAt = detectedAt,
    userId = userId,
    sessionId = sessionId,
    correlationId = correlationId,
    causationId = causationId,
    traceId = traceId,
    tenantId = tenantId
    )

interface RuntimeCapabilityCatalogReadModelRepository {
    fun findAll(pageable: Pageable): Page<RuntimeCapabilityCatalogReadModel>
    fun findById(id: UUID): RuntimeCapabilityCatalogReadModel?
    fun findProjectionById(id: UUID): RuntimeCapabilityCatalogReadModelProjection?
    fun save(projection: RuntimeCapabilityCatalogReadModelProjection)
}

data class RuntimeCapabilityCatalogReadModel(
    val runtimeId: UUID?,
    val capabilityTypes: List<String>,
    val capabilityStatus: String?,
    val detectedAt: LocalDateTime?,
    val userId: String?,
    val sessionId: String?,
    val correlationId: String?,
    val causationId: String?,
    val traceId: String?,
    val tenantId: String?
)
