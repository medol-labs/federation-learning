package tech.medo.shared.application.sync

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

@Entity
@Table(name = "medol_sync_read_model_checkpoint")
class SyncReadModelCheckpoint {
    @Id
    var target: String = ""
    var source: String = ""
    var lastSuccessfulSyncedAt: LocalDateTime? = null
    var lastAttemptedAt: LocalDateTime? = null
    var lastStatus: String = "NEVER_SYNCED"
    var lastError: String? = null
    var syncedItemCount: Int = 0
}

interface SyncReadModelCheckpointRepository : JpaRepository<SyncReadModelCheckpoint, String>
