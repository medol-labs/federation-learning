package tech.medo.runtimeagentoperations.infrastructure.secondary.persistence.runtimedatasetbindingcatalogreadmodel

import jakarta.persistence.Entity
import jakarta.persistence.Column
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
class RuntimeDatasetBindingCatalogReadModelEntity : MetadataProjection {
    @Id
    var runtimeDatasetBindingId: UUID? = null
    var datasetId: UUID? = null
    var organizationId: UUID? = null
    var runtimeId: UUID? = null
    var datasetName: String? = null
    var dataSourceType: String? = null
    var host: String? = null
    var port: Int? = null
    var url: String? = null
    var databaseName: String? = null
    var schemaName: String? = null
    var tableName: String? = null
    var filePath: String? = null
    var objectBucket: String? = null
    var objectPrefix: String? = null
    var dataFormat: String? = null
    var credentialSecretName: String? = null
    var configuredAt: LocalDateTime? = null
    override var userId: String? = null
    override var sessionId: String? = null
    override var correlationId: String? = null
    override var causationId: String? = null
    override var traceId: String? = null
    override var tenantId: String? = null
}
