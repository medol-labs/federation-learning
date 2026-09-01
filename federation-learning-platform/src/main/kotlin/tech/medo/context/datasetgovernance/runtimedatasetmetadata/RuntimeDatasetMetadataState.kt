package tech.medo.datasetgovernance.runtimedatasetmetadata

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.datasetgovernance.events.DatasetMetadataReportedEvent
import tech.medo.datasetgovernance.events.DatasetMetadataReprofiledEvent
import tech.medo.datasetgovernance.domain.states.RuntimeDatasetMetadataStateEnum

import java.util.UUID;
import java.math.BigDecimal;


@EventSourced(idType = UUID::class, tagKey = RuntimeDatasetMetadataTags.RUNTIME_DATASET_BINDING_ID)
class RuntimeDatasetMetadataState @EntityCreator constructor() {

    var currentState: RuntimeDatasetMetadataStateEnum? = null
    var runtimeDatasetBindingId: UUID? = null
    var metadataReportId: UUID? = null
    var datasetId: UUID? = null
    var organizationId: UUID? = null
    var runtimeId: UUID? = null
    var featureSchemaId: UUID? = null
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

    @EventSourcingHandler
    fun evolve(event: DatasetMetadataReportedEvent): RuntimeDatasetMetadataState = apply {
        currentState = RuntimeDatasetMetadataStateEnum.METADATA_REPORTED
        runtimeDatasetBindingId = event.runtimeDatasetBindingId
        metadataReportId = event.metadataReportId
        datasetId = event.datasetId
        organizationId = event.organizationId
        runtimeId = event.runtimeId
        featureSchemaId = event.featureSchemaId
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
    fun evolve(event: DatasetMetadataReprofiledEvent): RuntimeDatasetMetadataState = apply {
        currentState = RuntimeDatasetMetadataStateEnum.METADATA_REPORTED
        runtimeDatasetBindingId = event.runtimeDatasetBindingId
        metadataReportId = event.metadataReportId
        datasetId = event.datasetId
        organizationId = event.organizationId
        runtimeId = event.runtimeId
        featureSchemaId = event.featureSchemaId
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
}
