package tech.medo.dataexchange.dataexportjob

import org.axonframework.messaging.core.annotation.Namespace
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.dataexchange.events.DataExportCompletedEvent
import tech.medo.dataexchange.events.DataExportFailedEvent
import tech.medo.dataexchange.events.DataExportProcessingStartedEvent
import tech.medo.dataexchange.events.DataExportRequestedEvent
import tech.medo.shared.application.metadata.ProjectionMetadata

interface DataExportJobCatalogReadModelProjectionUpdater {
    fun update(event: DataExportRequestedEvent, message: EventMessage)
    fun update(event: DataExportProcessingStartedEvent, message: EventMessage)
    fun update(event: DataExportCompletedEvent, message: EventMessage)
    fun update(event: DataExportFailedEvent, message: EventMessage)
}

open class DefaultDataExportJobCatalogReadModelProjectionUpdater(
    private val repository: DataExportJobCatalogReadModelRepository
) : DataExportJobCatalogReadModelProjectionUpdater {
    @Transactional
    open override fun update(event: DataExportRequestedEvent, message: EventMessage) {
        val entity = repository.findProjectionById(event.dataExportJobId) ?: DataExportJobCatalogReadModelProjection().apply {
            dataExportJobId = event.dataExportJobId
        }
        entity.dataExportJobId = event.dataExportJobId
        entity.resourceName = event.resourceName
        entity.criteriaJson = event.criteriaJson
        entity.sortJson = event.sortJson
        entity.columnsJson = event.columnsJson
        entity.requestedLocale = event.requestedLocale
        entity.requestedAt = event.requestedAt
        entity.snapshotUpperBound = event.snapshotUpperBound
        entity.requestHash = event.requestHash
        entity.fileName = event.fileName
        entity.status = event.status
        entity.errorMessage = null
        ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    open override fun update(event: DataExportProcessingStartedEvent, message: EventMessage) {
        val entity = repository.findProjectionById(event.dataExportJobId) ?: DataExportJobCatalogReadModelProjection().apply {
            dataExportJobId = event.dataExportJobId
        }
        entity.status = event.status
        entity.errorMessage = null
        ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    open override fun update(event: DataExportCompletedEvent, message: EventMessage) {
        val entity = repository.findProjectionById(event.dataExportJobId) ?: DataExportJobCatalogReadModelProjection().apply {
            dataExportJobId = event.dataExportJobId
        }
        entity.fileName = event.fileName
        entity.filePath = event.filePath
        entity.rowCount = event.rowCount
        entity.status = event.status
        entity.errorMessage = null
        ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }

    @Transactional
    open override fun update(event: DataExportFailedEvent, message: EventMessage) {
        val entity = repository.findProjectionById(event.dataExportJobId) ?: DataExportJobCatalogReadModelProjection().apply {
            dataExportJobId = event.dataExportJobId
        }
        entity.errorMessage = event.errorMessage
        entity.status = event.status
        ProjectionMetadata.assign(entity, message)
        repository.save(entity)
    }
}

@Configuration(proxyBeanMethods = false)
class DataExportJobCatalogReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(DataExportJobCatalogReadModelProjectionUpdater::class)
    fun defaultDataExportJobCatalogReadModelProjectionUpdater(
        repository: DataExportJobCatalogReadModelRepository
    ): DataExportJobCatalogReadModelProjectionUpdater =
        DefaultDataExportJobCatalogReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-data-export-job-catalog")
@Component
class DataExportJobCatalogReadModelProjector(
    private val updater: DataExportJobCatalogReadModelProjectionUpdater
) {
    @EventHandler
    fun on(event: DataExportRequestedEvent, message: EventMessage) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(event: DataExportProcessingStartedEvent, message: EventMessage) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(event: DataExportCompletedEvent, message: EventMessage) {
        updater.update(event, message)
    }

    @EventHandler
    fun on(event: DataExportFailedEvent, message: EventMessage) {
        updater.update(event, message)
    }
}
