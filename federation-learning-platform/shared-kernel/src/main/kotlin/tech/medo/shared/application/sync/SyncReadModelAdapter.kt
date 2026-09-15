package tech.medo.shared.application.sync

interface SyncReadModelAdapter {
    fun supports(mode: String): Boolean
    fun syncOnce(target: SyncReadModelTarget, checkpoint: SyncReadModelCheckpoint?): SyncReadModelResult
}
