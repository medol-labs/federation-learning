package tech.medo.runtimeagentoperations.datasetreadiness

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.runtimeagentoperations.events.DatasetDeclaredEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidatedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessValidationFailedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessRevalidatedEvent
import tech.medo.runtimeagentoperations.events.AgentDatasetAccessRevalidationFailedEvent
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



interface DatasetReadinessReadModelProjectionUpdater {
    fun update(
        event: DatasetDeclaredEvent,
        message: EventMessage
    )

    fun update(
        event: AgentDatasetAccessValidatedEvent,
        message: EventMessage
    )

    fun update(
        event: AgentDatasetAccessValidationFailedEvent,
        message: EventMessage
    )

    fun update(
        event: AgentDatasetAccessRevalidatedEvent,
        message: EventMessage
    )

    fun update(
        event: AgentDatasetAccessRevalidationFailedEvent,
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
@ConditionalOnMissingBean(DatasetReadinessReadModelProjectionUpdater::class)
class DefaultDatasetReadinessReadModelProjectionUpdater(
    private val repository: DatasetReadinessReadModelRepository
) : DatasetReadinessReadModelProjectionUpdater {
    @Transactional
    override fun update(
        event: DatasetDeclaredEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.featureSchemaId = event.featureSchemaId
            entity.datasetName = event.datasetName
            entity.organizationName = event.organizationName
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.datasetUsage = event.datasetUsage
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    override fun update(
        event: AgentDatasetAccessValidatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.datasetName = event.datasetName
            entity.organizationName = event.organizationName
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.datasetAccessValidationId = event.datasetAccessValidationId
            entity.readable = event.readable
            entity.schemaReadable = event.schemaReadable
            entity.sampleBatchReadable = event.sampleBatchReadable
            entity.accessStatus = "Checked"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    override fun update(
        event: AgentDatasetAccessValidationFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.datasetName = event.datasetName
            entity.organizationName = event.organizationName
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.datasetAccessValidationId = event.datasetAccessValidationId
            entity.accessStatus = "Failed"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    override fun update(
        event: AgentDatasetAccessRevalidatedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.datasetName = event.datasetName
            entity.organizationName = event.organizationName
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.datasetAccessValidationId = event.datasetAccessValidationId
            entity.readable = event.readable
            entity.schemaReadable = event.schemaReadable
            entity.sampleBatchReadable = event.sampleBatchReadable
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    override fun update(
        event: AgentDatasetAccessRevalidationFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.datasetName = event.datasetName
            entity.organizationName = event.organizationName
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.datasetAccessValidationId = event.datasetAccessValidationId
            entity.accessStatus = "Failed"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    override fun update(
        event: AgentDatasetMetadataReportedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.datasetName = event.datasetName
            entity.organizationName = event.organizationName
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.sampleCount = event.sampleCount
            entity.featureCount = event.featureCount
            entity.schemaCompatible = event.schemaCompatible
            entity.labelCompatible = event.labelCompatible
            entity.qualityScore = event.qualityScore
            entity.nonIidScore = event.nonIidScore
            entity.classBalanceScore = event.classBalanceScore
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

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.datasetName = event.datasetName
            entity.organizationName = event.organizationName
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
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

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.datasetName = event.datasetName
            entity.organizationName = event.organizationName
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
            entity.sampleCount = event.sampleCount
            entity.featureCount = event.featureCount
            entity.schemaCompatible = event.schemaCompatible
            entity.labelCompatible = event.labelCompatible
            entity.qualityScore = event.qualityScore
            entity.nonIidScore = event.nonIidScore
            entity.classBalanceScore = event.classBalanceScore
            entity.metadataReportId = event.metadataReportId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    override fun update(
        event: AgentDatasetReprofilingFailedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            entity.runtimeId = event.runtimeId
            entity.featureSchemaId = event.featureSchemaId
            entity.datasetName = event.datasetName
            entity.organizationName = event.organizationName
            entity.featureDomain = event.featureDomain
            entity.featureSchemaVersion = event.featureSchemaVersion
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

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
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

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
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

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
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

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
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

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
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

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.organizationId = event.organizationId
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

    @Transactional
    override fun update(
        event: DatasetTrainingApprovalRevokedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.datasetId) ?: DatasetReadinessReadModelProjection().apply {
                this.datasetId = event.datasetId
        }
            entity.datasetId = event.datasetId
            entity.approvalStatus = "ApprovalRevoked"
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

}

@Namespace("readmodel-dataset-readiness")
@Component
class DatasetReadinessReadModelProjector(
    private val updater: DatasetReadinessReadModelProjectionUpdater
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
        event: AgentDatasetAccessValidatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: AgentDatasetAccessValidationFailedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: AgentDatasetAccessRevalidatedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(
        event: AgentDatasetAccessRevalidationFailedEvent,
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
