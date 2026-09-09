package tech.medo.datasetgovernance.retirefeatureschema

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.datasetgovernance.featureschema.FeatureSchemaSelection
import java.util.UUID;


@Command
data class RetireFeatureSchemaCommand(
    val featureSchemaId: UUID,
    val retirementReason: String,
    val featureDomain: String,
    val version: String
) {
    @TargetEntityId
    val selection: FeatureSchemaSelection = FeatureSchemaSelection(featureDomain = featureDomain, version = version)

}
