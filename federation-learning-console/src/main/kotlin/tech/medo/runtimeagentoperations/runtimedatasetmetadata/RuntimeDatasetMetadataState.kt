package tech.medo.runtimeagentoperations.runtimedatasetmetadata

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimeagentoperations.events.AgentDatasetMetadataReportedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetProfilingFailedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetReprofiledEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetReprofilingFailedEvent
import tech.medo.runtimeagentoperations.domain.states.RuntimeDatasetMetadataStateEnum

import java.util.UUID;
import java.math.BigDecimal;


@EventSourced(idType = UUID::class, tagKey = RuntimeDatasetMetadataTags.METADATA_REPORT_ID)
class RuntimeDatasetMetadataState @EntityCreator constructor() {

    var currentState: RuntimeDatasetMetadataStateEnum? = null
    private var metadataReportId: UUID? = null
    private var runtimeDatasetBindingId: UUID? = null
    private var datasetId: UUID? = null
    private var organizationId: UUID? = null
    private var runtimeId: UUID? = null
    private var featureSchemaId: UUID? = null
    private var sampleCount: Int? = null
    private var featureCount: Int? = null
    private var schemaCompatible: Boolean? = null
    private var labelCompatible: Boolean? = null
    private var missingValueRate: BigDecimal? = null
    private var duplicateRate: BigDecimal? = null
    private var qualityScore: BigDecimal? = null
    private var nonIidScore: BigDecimal? = null
    private var classBalanceScore: BigDecimal? = null
    private var failureReason: String? = null

    @EventSourcingHandler
    fun evolve(event: AgentDatasetMetadataReportedEvent): RuntimeDatasetMetadataState = apply {
        currentState = RuntimeDatasetMetadataStateEnum.REPORTED
        metadataReportId = event.metadataReportId
        runtimeDatasetBindingId = event.runtimeDatasetBindingId
        datasetId = event.datasetId
        organizationId = event.organizationId
        runtimeId = event.runtimeId
        featureSchemaId = event.featureSchemaId
        sampleCount = event.sampleCount
        featureCount = event.featureCount
        schemaCompatible = event.schemaCompatible
        labelCompatible = event.labelCompatible
        missingValueRate = event.missingValueRate
        duplicateRate = event.duplicateRate
        qualityScore = event.qualityScore
        nonIidScore = event.nonIidScore
        classBalanceScore = event.classBalanceScore
    }

    @EventSourcingHandler
    fun evolve(event: AgentDatasetProfilingFailedEvent): RuntimeDatasetMetadataState = apply {
        metadataReportId = event.metadataReportId
        runtimeDatasetBindingId = event.runtimeDatasetBindingId
        datasetId = event.datasetId
        runtimeId = event.runtimeId
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: AgentDatasetReprofiledEvent): RuntimeDatasetMetadataState = apply {
        currentState = RuntimeDatasetMetadataStateEnum.REPORTED
        metadataReportId = event.metadataReportId
        runtimeDatasetBindingId = event.runtimeDatasetBindingId
        datasetId = event.datasetId
        organizationId = event.organizationId
        runtimeId = event.runtimeId
        featureSchemaId = event.featureSchemaId
        sampleCount = event.sampleCount
        featureCount = event.featureCount
        schemaCompatible = event.schemaCompatible
        labelCompatible = event.labelCompatible
        missingValueRate = event.missingValueRate
        duplicateRate = event.duplicateRate
        qualityScore = event.qualityScore
        nonIidScore = event.nonIidScore
        classBalanceScore = event.classBalanceScore
    }

    @EventSourcingHandler
    fun evolve(event: AgentDatasetReprofilingFailedEvent): RuntimeDatasetMetadataState = apply {
        metadataReportId = event.metadataReportId
        runtimeDatasetBindingId = event.runtimeDatasetBindingId
        datasetId = event.datasetId
        runtimeId = event.runtimeId
        failureReason = event.failureReason
    }
}
