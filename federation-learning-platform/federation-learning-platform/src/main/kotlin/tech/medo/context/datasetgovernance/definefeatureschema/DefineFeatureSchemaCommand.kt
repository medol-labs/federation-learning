package tech.medo.datasetgovernance.definefeatureschema

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.datasetgovernance.featureschema.FeatureSchemaSelection
import java.util.UUID;
import tech.medo.datasetgovernance.domain.types.FeatureDefinition;
import tech.medo.datasetgovernance.domain.types.LabelDefinition;

import tech.medo.datasetgovernance.featureschema.FeatureSchemaFeatureDomainVersionSelection

@Command
data class DefineFeatureSchemaCommand(
    val featureSchemaId: UUID = java.util.UUID.randomUUID(),
    val featureDomain: String,
    val version: String,
    val dataModality: String,
    val features: List<FeatureDefinition>,
    val labels: List<LabelDefinition>
) {
    @TargetEntityId
    val selection: FeatureSchemaSelection = FeatureSchemaSelection(featureDomain = featureDomain, version = version)

    val featureSchemaFeatureDomainVersionSelection: FeatureSchemaFeatureDomainVersionSelection = FeatureSchemaFeatureDomainVersionSelection(normalizedFeatureDomain = featureDomain.trim().lowercase(), normalizedVersion = version.trim().lowercase())
}
