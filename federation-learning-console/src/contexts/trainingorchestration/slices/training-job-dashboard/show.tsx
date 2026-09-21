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
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:trainingJobId", { value: record?.trainingJobId, record, resource: "training-job-dashboard", field: "trainingJobId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.federationId.label", "Federation Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:federationId", { value: record?.federationId, record, resource: "training-job-dashboard", field: "federationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.federationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:trainingRunConfigurationId", { value: record?.trainingRunConfigurationId, record, resource: "training-job-dashboard", field: "trainingRunConfigurationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingRunConfigurationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:featureSchemaId", { value: record?.featureSchemaId, record, resource: "training-job-dashboard", field: "featureSchemaId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.federationName.label", "Federation Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:federationName", { value: record?.federationName, record, resource: "training-job-dashboard", field: "federationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.federationName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.featureDomain.label", "Feature Domain")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:featureDomain", { value: record?.featureDomain, record, resource: "training-job-dashboard", field: "featureDomain", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.featureSchemaVersion.label", "Feature Schema Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:featureSchemaVersion", { value: record?.featureSchemaVersion, record, resource: "training-job-dashboard", field: "featureSchemaVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.objective.label", "Objective")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:objective", { value: record?.objective, record, resource: "training-job-dashboard", field: "objective", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.objective, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.strategyName.label", "Strategy Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:strategyName", { value: record?.strategyName, record, resource: "training-job-dashboard", field: "strategyName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.strategyName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.aggregationAlgorithm.label", "Aggregation Algorithm")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:aggregationAlgorithm", { value: record?.aggregationAlgorithm, record, resource: "training-job-dashboard", field: "aggregationAlgorithm", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.aggregationAlgorithm, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.secureAggregationRequired.label", "Secure Aggregation Required")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:secureAggregationRequired", { value: record?.secureAggregationRequired, record, resource: "training-job-dashboard", field: "secureAggregationRequired", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.secureAggregationRequired, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:state", { value: record?.state, record, resource: "training-job-dashboard", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.workflowStage.label", "Workflow Stage")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:workflowStage", { value: record?.workflowStage, record, resource: "training-job-dashboard", field: "workflowStage", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.workflowStage, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.workflowStep.label", "Workflow Step")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:workflowStep", { value: record?.workflowStep, record, resource: "training-job-dashboard", field: "workflowStep", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.workflowStep, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.nextAction.label", "Next Action")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:nextAction", { value: record?.nextAction, record, resource: "training-job-dashboard", field: "nextAction", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.nextAction, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.availableActions.label", "Available Actions")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:availableActions", { value: record?.availableActions, record, resource: "training-job-dashboard", field: "availableActions", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.availableActions, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.blockedReason.label", "Blocked Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:blockedReason", { value: record?.blockedReason, record, resource: "training-job-dashboard", field: "blockedReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.blockedReason, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.canSubmit.label", "Can Submit")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:canSubmit", { value: record?.canSubmit, record, resource: "training-job-dashboard", field: "canSubmit", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.canSubmit, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.canStartRound.label", "Can Start Round")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:canStartRound", { value: record?.canStartRound, record, resource: "training-job-dashboard", field: "canStartRound", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.canStartRound, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.canPause.label", "Can Pause")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:canPause", { value: record?.canPause, record, resource: "training-job-dashboard", field: "canPause", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.canPause, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.canResume.label", "Can Resume")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:canResume", { value: record?.canResume, record, resource: "training-job-dashboard", field: "canResume", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.canResume, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.canCancel.label", "Can Cancel")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:canCancel", { value: record?.canCancel, record, resource: "training-job-dashboard", field: "canCancel", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.canCancel, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.canComplete.label", "Can Complete")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:canComplete", { value: record?.canComplete, record, resource: "training-job-dashboard", field: "canComplete", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.canComplete, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.currentRoundNumber.label", "Current Round Number")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:currentRoundNumber", { value: record?.currentRoundNumber, record, resource: "training-job-dashboard", field: "currentRoundNumber", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.currentRoundNumber, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.startedRuntimeCount.label", "Started Runtime Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:startedRuntimeCount", { value: record?.startedRuntimeCount, record, resource: "training-job-dashboard", field: "startedRuntimeCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.startedRuntimeCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.minimumNodesPerRound.label", "Minimum Nodes Per Round")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:minimumNodesPerRound", { value: record?.minimumNodesPerRound, record, resource: "training-job-dashboard", field: "minimumNodesPerRound", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.minimumNodesPerRound, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.maxRounds.label", "Max Rounds")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:maxRounds", { value: record?.maxRounds, record, resource: "training-job-dashboard", field: "maxRounds", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.maxRounds, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.roundProgressPercent.label", "Round Progress Percent")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:roundProgressPercent", { value: record?.roundProgressPercent, record, resource: "training-job-dashboard", field: "roundProgressPercent", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roundProgressPercent, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.globalAccuracy.label", "Global Accuracy")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:globalAccuracy", { value: record?.globalAccuracy, record, resource: "training-job-dashboard", field: "globalAccuracy", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.globalAccuracy, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.finalModelId.label", "Final Model Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:finalModelId", { value: record?.finalModelId, record, resource: "training-job-dashboard", field: "finalModelId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.finalModelId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_job_dashboard.fields.stopReason.label", "Stop Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-job-dashboard:display:stopReason", { value: record?.stopReason, record, resource: "training-job-dashboard", field: "stopReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.stopReason, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
