package tech.medo.datasetgovernance.infrastructure.secondary.persistence.currentrecommendedfeatureschemacatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
class CurrentRecommendedFeatureSchemaCatalogReadModelEntity : MetadataProjection {
    @Id
    var featureDomain: String? = null
    var recommendedFeatureSchemaId: UUID? = null
    var recommendedVersion: String? = null
    var recommendedAt: LocalDateTime? = null
    var recommendationNote: String? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
