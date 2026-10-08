package tech.medo.trainingorchestration.recordpersonalizedmodelresult

import tech.medo.trainingorchestration.recordpersonalizedmodelresult.RecordPersonalizedModelResultCommand


import tech.medo.trainingorchestration.events.PersonalizedModelResultRecordedEvent
import tech.medo.trainingorchestration.personalizedmodelresult.PersonalizedModelResultState





interface RecordPersonalizedModelResultDecision {
    fun decide(command: RecordPersonalizedModelResultCommand): List<Any> {
        return listOf(
            PersonalizedModelResultRecordedEvent(personalizedModelId = command.personalizedModelId, modelUpdateSubmissionId = command.modelUpdateSubmissionId, trainingJobId = command.trainingJobId, trainingRunConfigurationId = command.trainingRunConfigurationId, roundId = command.roundId, roundNumber = command.roundNumber, runtimeId = command.runtimeId, featureSchemaId = command.featureSchemaId, personalizationMode = command.personalizationMode, personalizationScope = command.personalizationScope, personalizedArtifactPolicy = command.personalizedArtifactPolicy, personalizationMetric = command.personalizationMetric, minimumPersonalizationLift = command.minimumPersonalizationLift, personalizationTargetType = command.personalizationTargetType, personalizationTargetId = command.personalizationTargetId, personalizationCohortId = command.personalizationCohortId, personalizationProfileId = command.personalizationProfileId, personalizedModelArtifactRef = command.personalizedModelArtifactRef, personalizedModelArtifactDigest = command.personalizedModelArtifactDigest, personalizedMetricsArtifactRef = command.personalizedMetricsArtifactRef, personalizedAccuracy = command.personalizedAccuracy, personalizationLift = command.personalizationLift)
        )
    }
}
