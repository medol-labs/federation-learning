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

export const TrainingJobDashboardShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "training_job_dashboard_read_model_entity",
      idField: "trainingJobId",
      label: t("resources.training_job_dashboard.label", "Training Job Dashboard"),
      aggregateRoute: "trainingjob",
      queryRoute: "trainingjobdashboard",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.trainingJobId ?? t("resources.training_job_dashboard.label", "Training Job Dashboard")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.trainingJobId.label", "Training Job Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.federationId.label", "Federation Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.federationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingRunConfigurationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.federationName.label", "Federation Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.federationName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.featureDomain.label", "Feature Domain")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.featureSchemaVersion.label", "Feature Schema Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.objective.label", "Objective")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.objective, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.strategyName.label", "Strategy Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.strategyName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.aggregationAlgorithm.label", "Aggregation Algorithm")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.aggregationAlgorithm, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.secureAggregationRequired.label", "Secure Aggregation Required")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.secureAggregationRequired, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.state.label", "State")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.workflowStage.label", "Workflow Stage")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.workflowStage, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.workflowStep.label", "Workflow Step")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.workflowStep, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.nextAction.label", "Next Action")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.nextAction, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.availableActions.label", "Available Actions")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.availableActions, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.blockedReason.label", "Blocked Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.blockedReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.canSubmit.label", "Can Submit")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.canSubmit, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.canStartRound.label", "Can Start Round")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.canStartRound, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.canPause.label", "Can Pause")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.canPause, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.canResume.label", "Can Resume")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.canResume, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.canCancel.label", "Can Cancel")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.canCancel, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.canComplete.label", "Can Complete")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.canComplete, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.currentRoundNumber.label", "Current Round Number")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.currentRoundNumber, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.startedRuntimeCount.label", "Started Runtime Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.startedRuntimeCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.minimumNodesPerRound.label", "Minimum Nodes Per Round")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.minimumNodesPerRound, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.maxRounds.label", "Max Rounds")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.maxRounds, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.roundProgressPercent.label", "Round Progress Percent")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roundProgressPercent, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.globalAccuracy.label", "Global Accuracy")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.globalAccuracy, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.finalModelId.label", "Final Model Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.finalModelId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.stopReason.label", "Stop Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.stopReason, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
