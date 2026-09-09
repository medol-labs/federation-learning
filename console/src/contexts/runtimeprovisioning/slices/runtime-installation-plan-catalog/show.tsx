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

export const RuntimeInstallationPlanCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_installation_plan_catalog_read_model_entity",
      idField: "runtimeInstallationPlanId",
      label: t("resources.runtime_installation_plan_catalog.label", "Runtime Installation Plan Catalog"),
      aggregateRoute: "runtimeinstallationplan",
      queryRoute: "runtimeinstallationplancatalog",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.runtimeInstallationPlanId ?? t("resources.runtime_installation_plan_catalog.label", "Runtime Installation Plan Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.runtimeInstallationPlanId.label", "Runtime Installation Plan Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInstallationPlanId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.organizationId.label", "Organization Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.organizationName.label", "Organization Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.runtimeInfrastructurePackageName.label", "Runtime Infrastructure Package Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.runtimeInfrastructurePackageVersion.label", "Runtime Infrastructure Package Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.runtimeName.label", "Runtime Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.agentInstallMode.label", "Agent Install Mode")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.agentInstallMode, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.expectedNodeCount.label", "Expected Node Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.expectedNodeCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.planStatus.label", "Plan Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.planStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructureId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.preparedAt.label", "Prepared At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.preparedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.preparedNodeCount.label", "Prepared Node Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.preparedNodeCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.observedNodeCount.label", "Observed Node Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.observedNodeCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.runtimeAgentVersion.label", "Runtime Agent Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.plannedAt.label", "Planned At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.plannedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.verifiedAt.label", "Verified At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.verifiedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.verificationFailedAt.label", "Verification Failed At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.verificationFailedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.verificationFailureReason.label", "Verification Failure Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.verificationFailureReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.agentReadyAt.label", "Agent Ready At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.agentReadyAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.agentDeploymentFailedAt.label", "Agent Deployment Failed At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.agentDeploymentFailedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.agentDeploymentFailureReason.label", "Agent Deployment Failure Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.agentDeploymentFailureReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.agentDeploymentRetryFailedAt.label", "Agent Deployment Retry Failed At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.agentDeploymentRetryFailedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.agentDeploymentRetryFailureReason.label", "Agent Deployment Retry Failure Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.agentDeploymentRetryFailureReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.lastConnectedAt.label", "Last Connected At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.lastConnectedAt, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
