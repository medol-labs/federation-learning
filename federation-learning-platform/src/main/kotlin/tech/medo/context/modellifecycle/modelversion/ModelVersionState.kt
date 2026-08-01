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
import tech.medo.modellifecycle.events.ModelPromotedToProductionEvent
import tech.medo.modellifecycle.events.ModelVersionRolledBackEvent
import tech.medo.modellifecycle.events.ModelVersionRetiredEvent
import tech.medo.modellifecycle.domain.states.ModelVersionStateEnum

import java.util.UUID;
import java.math.BigDecimal;


@EventSourced(idType = UUID::class, tagKey = ModelVersionTags.MODEL_VERSION_ID)
class ModelVersionState @EntityCreator constructor() {

    var currentState: ModelVersionStateEnum? = null
    var modelVersionId: UUID? = null
    var trainingJobId: UUID? = null
    var finalRoundId: UUID? = null
    var modelArtifactId: UUID? = null
    var modelHash: String? = null
    var evaluationReportId: UUID? = null
    var finalGlobalAccuracy: BigDecimal? = null
    var experimentId: UUID? = null
    var hyperparameterSnapshotId: UUID? = null
    var reproducibilityManifestId: UUID? = null
    var modelCardId: UUID? = null
    var baselineModelVersionId: UUID? = null
    var approvalNote: String? = null
    var releaseChannel: String? = null
    var productionStage: String? = null
    var previousModelVersionId: UUID? = null
    var rollbackReason: String? = null
    var retirementReason: String? = null

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
    fun evolve(event: ModelPromotedToProductionEvent): ModelVersionState = apply {
        currentState = ModelVersionStateEnum.PRODUCTION
        modelVersionId = event.modelVersionId
        releaseChannel = event.releaseChannel
        productionStage = event.productionStage
    }

    @EventSourcingHandler
    fun evolve(event: ModelVersionRolledBackEvent): ModelVersionState = apply {
        currentState = ModelVersionStateEnum.ROLLED_BACK
        modelVersionId = event.modelVersionId
        previousModelVersionId = event.previousModelVersionId
        rollbackReason = event.rollbackReason
    }

    @EventSourcingHandler
    fun evolve(event: ModelVersionRetiredEvent): ModelVersionState = apply {
        currentState = ModelVersionStateEnum.RETIRED
        modelVersionId = event.modelVersionId
        retirementReason = event.retirementReason
    }
}
