package tech.medo.runtimeagentoperations.datasetcapability

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata

import tech.medo.runtimeagentoperations.events.DatasetDeclaredEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetMetadataReportedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetProfilingFailedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetReprofiledEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetReprofilingFailedEvent
import tech.medo.runtimeagentoperations.events.DatasetContractValidatedEvent
import tech.medo.runtimeagentoperations.events.DatasetContractValidationFailedEvent
import tech.medo.runtimeagentoperations.events.DatasetContractRevalidatedEvent
import tech.medo.runtimeagentoperations.events.DatasetContractRevalidationFailedEvent
import tech.medo.runtimeagentoperations.events.DatasetRejectedForTrainingEvent
import tech.medo.runtimeagentoperations.events.DatasetApprovedForTrainingEvent
import tech.medo.runtimeagentoperations.events.DatasetTrainingApprovalRevokedEvent



interface DatasetCapabilityReadModelProjectionUpdater {
    fun update(
        event: DatasetDeclaredEvent,
        message: EventMessage
    )

    fun update(
        event: AgentDatasetMetadataReportedEvent,
        message: EventMessage
    )

    fun update(
        event: AgentDatasetProfilingFailedEvent,
        message: EventMessage
    )

    fun update(
        event: AgentDatasetReprofiledEvent,
        message: EventMessage
    )

    fun update(
        event: AgentDatasetReprofilingFailedEvent,
        message: EventMessage
    )

    fun update(
        event: DatasetContractValidatedEvent,
        message: EventMessage
    )

    fun update(
        event: DatasetContractValidationFailedEvent,
        message: EventMessage
    )

    fun update(
        event: DatasetContractRevalidatedEvent,
        message: EventMessage
    )

    fun update(
        event: DatasetContractRevalidationFailedEvent,
        message: EventMessage
    )

    fun update(
        event: DatasetRejectedForTrainingEvent,
        message: EventMessage
    )

    fun update(
        event: DatasetApprovedForTrainingEvent,
        message: EventMessage
    )

    fun update(
        event: DatasetTrainingApprovalRevokedEvent,
        message: EventMessage
    )
}

@Component
@ConditionalOnMissingBean(DatasetCapabilityReadModelProjectionUpdater::class)
class DefaultDatasetCapabilityReadModelProjectionUpdater(
    private val repository: DatasetCapabilityReadModelRepository
) : DatasetCapabilityReadModelProjectionUpdater {
    @Transactional
    override fun update(
        event: DatasetDeclaredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.featureSchemaId = event.featureSchemaId
            entity.features = event.features
            entity.labels = event.labels
            entity.organizationName = event.organizationName
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.datasetName = event.datasetName
            entity.datasetUsage = event.datasetUsage
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: AgentDatasetMetadataReportedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.organizationName = event.organizationName
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.datasetName = event.datasetName
            entity.sampleCount = event.sampleCount
            entity.featureCount = event.featureCount
            entity.schemaCompatible = event.schemaCompatible
            entity.labelCompatible = event.labelCompatible
            entity.qualityScore = event.qualityScore
            entity.nonIidScore = event.nonIidScore
            entity.metadataReportId = event.metadataReportId
            entity.metadataStatus = "Reported"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: AgentDatasetProfilingFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.organizationName = event.organizationName
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.datasetName = event.datasetName
            entity.metadataReportId = event.metadataReportId
            entity.metadataStatus = "Failed"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: AgentDatasetReprofiledEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.organizationName = event.organizationName
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.datasetName = event.datasetName
            entity.sampleCount = event.sampleCount
            entity.featureCount = event.featureCount
            entity.schemaCompatible = event.schemaCompatible
            entity.labelCompatible = event.labelCompatible
            entity.qualityScore = event.qualityScore
            entity.nonIidScore = event.nonIidScore
            entity.metadataReportId = event.metadataReportId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: AgentDatasetReprofilingFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.organizationName = event.organizationName
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.datasetName = event.datasetName
            entity.metadataReportId = event.metadataReportId
            entity.metadataStatus = "Failed"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: DatasetContractValidatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.featureSchemaId = event.featureSchemaId
            entity.schemaCompatible = event.schemaCompatible
            entity.labelCompatible = event.labelCompatible
            entity.qualityScore = event.qualityScore
            entity.nonIidScore = event.nonIidScore
            entity.metadataReportId = event.metadataReportId
            entity.contractStatus = "ContractValidationCompleted"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: DatasetContractValidationFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.featureSchemaId = event.featureSchemaId
            entity.schemaCompatible = event.schemaCompatible
            entity.labelCompatible = event.labelCompatible
            entity.qualityScore = event.qualityScore
            entity.nonIidScore = event.nonIidScore
            entity.metadataReportId = event.metadataReportId
            entity.contractStatus = "Failed"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: DatasetContractRevalidatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.featureSchemaId = event.featureSchemaId
            entity.schemaCompatible = event.schemaCompatible
            entity.labelCompatible = event.labelCompatible
            entity.qualityScore = event.qualityScore
            entity.nonIidScore = event.nonIidScore
            entity.metadataReportId = event.metadataReportId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: DatasetContractRevalidationFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.featureSchemaId = event.featureSchemaId
            entity.schemaCompatible = event.schemaCompatible
            entity.labelCompatible = event.labelCompatible
            entity.qualityScore = event.qualityScore
            entity.nonIidScore = event.nonIidScore
            entity.metadataReportId = event.metadataReportId
            entity.contractStatus = "Failed"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: DatasetRejectedForTrainingEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: DatasetApprovedForTrainingEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.approved = true
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    override fun update(
        event: DatasetTrainingApprovalRevokedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetCapabilityReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.approvalStatus = "ApprovalRevoked"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

}

@Namespace("readmodel-dataset-capability")
@Component
class DatasetCapabilityReadModelProjector(
    private val updater: DatasetCapabilityReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: DatasetDeclaredEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: AgentDatasetMetadataReportedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: AgentDatasetProfilingFailedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: AgentDatasetReprofiledEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: AgentDatasetReprofilingFailedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: DatasetContractValidatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: DatasetContractValidationFailedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: DatasetContractRevalidatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: DatasetContractRevalidationFailedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: DatasetRejectedForTrainingEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: DatasetApprovedForTrainingEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: DatasetTrainingApprovalRevokedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
