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

export const RuntimeInfrastructureAccessViewShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_infrastructure_access_view_read_model_entity",
      idField: "runtimeInfrastructureId",
      label: t("resources.runtime_infrastructure_access_view.label", "Runtime Infrastructure Access View"),
      aggregateRoute: "runtimeinfrastructure",
      queryRoute: "runtimeinfrastructureaccessview",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.runtimeInfrastructureId ?? t("resources.runtime_infrastructure_access_view.label", "Runtime Infrastructure Access View")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructureId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.organizationId.label", "Organization Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeInstallationPlanId.label", "Runtime Installation Plan Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInstallationPlanId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeInfrastructurePackageName.label", "Runtime Infrastructure Package Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeInfrastructurePackageVersion.label", "Runtime Infrastructure Package Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.organizationName.label", "Organization Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeName.label", "Runtime Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeDeploymentTargetType.label", "Runtime Deployment Target Type")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeDeploymentTargetType, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeEnvironmentType.label", "Runtime Environment Type")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEnvironmentType, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.agentInstallMode.label", "Agent Install Mode")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.agentInstallMode, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.expectedNodeCount.label", "Expected Node Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.expectedNodeCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeAgentVersion.label", "Runtime Agent Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.infrastructureVerifiedAt.label", "Infrastructure Verified At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.infrastructureVerifiedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.infrastructureVerificationFailedAt.label", "Infrastructure Verification Failed At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.infrastructureVerificationFailedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.infrastructureVerificationFailureReason.label", "Infrastructure Verification Failure Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.infrastructureVerificationFailureReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.agentReadyAt.label", "Agent Ready At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.agentReadyAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.agentDeploymentFailedAt.label", "Agent Deployment Failed At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.agentDeploymentFailedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.agentDeploymentFailureReason.label", "Agent Deployment Failure Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.agentDeploymentFailureReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.agentDeploymentRetryFailedAt.label", "Agent Deployment Retry Failed At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.agentDeploymentRetryFailedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.agentDeploymentRetryFailureReason.label", "Agent Deployment Retry Failure Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.agentDeploymentRetryFailureReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.connectedAt.label", "Connected At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.connectedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.state.label", "State")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
