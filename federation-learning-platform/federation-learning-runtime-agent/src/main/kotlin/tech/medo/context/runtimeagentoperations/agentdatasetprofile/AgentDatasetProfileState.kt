package tech.medo.runtimeagentoperations.agentdatasetprofile

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
import tech.medo.runtimeagentoperations.domain.states.AgentDatasetProfileStateEnum

import java.util.UUID;
import java.math.BigDecimal;


@EventSourced(idType = UUID::class, tagKey = AgentDatasetProfileTags.RUNTIME_DATASET_BINDING_ID)
class AgentDatasetProfileState @EntityCreator constructor() {

    var currentState: AgentDatasetProfileStateEnum? = null
    var metadataReportId: UUID? = null
    var runtimeDatasetBindingId: UUID? = null
    var datasetId: UUID? = null
    var organizationId: UUID? = null
    var organizationName: String? = null
    var runtimeId: UUID? = null
    var runtimeName: String? = null
    var featureSchemaId: UUID? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var datasetName: String? = null
    var sampleCount: Int? = null
    var featureCount: Int? = null
    var schemaCompatible: Boolean? = null
    var labelCompatible: Boolean? = null
    var missingValueRate: BigDecimal? = null
    var duplicateRate: BigDecimal? = null
    var qualityScore: BigDecimal? = null
    var nonIidScore: BigDecimal? = null
    var classBalanceScore: BigDecimal? = null
    var failureReason: String? = null

    @EventSourcingHandler
    fun evolve(event: AgentDatasetMetadataReportedEvent): AgentDatasetProfileState = apply {
        currentState = AgentDatasetProfileStateEnum.REPORTED
        metadataReportId = event.metadataReportId
        runtimeDatasetBindingId = event.runtimeDatasetBindingId
        datasetId = event.datasetId
        organizationId = event.organizationId
        organizationName = event.organizationName
        runtimeId = event.runtimeId
        runtimeName = event.runtimeName
        featureSchemaId = event.featureSchemaId
        featureDomain = event.featureDomain
        featureSchemaVersion = event.featureSchemaVersion
        datasetName = event.datasetName
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
    fun evolve(event: AgentDatasetProfilingFailedEvent): AgentDatasetProfileState = apply {
        metadataReportId = event.metadataReportId
        runtimeDatasetBindingId = event.runtimeDatasetBindingId
        datasetId = event.datasetId
        organizationId = event.organizationId
        organizationName = event.organizationName
        featureSchemaId = event.featureSchemaId
        featureDomain = event.featureDomain
        featureSchemaVersion = event.featureSchemaVersion
        datasetName = event.datasetName
        runtimeId = event.runtimeId
        runtimeName = event.runtimeName
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: AgentDatasetReprofiledEvent): AgentDatasetProfileState = apply {
        metadataReportId = event.metadataReportId
        runtimeDatasetBindingId = event.runtimeDatasetBindingId
        datasetId = event.datasetId
        organizationId = event.organizationId
        organizationName = event.organizationName
        runtimeId = event.runtimeId
        runtimeName = event.runtimeName
        featureSchemaId = event.featureSchemaId
        featureDomain = event.featureDomain
        featureSchemaVersion = event.featureSchemaVersion
        datasetName = event.datasetName
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
    fun evolve(event: AgentDatasetReprofilingFailedEvent): AgentDatasetProfileState = apply {
        metadataReportId = event.metadataReportId
        runtimeDatasetBindingId = event.runtimeDatasetBindingId
        datasetId = event.datasetId
        organizationId = event.organizationId
        organizationName = event.organizationName
        featureSchemaId = event.featureSchemaId
        featureDomain = event.featureDomain
        featureSchemaVersion = event.featureSchemaVersion
        datasetName = event.datasetName
        runtimeId = event.runtimeId
        runtimeName = event.runtimeName
        failureReason = event.failureReason
    }
}
