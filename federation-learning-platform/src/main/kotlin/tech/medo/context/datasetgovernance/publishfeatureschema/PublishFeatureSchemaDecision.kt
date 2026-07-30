package tech.medo.datasetgovernance.publishfeatureschema

import org.springframework.stereotype.Component
import tech.medo.datasetgovernance.publishfeatureschema.PublishFeatureSchemaCommand

import tech.medo.datasetgovernance.events.FeatureSchemaPublishedEvent
import tech.medo.datasetgovernance.featureschema.FeatureSchemaState


import tech.medo.datasetgovernance.domain.states.FeatureSchemaStateEnum


@Component
class PublishFeatureSchemaDecision {
    fun decide(command: PublishFeatureSchemaCommand, state: FeatureSchemaState): List<Any> {
        require(state.currentState == FeatureSchemaStateEnum.DRAFT) {
            "PublishFeatureSchema requires FeatureSchema to be Draft."
        }
        return listOf(
            FeatureSchemaPublishedEvent(featureSchemaId = command.featureSchemaId, publishNote = command.publishNote, featureDomain = command.featureDomain, version = command.version)
        )
    }
}
