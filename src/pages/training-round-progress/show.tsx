// Generated from config.json by the refine generator.
import { useShow, useTranslate } from "@refinedev/core";

import { ShowView, ShowViewHeader } from "@/components/refine-ui/views/show-view";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Separator } from "@/components/ui/separator";

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
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingRunConfigurationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.roundId.label", "Round Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roundId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.trainingJobObjective.label", "Training Job Objective")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobObjective, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.featureDomain.label", "Feature Domain")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.featureSchemaVersion.label", "Feature Schema Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.roundNumber.label", "Round Number")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roundNumber, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.state.label", "State")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.selectedOrganizationIds.label", "Selected Organization Ids")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.selectedOrganizationIds, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.selectedParticipants.label", "Selected Participants")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.selectedParticipants, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.selectedOrganizationCount.label", "Selected Organization Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.selectedOrganizationCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.selectedRuntimeCount.label", "Selected Runtime Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.selectedRuntimeCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.targetRuntimeCount.label", "Target Runtime Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.targetRuntimeCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.executionPlanDispatchedCount.label", "Execution Plan Dispatched Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.executionPlanDispatchedCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.roundExecutionStartedCount.label", "Round Execution Started Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roundExecutionStartedCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.submittedModelUpdateCount.label", "Submitted Model Update Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.submittedModelUpdateCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.rejectedUpdateCount.label", "Rejected Update Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.rejectedUpdateCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.acceptedModelUpdateCount.label", "Accepted Model Update Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.acceptedModelUpdateCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.acceptedUpdateCount.label", "Accepted Update Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.acceptedUpdateCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.pendingUpdateCount.label", "Pending Update Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.pendingUpdateCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.failedRoundExecutionCount.label", "Failed Round Execution Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.failedRoundExecutionCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.completedRoundExecutionCount.label", "Completed Round Execution Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.completedRoundExecutionCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.retriedRoundExecutionCount.label", "Retried Round Execution Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.retriedRoundExecutionCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.failedRoundExecutionRetryCount.label", "Failed Round Execution Retry Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.failedRoundExecutionRetryCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.quorumMet.label", "Quorum Met")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.quorumMet, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.quorumStatus.label", "Quorum Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.quorumStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.minimumNodesPerRound.label", "Minimum Nodes Per Round")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.minimumNodesPerRound, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.aggregationReady.label", "Aggregation Ready")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.aggregationReady, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.secureAggregationRequired.label", "Secure Aggregation Required")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.secureAggregationRequired, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.secureAggregationStatus.label", "Secure Aggregation Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.secureAggregationStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.evaluationComplete.label", "Evaluation Complete")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.evaluationComplete, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.progressPercent.label", "Progress Percent")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.progressPercent, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.currentPhase.label", "Current Phase")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.currentPhase, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.nextAction.label", "Next Action")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.nextAction, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.blockedReason.label", "Blocked Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.blockedReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.delayedReason.label", "Delayed Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.delayedReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.roundStartedAt.label", "Round Started At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roundStartedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.contributionDeadlineAt.label", "Contribution Deadline At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.contributionDeadlineAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.aggregationStartedAt.label", "Aggregation Started At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.aggregationStartedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.evaluationSubmittedAt.label", "Evaluation Submitted At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.evaluationSubmittedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.completedAt.label", "Completed At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.completedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.failedAt.label", "Failed At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.failedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.baseModelId.label", "Base Model Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.baseModelId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.artifactRefs.label", "Artifact Refs")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.artifactRefs, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.rejectedUpdateReasons.label", "Rejected Update Reasons")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.rejectedUpdateReasons, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.aggregatedModelId.label", "Aggregated Model Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.aggregatedModelId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.globalAccuracy.label", "Global Accuracy")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.globalAccuracy, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.globalFairnessScore.label", "Global Fairness Score")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.globalFairnessScore, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_round_progress.fields.failureReason.label", "Failure Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.failureReason, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
