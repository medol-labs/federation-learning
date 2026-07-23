package tech.medo.dictionarymaintenance.infrastructure.secondary.persistence.dictionaryvaluecatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import tech.medo.dictionarymaintenance.domain.states.DictionaryValueStateEnum;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
class DictionaryValueCatalogReadModelEntity : MetadataProjection {
    @Id
    var dictionaryValueId: UUID? = null
    var dictionaryId: UUID? = null
    var dictionaryCode: String? = null
    var valueCode: String? = null
    var displayName: String? = null
    var displayOrder: Int? = null
    var description: String? = null
    var active: Boolean? = null
    @Enumerated(EnumType.STRING)
    var state: DictionaryValueStateEnum? = null
    var addedAt: LocalDateTime? = null
    var updatedAt: LocalDateTime? = null
    var disabledAt: LocalDateTime? = null
    var disabledReason: String? = null
    var enabledAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
