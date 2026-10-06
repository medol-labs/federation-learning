package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.agentdictionaryvaluetranslationcatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID
import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonFormat


@Entity
@Table(name = "agent_dictionary_value_translation_catalog")
class AgentDictionaryValueTranslationCatalogReadModelEntity : MetadataProjection {
    @Id
    var dictionaryValueTranslationId: UUID? = null
    var dictionaryValueId: UUID? = null
    var dictionaryCode: String? = null
    var valueCode: String? = null
    var locale: String? = null
    var displayName: String? = null
    @Column(columnDefinition = "text")
    var description: String? = null
    var syncedAt: LocalDateTime? = null
    override var projectionUpdatedAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
