package tech.medo.datasetgovernance.deprecatefeatureschema

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.datasetgovernance.featureschema.FeatureSchemaSelection
import java.util.UUID;


@Command
data class DeprecateFeatureSchemaCommand(
    val featureSchemaId: UUID,
    val deprecationReason: String,
    val featureDomain: String,
    val version: String
) {
    @TargetEntityId
    val selection: FeatureSchemaSelection = FeatureSchemaSelection(featureDomain = featureDomain, version = version)

}
