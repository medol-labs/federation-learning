package tech.medo.datasetgovernance.supersedefeatureschemaversion

import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.supersedefeatureschemaversion.SupersedeFeatureSchemaVersionCommand

import tech.medo.datasetgovernance.events.FeatureSchemaVersionSupersededEvent
import tech.medo.datasetgovernance.featureschema.FeatureSchemaState





@Component
class SupersedeFeatureSchemaVersionDecision {
    fun decide(command: SupersedeFeatureSchemaVersionCommand, state: FeatureSchemaState): List<Any> {
        // TODO: validate domain rules against state before appending events.
        return listOf(
            FeatureSchemaVersionSupersededEvent(featureSchemaId = command.featureSchemaId, supersededByFeatureSchemaId = command.supersededByFeatureSchemaId, featureDomain = command.featureDomain, supersededVersion = "" /* TODO: derive value */, supersessionReason = command.supersessionReason, version = command.version)
        )
    }
}
