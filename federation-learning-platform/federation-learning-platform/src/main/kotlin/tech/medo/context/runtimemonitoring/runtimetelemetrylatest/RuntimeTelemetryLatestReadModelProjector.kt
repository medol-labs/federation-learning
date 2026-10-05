package tech.medo.runtimemonitoring.runtimetelemetrylatest

import org.axonframework.messaging.eventhandling.annotation.EventHandler
import org.axonframework.messaging.eventhandling.EventMessage
import org.axonframework.messaging.core.annotation.Namespace
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.medo.shared.application.metadata.ProjectionMetadata


import tech.medo.runtimemonitoring.events.RuntimeTelemetryRecordedEvent



interface RuntimeTelemetryLatestReadModelProjectionUpdater {
    fun update(
        event: RuntimeTelemetryRecordedEvent,
        message: EventMessage
    )
}

open class DefaultRuntimeTelemetryLatestReadModelProjectionUpdater(
    private val repository: RuntimeTelemetryLatestReadModelRepository
) : RuntimeTelemetryLatestReadModelProjectionUpdater {
    @Transactional
    open override fun update(
        event: RuntimeTelemetryRecordedEvent,
        message: EventMessage
    ) {

        val entity = repository.findProjectionById(event.nodeId) ?: RuntimeTelemetryLatestReadModelProjection().apply {
                this.nodeId = event.nodeId
        }
            entity.nodeId = event.nodeId
            entity.runtimeAgentId = event.runtimeAgentId
            entity.federationId = event.federationId
            entity.federationName = event.federationName
            entity.trainingJobId = event.trainingJobId
            entity.trainingJobObjective = event.trainingJobObjective
            entity.roundExecutionId = event.roundExecutionId
            entity.runtimeNodeName = event.runtimeNodeName
            entity.cpuLoad = event.cpuLoad
            entity.gpuLoad = event.gpuLoad
            entity.memoryLoad = event.memoryLoad
            entity.lastHeartbeatAt = event.lastHeartbeatAt
            entity.lastRecoveredAt = event.lastRecoveredAt
            entity.offlineDetectionPending = event.offlineDetectionPending
            entity.recoveryDetectionPending = event.recoveryDetectionPending
            entity.resourcePressureDetectionPending = event.resourcePressureDetectionPending
            entity.offlineReason = event.offlineReason
            entity.recoveryReason = event.recoveryReason
            entity.pressureType = event.pressureType
            entity.observedValue = event.observedValue
            entity.thresholdValue = event.thresholdValue
            entity.alertSeverity = event.alertSeverity
            entity.alertMessage = event.alertMessage
            entity.healthStatus = event.healthStatus
            entity.telemetryRetentionPolicy = event.telemetryRetentionPolicy
            ProjectionMetadata.assign(entity, message)
        repository.save(entity)

    }

}

@Configuration(proxyBeanMethods = false)
class RuntimeTelemetryLatestReadModelProjectionUpdaterConfiguration {
    @Bean
    @ConditionalOnMissingBean(RuntimeTelemetryLatestReadModelProjectionUpdater::class)
    fun defaultRuntimeTelemetryLatestReadModelProjectionUpdater(
        repository: RuntimeTelemetryLatestReadModelRepository
    ): RuntimeTelemetryLatestReadModelProjectionUpdater =
        DefaultRuntimeTelemetryLatestReadModelProjectionUpdater(repository)
}

@Namespace("readmodel-runtime-telemetry-latest")
@Component
class RuntimeTelemetryLatestReadModelProjector(
    private val updater: RuntimeTelemetryLatestReadModelProjectionUpdater
) {
    @EventHandler
    fun on(
        event: RuntimeTelemetryRecordedEvent,
        message: EventMessage
    ) {
        updater.update(event, message)
    }
}
