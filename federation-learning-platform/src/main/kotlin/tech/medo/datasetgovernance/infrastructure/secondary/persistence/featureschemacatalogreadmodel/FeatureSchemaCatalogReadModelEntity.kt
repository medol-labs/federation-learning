package tech.medo.datasetgovernance.infrastructure.secondary.persistence.featureschemacatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import tech.medo.shared.application.metadata.MetadataProjection
import java.util.UUID;


@Entity
class FeatureSchemaCatalogReadModelEntity : MetadataProjection {
    @Id
    var featureSchemaId: UUID? = null
    var featureDomain: String? = null
    var version: String? = null
    var dataModality: String? = null
    @Column(columnDefinition = "text")
    var features: String? = null
    @Column(columnDefinition = "text")
    var labels: String? = null
    var featureCount: Int? = null
    var schemaStatus: String? = null
    var supersededByFeatureSchemaId: UUID? = null
    var recommendedForDomain: Boolean? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
