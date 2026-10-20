package tech.medo.dictionarymaintenance.infrastructure.secondary.persistence.dictionarycatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import tech.medo.dictionarymaintenance.domain.states.DictionaryStateEnum
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat

@Entity
@Table(name = "dictionary_catalog")
class DictionaryCatalogReadModelEntity : MetadataProjection {
    @Id
    var dictionaryId: UUID? = null
    var dictionaryCode: String? = null
    var dictionaryName: String? = null
    @Column(columnDefinition = "text")
    var description: String? = null
    @Enumerated(EnumType.STRING)
    var state: DictionaryStateEnum? = null
    var registeredAt: LocalDateTime? = null
    var updatedAt: LocalDateTime? = null
    var archivedAt: LocalDateTime? = null
    @Column(columnDefinition = "text")
    var archiveReason: String? = null
    var projectionUpdatedAt: LocalDateTime? = null
    var userId: String? = null
    var sessionId: String? = null
    var correlationId: String? = null
    var causationId: String? = null
    var traceId: String? = null
    var tenantId: String? = null

    override fun assignMetadata(metadata: tech.medo.shared.application.metadata.MetadataSnapshot, updateTimestamp: Boolean) {
        if (updateTimestamp) this.projectionUpdatedAt = metadata.projectionUpdatedAt
        this.userId = metadata.userId
        this.sessionId = metadata.sessionId
        this.correlationId = metadata.correlationId
        this.causationId = metadata.causationId
        this.traceId = metadata.traceId
        this.tenantId = metadata.tenantId
    }
}
