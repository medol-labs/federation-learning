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

export const RoundExecutionCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "flruntime-agent",
    meta: {
      tableName: "round_execution_catalog_read_model_entity",
      idField: "roundExecutionId",
      label: t("resources.round_execution_catalog.label", "Round Execution Catalog"),
      aggregateRoute: "roundexecution",
      queryRoute: "roundexecutioncatalog",
      dataProviderName: "flruntime-agent",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.roundExecutionId ?? t("resources.round_execution_catalog.label", "Round Execution Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.roundExecutionId.label", "Round Execution Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roundExecutionId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.executionSessionId.label", "Execution Session Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.executionSessionId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.executionPlanId.label", "Execution Plan Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.executionPlanId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.trainingJobId.label", "Training Job Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingRunConfigurationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.roundId.label", "Round Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roundId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.roundNumber.label", "Round Number")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roundNumber, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.organizationId.label", "Organization Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeId.label", "Runtime Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.state.label", "State")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.baseModelVersionId.label", "Base Model Version Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.baseModelVersionId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeEngineJobId.label", "Runtime Engine Job Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineJobId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.localExecutionRequirementsSatisfied.label", "Local Execution Requirements Satisfied")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.localExecutionRequirementsSatisfied, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeIdentityMatched.label", "Runtime Identity Matched")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeIdentityMatched, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeDatasetBindingAvailable.label", "Runtime Dataset Binding Available")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeDatasetBindingAvailable, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.datasetAccessValidated.label", "Dataset Access Validated")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetAccessValidated, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.baseModelAvailable.label", "Base Model Available")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.baseModelAvailable, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.trainingConfigurationSupported.label", "Training Configuration Supported")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingConfigurationSupported, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeResourceAvailable.label", "Runtime Resource Available")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeResourceAvailable, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeAgentIdle.label", "Runtime Agent Idle")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentIdle, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.updateArtifactId.label", "Update Artifact Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.updateArtifactId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.artifactRef.label", "Artifact Ref")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.artifactRef, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.artifactDigest.label", "Artifact Digest")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.artifactDigest, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.trainingLoss.label", "Training Loss")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingLoss, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.receivedAt.label", "Received At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.receivedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.acceptedAt.label", "Accepted At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.acceptedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.rejectedAt.label", "Rejected At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.rejectedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.startedAt.label", "Started At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.startedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.completedAt.label", "Completed At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.completedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.failedAt.label", "Failed At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.failedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.submittedAt.label", "Submitted At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.submittedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.failureReason.label", "Failure Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.failureReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.retryReason.label", "Retry Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.retryReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeEngineReleased.label", "Runtime Engine Released")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineReleased, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeEngineReleaseFailureReason.label", "Runtime Engine Release Failure Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineReleaseFailureReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.rejectionReasons.label", "Rejection Reasons")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.rejectionReasons, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
