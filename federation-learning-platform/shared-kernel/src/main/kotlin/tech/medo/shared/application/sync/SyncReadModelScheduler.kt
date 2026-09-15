package tech.medo.shared.application.sync

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class SyncReadModelScheduler(
    private val properties: SyncReadModelProperties,
    private val registry: SyncReadModelRegistry,
    private val adapters: List<SyncReadModelAdapter>,
    private val checkpoints: SyncReadModelCheckpointRepository
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(fixedDelayString = "\${medol.sync.fixed-delay-ms:30000}")
    fun syncAll() {
        if (!properties.enabled || registry.targets.isEmpty()) return
        val adapter = adapters.firstOrNull { it.supports(properties.mode) }
        if (adapter == null) {
            log.warn("No sync read model adapter supports mode={}", properties.mode)
            return
        }

        registry.targets.forEach { target ->
            val checkpoint = checkpoints.findById(target.name).orElseGet {
                SyncReadModelCheckpoint().also {
                    it.target = target.name
                    it.source = target.source
                }
            }
            checkpoint.lastAttemptedAt = LocalDateTime.now()
            try {
                val result = adapter.syncOnce(target, checkpoint)
                checkpoint.lastSuccessfulSyncedAt = LocalDateTime.now()
                checkpoint.lastStatus = "SYNCED"
                checkpoint.lastError = null
                checkpoint.syncedItemCount = result.itemCount
            } catch (ex: Exception) {
                checkpoint.lastStatus = "FAILED"
                checkpoint.lastError = ex.message
                log.warn("Sync read model target={} failed", target.name, ex)
            }
            checkpoints.save(checkpoint)
        }
    }
}
