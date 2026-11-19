package tech.medo.infrastructure.secondary.runtimeagentoperations.roundexecution

import java.util.UUID
import org.springframework.data.domain.PageRequest
import tech.jhipster.service.filter.StringFilter
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModel
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelCriteria
import tech.medo.runtimeagentoperations.runtimedatasetbindingcatalog.RuntimeDatasetBindingCatalogReadModelRepository

internal fun RuntimeDatasetBindingCatalogReadModelRepository.forExecutionPlan(
    runtime: UUID, organization: UUID, schema: UUID, dataset: UUID?
): RuntimeDatasetBindingCatalogReadModel? {
    if (dataset == null) return null
    fun exact(id: UUID) = StringFilter().apply { equals = id.toString() }
    val criteria = RuntimeDatasetBindingCatalogReadModelCriteria().apply {
        runtimeId = exact(runtime)
        organizationId = exact(organization)
        featureSchemaId = exact(schema)
        datasetId = exact(dataset)
    }
    // Two rows suffice to distinguish a unique binding from an ambiguous one.
    return findAllByCriteria(criteria, PageRequest.of(0, 2)).content.singleOrNull()
}
