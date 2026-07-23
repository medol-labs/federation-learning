package tech.medo.modellifecycle.modelversion

import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder
import org.axonframework.eventsourcing.annotation.EventSourcingHandler
import org.axonframework.eventsourcing.annotation.reflection.EntityCreator
import org.axonframework.extension.spring.stereotype.EventSourced
import org.axonframework.messaging.eventstreaming.EventCriteria
import org.axonframework.messaging.eventstreaming.Tag
import tech.medo.modellifecycle.events.ModelCandidateRegisteredEvent
import tech.medo.modellifecycle.events.ModelEvaluationPackageRecordedEvent
import tech.medo.modellifecycle.events.ModelApprovedEvent
import tech.medo.modellifecycle.events.ModelVersionRolledBackEvent
import tech.medo.modellifecycle.domain.states.ModelVersionStateEnum

import java.util.UUID;
import java.math.BigDecimal;


@EventSourced(idType = UUID::class, tagKey = ModelVersionTags.MODEL_VERSION_ID)
class ModelVersionState @EntityCreator constructor() {

    var currentState: ModelVersionStateEnum? = null
    private var modelVersionId: UUID? = null
    private var trainingJobId: UUID? = null
    private var finalRoundId: UUID? = null
    private var modelArtifactId: UUID? = null
    private var modelHash: String? = null
    private var evaluationReportId: UUID? = null
    private var finalGlobalAccuracy: BigDecimal? = null
    private var experimentId: UUID? = null
    private var hyperparameterSnapshotId: UUID? = null
    private var reproducibilityManifestId: UUID? = null
    private var modelCardId: UUID? = null
    private var baselineModelVersionId: UUID? = null
    private var approvalNote: String? = null
    private var previousModelVersionId: UUID? = null
    private var rollbackReason: String? = null

    @EventSourcingHandler
    fun evolve(event: ModelCandidateRegisteredEvent): ModelVersionState = apply {
        currentState = ModelVersionStateEnum.CANDIDATE
        modelVersionId = event.modelVersionId
        trainingJobId = event.trainingJobId
        finalRoundId = event.finalRoundId
        modelArtifactId = event.modelArtifactId
        modelHash = event.modelHash
        evaluationReportId = event.evaluationReportId
        finalGlobalAccuracy = event.finalGlobalAccuracy
    }

    @EventSourcingHandler
    fun evolve(event: ModelEvaluationPackageRecordedEvent): ModelVersionState = apply {
        currentState = ModelVersionStateEnum.EVALUATION_PACKAGED
        modelVersionId = event.modelVersionId
        trainingJobId = event.trainingJobId
        evaluationReportId = event.evaluationReportId
        experimentId = event.experimentId
        hyperparameterSnapshotId = event.hyperparameterSnapshotId
        reproducibilityManifestId = event.reproducibilityManifestId
        modelCardId = event.modelCardId
        baselineModelVersionId = event.baselineModelVersionId
    }

    @EventSourcingHandler
    fun evolve(event: ModelApprovedEvent): ModelVersionState = apply {
        currentState = ModelVersionStateEnum.APPROVED
        modelVersionId = event.modelVersionId
        approvalNote = event.approvalNote
    }

    @EventSourcingHandler
    fun evolve(event: ModelVersionRolledBackEvent): ModelVersionState = apply {
        currentState = ModelVersionStateEnum.ROLLED_BACK
        modelVersionId = event.modelVersionId
        previousModelVersionId = event.previousModelVersionId
        rollbackReason = event.rollbackReason
    }
}
