package tech.medo.modellifecycle.model

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
import tech.medo.modellifecycle.events.ModelRolledBackEvent
import tech.medo.modellifecycle.events.ModelRetiredEvent
import tech.medo.modellifecycle.domain.states.ModelStateEnum

import java.util.UUID;
import java.math.BigDecimal;


@EventSourced(idType = UUID::class, tagKey = ModelTags.MODEL_ID)
class ModelState @EntityCreator constructor() {

    var currentState: ModelStateEnum? = null
    var modelId: UUID? = null
    var trainingJobId: UUID? = null
    var trainingJobObjective: String? = null
    var finalRoundId: UUID? = null
    var modelArtifactId: UUID? = null
    var modelArtifactDigest: String? = null
    var evaluationReportId: UUID? = null
    var finalGlobalAccuracy: BigDecimal? = null
    var experimentId: UUID? = null
    var hyperparameterSnapshotId: UUID? = null
    var reproducibilityManifestId: UUID? = null
    var modelCardId: UUID? = null
    var baselineModelId: UUID? = null
    var approvalNote: String? = null
    var releaseChannel: String? = null
    var productionStage: String? = null
    var previousModelId: UUID? = null
    var rollbackReason: String? = null
    var retirementReason: String? = null

    @EventSourcingHandler
    fun evolve(event: ModelCandidateRegisteredEvent): ModelState = apply {
        currentState = ModelStateEnum.CANDIDATE
        modelId = event.modelId
        trainingJobId = event.trainingJobId
        trainingJobObjective = event.trainingJobObjective
        finalRoundId = event.finalRoundId
        modelArtifactId = event.modelArtifactId
        modelArtifactDigest = event.modelArtifactDigest
        evaluationReportId = event.evaluationReportId
        finalGlobalAccuracy = event.finalGlobalAccuracy
    }

    @EventSourcingHandler
    fun evolve(event: ModelEvaluationPackageRecordedEvent): ModelState = apply {
        currentState = ModelStateEnum.EVALUATION_PACKAGED
        modelId = event.modelId
        trainingJobId = event.trainingJobId
        evaluationReportId = event.evaluationReportId
        experimentId = event.experimentId
        hyperparameterSnapshotId = event.hyperparameterSnapshotId
        reproducibilityManifestId = event.reproducibilityManifestId
        modelCardId = event.modelCardId
        baselineModelId = event.baselineModelId
    }

    @EventSourcingHandler
    fun evolve(event: ModelApprovedEvent): ModelState = apply {
        currentState = ModelStateEnum.APPROVED
        modelId = event.modelId
        approvalNote = event.approvalNote
    }

    @EventSourcingHandler
    fun evolve(event: ModelPromotedToProductionEvent): ModelState = apply {
        currentState = ModelStateEnum.PRODUCTION
        modelId = event.modelId
        releaseChannel = event.releaseChannel
        productionStage = event.productionStage
    }

    @EventSourcingHandler
    fun evolve(event: ModelRolledBackEvent): ModelState = apply {
        currentState = ModelStateEnum.ROLLED_BACK
        modelId = event.modelId
        previousModelId = event.previousModelId
        rollbackReason = event.rollbackReason
    }

    @EventSourcingHandler
    fun evolve(event: ModelRetiredEvent): ModelState = apply {
        currentState = ModelStateEnum.RETIRED
        modelId = event.modelId
        retirementReason = event.retirementReason
    }
}
