package tech.medo.datasetgovernance.markcurrentrecommendedfeatureschemaversion

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.datasetgovernance.featureschema.FeatureSchemaSelection
import java.util.UUID;


@Command
data class MarkCurrentRecommendedFeatureSchemaVersionCommand(
    val featureSchemaId: UUID,
    val recommendationNote: String?,
    val featureDomain: String,
    val version: String
) {
    @TargetEntityId
    val selection: FeatureSchemaSelection = FeatureSchemaSelection(featureDomain = featureDomain, version = version)

}
