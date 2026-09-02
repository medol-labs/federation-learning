package tech.medo.dictionarymaintenance.infrastructure.secondary.persistence.dictionarycatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.states.DictionaryStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


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
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
