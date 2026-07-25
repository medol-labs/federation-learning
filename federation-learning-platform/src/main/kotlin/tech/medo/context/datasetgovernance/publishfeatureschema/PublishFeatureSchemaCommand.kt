package tech.medo.datasetgovernance.publishfeatureschema

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.datasetgovernance.featureschema.FeatureSchemaSelection
import java.util.UUID;


@Command
data class PublishFeatureSchemaCommand(
    val featureSchemaId: UUID,
    val publishNote: String?,
    val featureDomain: String,
    val version: String
) {
    @TargetEntityId
    val selection: FeatureSchemaSelection = FeatureSchemaSelection(featureDomain = featureDomain, version = version)

}
