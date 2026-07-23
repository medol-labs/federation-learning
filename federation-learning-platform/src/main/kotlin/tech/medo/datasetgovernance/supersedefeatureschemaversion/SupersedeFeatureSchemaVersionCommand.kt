package tech.medo.datasetgovernance.supersedefeatureschemaversion

import org.axonframework.messaging.commandhandling.annotation.Command
import org.axonframework.modelling.annotation.TargetEntityId
import tech.medo.datasetgovernance.featureschema.FeatureSchemaSelection
import java.util.UUID;


@Command
data class SupersedeFeatureSchemaVersionCommand(
    val featureSchemaId: UUID,
    val supersededByFeatureSchemaId: UUID,
    val supersessionReason: String?,
    val featureDomain: String,
    val version: String
) {
    @TargetEntityId
    val selection: FeatureSchemaSelection = FeatureSchemaSelection(featureDomain = featureDomain, version = version)

}
