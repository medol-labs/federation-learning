package tech.medo.datasetgovernance.dataset

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.datasetgovernance.events.DatasetDeclaredEvent
import tech.medo.datasetgovernance.events.DatasetMetadataReprofiledEvent
import tech.medo.datasetgovernance.events.DatasetContractValidatedEvent
import tech.medo.datasetgovernance.events.DatasetContractValidationFailedEvent
import tech.medo.datasetgovernance.events.DatasetRejectedForTrainingEvent
import tech.medo.datasetgovernance.events.DatasetApprovedForTrainingEvent
import tech.medo.datasetgovernance.events.DatasetTrainingApprovalRevokedEvent
import tech.medo.datasetgovernance.domain.states.DatasetStateEnum

import java.util.UUID;
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
    private var datasetId: UUID? = null
    private var organizationId: UUID? = null
    private var featureSchemaId: UUID? = null
    private var datasetName: String? = null
    private var datasetType: String? = null
    private var datasetUsage: String? = null
    private var metadataReportId: UUID? = null
    private var runtimeId: UUID? = null
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
    private var rejectionReason: String? = null
    private var revokeReason: String? = null

    @EventSourcingHandler
    fun evolve(event: DatasetDeclaredEvent): DatasetState = apply {
        currentState = DatasetStateEnum.REGISTERED
        datasetId = event.datasetId
        organizationId = event.organizationId
        featureSchemaId = event.featureSchemaId
        datasetName = event.datasetName
        datasetType = event.datasetType
        datasetUsage = event.datasetUsage
    }

    @EventSourcingHandler
    fun evolve(event: DatasetMetadataReprofiledEvent): DatasetState = apply {
        currentState = DatasetStateEnum.METADATA_REPORTED
        metadataReportId = event.metadataReportId
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
