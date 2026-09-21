// Generated from config.json by the refine generator.
import { useShow, useTranslate } from "@refinedev/core";

import { frontendComposition } from "@/app/composition/composition.resolved";
import { ShowView, ShowViewHeader } from "@/components/refine-ui/views/show-view";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Separator } from "@/components/ui/separator";
import { renderFieldOverride } from "@/platform/composition";

const formatValue = (value: unknown, t: ReturnType<typeof useTranslate>) => {
  if (value === null || value === undefined || value === "") return "-";
  if (typeof value === "boolean") return value ? t("values.boolean.true", "True") : t("values.boolean.false", "False");
  return String(value);
};

export const TrainingRoundProgressShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "training_round_progress_read_model_entity",
      idField: "trainingJobId",
      label: t("resources.training_round_progress.label", "Training Round Progress"),
      aggregateRoute: "traininground",
      queryRoute: "trainingroundprogress",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.trainingJobId ?? t("resources.training_round_progress.label", "Training Round Progress")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.trainingJobId.label", "Training Job Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:trainingJobId", { value: record?.trainingJobId, record, resource: "training-round-progress", field: "trainingJobId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:trainingRunConfigurationId", { value: record?.trainingRunConfigurationId, record, resource: "training-round-progress", field: "trainingRunConfigurationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingRunConfigurationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:featureSchemaId", { value: record?.featureSchemaId, record, resource: "training-round-progress", field: "featureSchemaId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.roundId.label", "Round Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:roundId", { value: record?.roundId, record, resource: "training-round-progress", field: "roundId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roundId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.trainingJobObjective.label", "Training Job Objective")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:trainingJobObjective", { value: record?.trainingJobObjective, record, resource: "training-round-progress", field: "trainingJobObjective", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobObjective, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.featureDomain.label", "Feature Domain")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:featureDomain", { value: record?.featureDomain, record, resource: "training-round-progress", field: "featureDomain", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.featureSchemaVersion.label", "Feature Schema Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:featureSchemaVersion", { value: record?.featureSchemaVersion, record, resource: "training-round-progress", field: "featureSchemaVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.roundNumber.label", "Round Number")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:roundNumber", { value: record?.roundNumber, record, resource: "training-round-progress", field: "roundNumber", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roundNumber, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:state", { value: record?.state, record, resource: "training-round-progress", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.selectedOrganizationIds.label", "Selected Organization Ids")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:selectedOrganizationIds", { value: record?.selectedOrganizationIds, record, resource: "training-round-progress", field: "selectedOrganizationIds", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.selectedOrganizationIds, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.selectedParticipants.label", "Selected Participants")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:selectedParticipants", { value: record?.selectedParticipants, record, resource: "training-round-progress", field: "selectedParticipants", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.selectedParticipants, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.selectedOrganizationCount.label", "Selected Organization Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:selectedOrganizationCount", { value: record?.selectedOrganizationCount, record, resource: "training-round-progress", field: "selectedOrganizationCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.selectedOrganizationCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.selectedRuntimeCount.label", "Selected Runtime Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:selectedRuntimeCount", { value: record?.selectedRuntimeCount, record, resource: "training-round-progress", field: "selectedRuntimeCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.selectedRuntimeCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.targetRuntimeCount.label", "Target Runtime Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:targetRuntimeCount", { value: record?.targetRuntimeCount, record, resource: "training-round-progress", field: "targetRuntimeCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.targetRuntimeCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.executionPlanDispatchedCount.label", "Execution Plan Dispatched Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:executionPlanDispatchedCount", { value: record?.executionPlanDispatchedCount, record, resource: "training-round-progress", field: "executionPlanDispatchedCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.executionPlanDispatchedCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.roundExecutionStartedCount.label", "Round Execution Started Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:roundExecutionStartedCount", { value: record?.roundExecutionStartedCount, record, resource: "training-round-progress", field: "roundExecutionStartedCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roundExecutionStartedCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.submittedModelUpdateCount.label", "Submitted Model Update Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:submittedModelUpdateCount", { value: record?.submittedModelUpdateCount, record, resource: "training-round-progress", field: "submittedModelUpdateCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.submittedModelUpdateCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.rejectedUpdateCount.label", "Rejected Update Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:rejectedUpdateCount", { value: record?.rejectedUpdateCount, record, resource: "training-round-progress", field: "rejectedUpdateCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.rejectedUpdateCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.acceptedModelUpdateCount.label", "Accepted Model Update Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:acceptedModelUpdateCount", { value: record?.acceptedModelUpdateCount, record, resource: "training-round-progress", field: "acceptedModelUpdateCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.acceptedModelUpdateCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.acceptedUpdateCount.label", "Accepted Update Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:acceptedUpdateCount", { value: record?.acceptedUpdateCount, record, resource: "training-round-progress", field: "acceptedUpdateCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.acceptedUpdateCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.pendingUpdateCount.label", "Pending Update Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:pendingUpdateCount", { value: record?.pendingUpdateCount, record, resource: "training-round-progress", field: "pendingUpdateCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.pendingUpdateCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.failedRoundExecutionCount.label", "Failed Round Execution Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:failedRoundExecutionCount", { value: record?.failedRoundExecutionCount, record, resource: "training-round-progress", field: "failedRoundExecutionCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.failedRoundExecutionCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.completedRoundExecutionCount.label", "Completed Round Execution Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:completedRoundExecutionCount", { value: record?.completedRoundExecutionCount, record, resource: "training-round-progress", field: "completedRoundExecutionCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.completedRoundExecutionCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.retriedRoundExecutionCount.label", "Retried Round Execution Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:retriedRoundExecutionCount", { value: record?.retriedRoundExecutionCount, record, resource: "training-round-progress", field: "retriedRoundExecutionCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.retriedRoundExecutionCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.failedRoundExecutionRetryCount.label", "Failed Round Execution Retry Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:failedRoundExecutionRetryCount", { value: record?.failedRoundExecutionRetryCount, record, resource: "training-round-progress", field: "failedRoundExecutionRetryCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.failedRoundExecutionRetryCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.quorumMet.label", "Quorum Met")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:quorumMet", { value: record?.quorumMet, record, resource: "training-round-progress", field: "quorumMet", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.quorumMet, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.quorumStatus.label", "Quorum Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:quorumStatus", { value: record?.quorumStatus, record, resource: "training-round-progress", field: "quorumStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.quorumStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.minimumNodesPerRound.label", "Minimum Nodes Per Round")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:minimumNodesPerRound", { value: record?.minimumNodesPerRound, record, resource: "training-round-progress", field: "minimumNodesPerRound", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.minimumNodesPerRound, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.aggregationReady.label", "Aggregation Ready")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:aggregationReady", { value: record?.aggregationReady, record, resource: "training-round-progress", field: "aggregationReady", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.aggregationReady, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.secureAggregationRequired.label", "Secure Aggregation Required")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:secureAggregationRequired", { value: record?.secureAggregationRequired, record, resource: "training-round-progress", field: "secureAggregationRequired", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.secureAggregationRequired, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.secureAggregationStatus.label", "Secure Aggregation Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:secureAggregationStatus", { value: record?.secureAggregationStatus, record, resource: "training-round-progress", field: "secureAggregationStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.secureAggregationStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.evaluationComplete.label", "Evaluation Complete")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:evaluationComplete", { value: record?.evaluationComplete, record, resource: "training-round-progress", field: "evaluationComplete", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.evaluationComplete, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.progressPercent.label", "Progress Percent")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:progressPercent", { value: record?.progressPercent, record, resource: "training-round-progress", field: "progressPercent", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.progressPercent, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.currentPhase.label", "Current Phase")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:currentPhase", { value: record?.currentPhase, record, resource: "training-round-progress", field: "currentPhase", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.currentPhase, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.nextAction.label", "Next Action")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:nextAction", { value: record?.nextAction, record, resource: "training-round-progress", field: "nextAction", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.nextAction, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.blockedReason.label", "Blocked Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:blockedReason", { value: record?.blockedReason, record, resource: "training-round-progress", field: "blockedReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.blockedReason, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.delayedReason.label", "Delayed Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:delayedReason", { value: record?.delayedReason, record, resource: "training-round-progress", field: "delayedReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.delayedReason, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.roundStartedAt.label", "Round Started At")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:roundStartedAt", { value: record?.roundStartedAt, record, resource: "training-round-progress", field: "roundStartedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roundStartedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.contributionDeadlineAt.label", "Contribution Deadline At")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:contributionDeadlineAt", { value: record?.contributionDeadlineAt, record, resource: "training-round-progress", field: "contributionDeadlineAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.contributionDeadlineAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.aggregationStartedAt.label", "Aggregation Started At")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:aggregationStartedAt", { value: record?.aggregationStartedAt, record, resource: "training-round-progress", field: "aggregationStartedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.aggregationStartedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.evaluationSubmittedAt.label", "Evaluation Submitted At")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:evaluationSubmittedAt", { value: record?.evaluationSubmittedAt, record, resource: "training-round-progress", field: "evaluationSubmittedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.evaluationSubmittedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.completedAt.label", "Completed At")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:completedAt", { value: record?.completedAt, record, resource: "training-round-progress", field: "completedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.completedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.failedAt.label", "Failed At")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:failedAt", { value: record?.failedAt, record, resource: "training-round-progress", field: "failedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.failedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.baseModelId.label", "Base Model Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:baseModelId", { value: record?.baseModelId, record, resource: "training-round-progress", field: "baseModelId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.baseModelId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.artifactRefs.label", "Artifact Refs")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:artifactRefs", { value: record?.artifactRefs, record, resource: "training-round-progress", field: "artifactRefs", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.artifactRefs, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.rejectedUpdateReasons.label", "Rejected Update Reasons")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:rejectedUpdateReasons", { value: record?.rejectedUpdateReasons, record, resource: "training-round-progress", field: "rejectedUpdateReasons", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.rejectedUpdateReasons, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.aggregatedModelId.label", "Aggregated Model Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:aggregatedModelId", { value: record?.aggregatedModelId, record, resource: "training-round-progress", field: "aggregatedModelId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.aggregatedModelId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.globalAccuracy.label", "Global Accuracy")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:globalAccuracy", { value: record?.globalAccuracy, record, resource: "training-round-progress", field: "globalAccuracy", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.globalAccuracy, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.globalFairnessScore.label", "Global Fairness Score")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:globalFairnessScore", { value: record?.globalFairnessScore, record, resource: "training-round-progress", field: "globalFairnessScore", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.globalFairnessScore, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.failureReason.label", "Failure Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-round-progress:display:failureReason", { value: record?.failureReason, record, resource: "training-round-progress", field: "failureReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.failureReason, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
