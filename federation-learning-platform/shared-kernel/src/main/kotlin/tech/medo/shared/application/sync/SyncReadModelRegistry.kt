package tech.medo.shared.application.sync

import org.springframework.stereotype.Component

@Component
class SyncReadModelRegistry(targets: List<SyncReadModelTarget>) {
    val targets: List<SyncReadModelTarget> = targets.sortedBy { it.name }
}
