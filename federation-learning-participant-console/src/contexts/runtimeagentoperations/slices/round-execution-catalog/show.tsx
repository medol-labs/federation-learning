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

export const RoundExecutionCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "round_execution_catalog_read_model_entity",
      idField: "roundExecutionId",
      label: t("resources.round_execution_catalog.label", "Round Execution Catalog"),
      aggregateRoute: "roundexecution",
      queryRoute: "roundexecutioncatalog",
      dataProviderName: "federation-learning-runtime-agent",
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
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:roundExecutionId", { value: record?.roundExecutionId, record, resource: "round-execution-catalog", field: "roundExecutionId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roundExecutionId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.executionSessionId.label", "Execution Session Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:executionSessionId", { value: record?.executionSessionId, record, resource: "round-execution-catalog", field: "executionSessionId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.executionSessionId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.executionPlanId.label", "Execution Plan Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:executionPlanId", { value: record?.executionPlanId, record, resource: "round-execution-catalog", field: "executionPlanId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.executionPlanId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.trainingJobId.label", "Training Job Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:trainingJobId", { value: record?.trainingJobId, record, resource: "round-execution-catalog", field: "trainingJobId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:trainingRunConfigurationId", { value: record?.trainingRunConfigurationId, record, resource: "round-execution-catalog", field: "trainingRunConfigurationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingRunConfigurationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.roundId.label", "Round Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:roundId", { value: record?.roundId, record, resource: "round-execution-catalog", field: "roundId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roundId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.roundNumber.label", "Round Number")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:roundNumber", { value: record?.roundNumber, record, resource: "round-execution-catalog", field: "roundNumber", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roundNumber, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.organizationId.label", "Organization Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:organizationId", { value: record?.organizationId, record, resource: "round-execution-catalog", field: "organizationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeId.label", "Runtime Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:runtimeId", { value: record?.runtimeId, record, resource: "round-execution-catalog", field: "runtimeId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:state", { value: record?.state, record, resource: "round-execution-catalog", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:featureSchemaId", { value: record?.featureSchemaId, record, resource: "round-execution-catalog", field: "featureSchemaId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.baseModelId.label", "Base Model Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:baseModelId", { value: record?.baseModelId, record, resource: "round-execution-catalog", field: "baseModelId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.baseModelId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeEngineProfileId.label", "Runtime Engine Profile Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:runtimeEngineProfileId", { value: record?.runtimeEngineProfileId, record, resource: "round-execution-catalog", field: "runtimeEngineProfileId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineProfileId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeEngineProfileName.label", "Runtime Engine Profile Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:runtimeEngineProfileName", { value: record?.runtimeEngineProfileName, record, resource: "round-execution-catalog", field: "runtimeEngineProfileName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineProfileName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeEnginePluginProfile.label", "Runtime Engine Plugin Profile")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:runtimeEnginePluginProfile", { value: record?.runtimeEnginePluginProfile, record, resource: "round-execution-catalog", field: "runtimeEnginePluginProfile", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEnginePluginProfile, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeEngineImage.label", "Runtime Engine Image")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:runtimeEngineImage", { value: record?.runtimeEngineImage, record, resource: "round-execution-catalog", field: "runtimeEngineImage", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineImage, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeEngineImageDigest.label", "Runtime Engine Image Digest")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:runtimeEngineImageDigest", { value: record?.runtimeEngineImageDigest, record, resource: "round-execution-catalog", field: "runtimeEngineImageDigest", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineImageDigest, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeEngineJobId.label", "Runtime Engine Job Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:runtimeEngineJobId", { value: record?.runtimeEngineJobId, record, resource: "round-execution-catalog", field: "runtimeEngineJobId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineJobId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeEngineObservedStatus.label", "Runtime Engine Observed Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:runtimeEngineObservedStatus", { value: record?.runtimeEngineObservedStatus, record, resource: "round-execution-catalog", field: "runtimeEngineObservedStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineObservedStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeEngineObservationAt.label", "Runtime Engine Observation At")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:runtimeEngineObservationAt", { value: record?.runtimeEngineObservationAt, record, resource: "round-execution-catalog", field: "runtimeEngineObservationAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineObservationAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.localUpdateArtifactRef.label", "Local Update Artifact Ref")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:localUpdateArtifactRef", { value: record?.localUpdateArtifactRef, record, resource: "round-execution-catalog", field: "localUpdateArtifactRef", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.localUpdateArtifactRef, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.metricsArtifactRef.label", "Metrics Artifact Ref")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:metricsArtifactRef", { value: record?.metricsArtifactRef, record, resource: "round-execution-catalog", field: "metricsArtifactRef", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.metricsArtifactRef, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.localExecutionRequirementsSatisfied.label", "Local Execution Requirements Satisfied")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:localExecutionRequirementsSatisfied", { value: record?.localExecutionRequirementsSatisfied, record, resource: "round-execution-catalog", field: "localExecutionRequirementsSatisfied", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.localExecutionRequirementsSatisfied, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeIdentityMatched.label", "Runtime Identity Matched")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:runtimeIdentityMatched", { value: record?.runtimeIdentityMatched, record, resource: "round-execution-catalog", field: "runtimeIdentityMatched", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeIdentityMatched, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeDatasetBindingAvailable.label", "Runtime Dataset Binding Available")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:runtimeDatasetBindingAvailable", { value: record?.runtimeDatasetBindingAvailable, record, resource: "round-execution-catalog", field: "runtimeDatasetBindingAvailable", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeDatasetBindingAvailable, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.datasetAccessValidated.label", "Dataset Access Validated")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:datasetAccessValidated", { value: record?.datasetAccessValidated, record, resource: "round-execution-catalog", field: "datasetAccessValidated", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetAccessValidated, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.baseModelAvailable.label", "Base Model Available")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:baseModelAvailable", { value: record?.baseModelAvailable, record, resource: "round-execution-catalog", field: "baseModelAvailable", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.baseModelAvailable, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.trainingConfigurationSupported.label", "Training Configuration Supported")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:trainingConfigurationSupported", { value: record?.trainingConfigurationSupported, record, resource: "round-execution-catalog", field: "trainingConfigurationSupported", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingConfigurationSupported, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeResourceAvailable.label", "Runtime Resource Available")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:runtimeResourceAvailable", { value: record?.runtimeResourceAvailable, record, resource: "round-execution-catalog", field: "runtimeResourceAvailable", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeResourceAvailable, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeAgentIdle.label", "Runtime Agent Idle")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:runtimeAgentIdle", { value: record?.runtimeAgentIdle, record, resource: "round-execution-catalog", field: "runtimeAgentIdle", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentIdle, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.updateArtifactId.label", "Update Artifact Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:updateArtifactId", { value: record?.updateArtifactId, record, resource: "round-execution-catalog", field: "updateArtifactId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.updateArtifactId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.artifactRef.label", "Artifact Ref")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:artifactRef", { value: record?.artifactRef, record, resource: "round-execution-catalog", field: "artifactRef", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.artifactRef, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.artifactDigest.label", "Artifact Digest")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:artifactDigest", { value: record?.artifactDigest, record, resource: "round-execution-catalog", field: "artifactDigest", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.artifactDigest, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.trainingLoss.label", "Training Loss")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:trainingLoss", { value: record?.trainingLoss, record, resource: "round-execution-catalog", field: "trainingLoss", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingLoss, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.receivedAt.label", "Received At")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:receivedAt", { value: record?.receivedAt, record, resource: "round-execution-catalog", field: "receivedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.receivedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.acceptedAt.label", "Accepted At")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:acceptedAt", { value: record?.acceptedAt, record, resource: "round-execution-catalog", field: "acceptedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.acceptedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.rejectedAt.label", "Rejected At")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:rejectedAt", { value: record?.rejectedAt, record, resource: "round-execution-catalog", field: "rejectedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.rejectedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.startedAt.label", "Started At")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:startedAt", { value: record?.startedAt, record, resource: "round-execution-catalog", field: "startedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.startedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.completedAt.label", "Completed At")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:completedAt", { value: record?.completedAt, record, resource: "round-execution-catalog", field: "completedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.completedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.failedAt.label", "Failed At")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:failedAt", { value: record?.failedAt, record, resource: "round-execution-catalog", field: "failedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.failedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.submittedAt.label", "Submitted At")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:submittedAt", { value: record?.submittedAt, record, resource: "round-execution-catalog", field: "submittedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.submittedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.failureReason.label", "Failure Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:failureReason", { value: record?.failureReason, record, resource: "round-execution-catalog", field: "failureReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.failureReason, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.retryReason.label", "Retry Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:retryReason", { value: record?.retryReason, record, resource: "round-execution-catalog", field: "retryReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.retryReason, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeEngineReleased.label", "Runtime Engine Released")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:runtimeEngineReleased", { value: record?.runtimeEngineReleased, record, resource: "round-execution-catalog", field: "runtimeEngineReleased", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineReleased, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.runtimeEngineReleaseFailureReason.label", "Runtime Engine Release Failure Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:runtimeEngineReleaseFailureReason", { value: record?.runtimeEngineReleaseFailureReason, record, resource: "round-execution-catalog", field: "runtimeEngineReleaseFailureReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineReleaseFailureReason, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.round_execution_catalog.fields.rejectionReasons.label", "Rejection Reasons")}</h4>
              {renderFieldOverride(frontendComposition, "field:round-execution-catalog:display:rejectionReasons", { value: record?.rejectionReasons, record, resource: "round-execution-catalog", field: "rejectionReasons", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.rejectionReasons, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
