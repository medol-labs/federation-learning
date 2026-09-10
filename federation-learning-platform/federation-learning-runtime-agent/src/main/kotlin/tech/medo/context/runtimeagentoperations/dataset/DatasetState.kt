package tech.medo.runtimeagentoperations.dataset

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.runtimeagentoperations.events.DatasetDeclaredEvent
import tech.medo.runtimeagentoperations.events.DatasetContractValidatedEvent
import tech.medo.runtimeagentoperations.events.DatasetContractValidationFailedEvent
import tech.medo.runtimeagentoperations.events.DatasetContractRevalidatedEvent
import tech.medo.runtimeagentoperations.events.DatasetContractRevalidationFailedEvent
import tech.medo.runtimeagentoperations.events.DatasetRejectedForTrainingEvent
import tech.medo.runtimeagentoperations.events.DatasetApprovedForTrainingEvent
import tech.medo.runtimeagentoperations.events.DatasetTrainingApprovalRevokedEvent
import tech.medo.runtimeagentoperations.domain.states.DatasetStateEnum

import java.util.UUID;
import tech.medo.runtimeagentoperations.domain.types.FeatureDefinition;
import tech.medo.runtimeagentoperations.domain.types.LabelDefinition;
import java.math.BigDecimal;


@EventSourced(idType = DatasetSelection::class)
class DatasetState @EntityCreator constructor() {
    companion object {
        @JvmStatic
        @EventCriteriaBuilder
        fun resolveCriteria(selection: DatasetSelection): EventCriteria = EventCriteria.either(
                EventCriteria.havingTags(Tag.of(DatasetTags.ORGANIZATION_ID, selection.organizationId.toString())),
                EventCriteria.havingTags(Tag.of(DatasetTags.FEATURE_SCHEMA_ID, selection.featureSchemaId.toString())),
                EventCriteria.havingTags(Tag.of(DatasetTags.DATASET_NAME, selection.datasetName.toString()))
        )
    }


    var currentState: DatasetStateEnum? = null
    var datasetId: UUID? = null
    var organizationId: UUID? = null
    var organizationName: String? = null
    var featureSchemaId: UUID? = null
    var featureDomain: String? = null
    var featureSchemaVersion: String? = null
    var datasetName: String? = null
    var datasetUsage: String? = null
    var features: List<FeatureDefinition> = emptyList()
    var labels: List<LabelDefinition> = emptyList()
    var metadataReportId: UUID? = null
    var schemaCompatible: Boolean? = null
    var labelCompatible: Boolean? = null
    var qualityScore: BigDecimal? = null
    var nonIidScore: BigDecimal? = null
    var failureReason: String? = null
    var rejectionReason: String? = null
    var revokeReason: String? = null

    @EventSourcingHandler
    fun evolve(event: DatasetDeclaredEvent): DatasetState = apply {
        currentState = DatasetStateEnum.REGISTERED
        datasetId = event.datasetId
        organizationId = event.organizationId
        organizationName = event.organizationName
        featureSchemaId = event.featureSchemaId
        featureDomain = event.featureDomain
        featureSchemaVersion = event.featureSchemaVersion
        datasetName = event.datasetName
        datasetUsage = event.datasetUsage
        features = event.features
        labels = event.labels
    }

    @EventSourcingHandler
    fun evolve(event: DatasetContractValidatedEvent): DatasetState = apply {
        currentState = DatasetStateEnum.CONTRACT_VALIDATION_COMPLETED
        datasetId = event.datasetId
        featureSchemaId = event.featureSchemaId
        metadataReportId = event.metadataReportId
        schemaCompatible = event.schemaCompatible
        labelCompatible = event.labelCompatible
        qualityScore = event.qualityScore
        nonIidScore = event.nonIidScore
    }

    @EventSourcingHandler
    fun evolve(event: DatasetContractValidationFailedEvent): DatasetState = apply {
        currentState = DatasetStateEnum.CONTRACT_VALIDATION_COMPLETED
        datasetId = event.datasetId
        featureSchemaId = event.featureSchemaId
        metadataReportId = event.metadataReportId
        schemaCompatible = event.schemaCompatible
        labelCompatible = event.labelCompatible
        qualityScore = event.qualityScore
        nonIidScore = event.nonIidScore
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: DatasetContractRevalidatedEvent): DatasetState = apply {
        datasetId = event.datasetId
        featureSchemaId = event.featureSchemaId
        metadataReportId = event.metadataReportId
        schemaCompatible = event.schemaCompatible
        labelCompatible = event.labelCompatible
        qualityScore = event.qualityScore
        nonIidScore = event.nonIidScore
    }

    @EventSourcingHandler
    fun evolve(event: DatasetContractRevalidationFailedEvent): DatasetState = apply {
        currentState = DatasetStateEnum.CONTRACT_VALIDATION_COMPLETED
        datasetId = event.datasetId
        featureSchemaId = event.featureSchemaId
        metadataReportId = event.metadataReportId
        schemaCompatible = event.schemaCompatible
        labelCompatible = event.labelCompatible
        qualityScore = event.qualityScore
        nonIidScore = event.nonIidScore
        failureReason = event.failureReason
    }

    @EventSourcingHandler
    fun evolve(event: DatasetRejectedForTrainingEvent): DatasetState = apply {
        currentState = DatasetStateEnum.REJECTED
        datasetId = event.datasetId
        rejectionReason = event.rejectionReason
    }

    @EventSourcingHandler
    fun evolve(event: DatasetApprovedForTrainingEvent): DatasetState = apply {
        currentState = DatasetStateEnum.APPROVED
        datasetId = event.datasetId
        organizationId = event.organizationId
    }

    @EventSourcingHandler
    fun evolve(event: DatasetTrainingApprovalRevokedEvent): DatasetState = apply {
        currentState = DatasetStateEnum.APPROVAL_REVOKED
        datasetId = event.datasetId
        revokeReason = event.revokeReason
    }
}
