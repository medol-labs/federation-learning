package tech.medo.trainingorchestration.personalizedmodelresult

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.trainingorchestration.events.PersonalizedModelResultRecordedEvent
import tech.medo.trainingorchestration.domain.states.PersonalizedModelResultStateEnum

import java.util.UUID;
import java.math.BigDecimal;


@EventSourced(idType = UUID::class, tagKey = PersonalizedModelResultTags.PERSONALIZED_MODEL_ID)
class PersonalizedModelResultState @EntityCreator constructor() {

    var currentState: PersonalizedModelResultStateEnum? = null
    var personalizedModelId: UUID? = null
    var modelUpdateSubmissionId: UUID? = null
    var trainingJobId: UUID? = null
    var trainingRunConfigurationId: UUID? = null
    var roundId: UUID? = null
    var roundNumber: Int? = null
    var runtimeId: UUID? = null
    var featureSchemaId: UUID? = null
    var personalizationMode: String? = null
    var personalizationScope: String? = null
    var personalizedArtifactPolicy: String? = null
    var personalizationMetric: String? = null
    var minimumPersonalizationLift: BigDecimal? = null
    var personalizationTargetType: String? = null
    var personalizationTargetId: UUID? = null
    var personalizationCohortId: UUID? = null
    var personalizationProfileId: UUID? = null
    var personalizedModelArtifactRef: String? = null
    var personalizedModelArtifactDigest: String? = null
    var personalizedModelStorageLocation: String? = null
    var platformManagedArtifactRef: String? = null
    var personalizedMetricsArtifactRef: String? = null
    var personalizedAccuracy: BigDecimal? = null
    var personalizationLift: BigDecimal? = null

    @EventSourcingHandler
    fun evolve(event: PersonalizedModelResultRecordedEvent): PersonalizedModelResultState = apply {
        currentState = PersonalizedModelResultStateEnum.PersonalizedResultRecorded
        personalizedModelId = event.personalizedModelId
        modelUpdateSubmissionId = event.modelUpdateSubmissionId
        trainingJobId = event.trainingJobId
        trainingRunConfigurationId = event.trainingRunConfigurationId
        roundId = event.roundId
        roundNumber = event.roundNumber
        runtimeId = event.runtimeId
        featureSchemaId = event.featureSchemaId
        personalizationMode = event.personalizationMode
        personalizationScope = event.personalizationScope
        personalizedArtifactPolicy = event.personalizedArtifactPolicy
        personalizationMetric = event.personalizationMetric
        minimumPersonalizationLift = event.minimumPersonalizationLift
        personalizationTargetType = event.personalizationTargetType
        personalizationTargetId = event.personalizationTargetId
        personalizationCohortId = event.personalizationCohortId
        personalizationProfileId = event.personalizationProfileId
        personalizedModelArtifactRef = event.personalizedModelArtifactRef
        personalizedModelArtifactDigest = event.personalizedModelArtifactDigest
        personalizedModelStorageLocation = event.personalizedModelStorageLocation
        platformManagedArtifactRef = event.platformManagedArtifactRef
        personalizedMetricsArtifactRef = event.personalizedMetricsArtifactRef
        personalizedAccuracy = event.personalizedAccuracy
        personalizationLift = event.personalizationLift
    }
}
