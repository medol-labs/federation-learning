// Generated from config.json by the refine generator.
import { useShow, useTranslate } from "@refinedev/core";

import { frontendComposition } from "@/app/composition/composition.resolved";
import { ShowView, ShowViewHeader } from "@/components/refine-ui/views/show-view";
import { CopyableText } from "@/components/refine-ui/fields/copyable-text";
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
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:runtimeInstallationPlanId", { value: record?.runtimeInstallationPlanId, record, resource: "runtime-installation-plan-catalog", field: "runtimeInstallationPlanId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInstallationPlanId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.organizationId.label", "Organization Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:organizationId", { value: record?.organizationId, record, resource: "runtime-installation-plan-catalog", field: "organizationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.organizationName.label", "Organization Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:organizationName", { value: record?.organizationName, record, resource: "runtime-installation-plan-catalog", field: "organizationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:runtimeInfrastructurePackageId", { value: record?.runtimeInfrastructurePackageId, record, resource: "runtime-installation-plan-catalog", field: "runtimeInfrastructurePackageId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.runtimeInfrastructurePackageName.label", "Runtime Infrastructure Package Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:runtimeInfrastructurePackageName", { value: record?.runtimeInfrastructurePackageName, record, resource: "runtime-installation-plan-catalog", field: "runtimeInfrastructurePackageName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.runtimeInfrastructurePackageVersion.label", "Runtime Infrastructure Package Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:runtimeInfrastructurePackageVersion", { value: record?.runtimeInfrastructurePackageVersion, record, resource: "runtime-installation-plan-catalog", field: "runtimeInfrastructurePackageVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.runtimeName.label", "Runtime Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:runtimeName", { value: record?.runtimeName, record, resource: "runtime-installation-plan-catalog", field: "runtimeName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.agentInstallMode.label", "Agent Install Mode")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:agentInstallMode", { value: record?.agentInstallMode, record, resource: "runtime-installation-plan-catalog", field: "agentInstallMode", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.agentInstallMode, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.expectedNodeCount.label", "Expected Node Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:expectedNodeCount", { value: record?.expectedNodeCount, record, resource: "runtime-installation-plan-catalog", field: "expectedNodeCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.expectedNodeCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.planStatus.label", "Plan Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:planStatus", { value: record?.planStatus, record, resource: "runtime-installation-plan-catalog", field: "planStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.planStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:runtimeInfrastructureId", { value: record?.runtimeInfrastructureId, record, resource: "runtime-installation-plan-catalog", field: "runtimeInfrastructureId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructureId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.preparedAt.label", "Prepared At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:preparedAt", { value: record?.preparedAt, record, resource: "runtime-installation-plan-catalog", field: "preparedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.preparedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.preparedNodeCount.label", "Prepared Node Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:preparedNodeCount", { value: record?.preparedNodeCount, record, resource: "runtime-installation-plan-catalog", field: "preparedNodeCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.preparedNodeCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.observedNodeCount.label", "Observed Node Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:observedNodeCount", { value: record?.observedNodeCount, record, resource: "runtime-installation-plan-catalog", field: "observedNodeCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.observedNodeCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:runtimeAgentId", { value: record?.runtimeAgentId, record, resource: "runtime-installation-plan-catalog", field: "runtimeAgentId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.runtimeAgentVersion.label", "Runtime Agent Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:runtimeAgentVersion", { value: record?.runtimeAgentVersion, record, resource: "runtime-installation-plan-catalog", field: "runtimeAgentVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.plannedAt.label", "Planned At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:plannedAt", { value: record?.plannedAt, record, resource: "runtime-installation-plan-catalog", field: "plannedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.plannedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.verifiedAt.label", "Verified At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:verifiedAt", { value: record?.verifiedAt, record, resource: "runtime-installation-plan-catalog", field: "verifiedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.verifiedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.verificationFailedAt.label", "Verification Failed At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:verificationFailedAt", { value: record?.verificationFailedAt, record, resource: "runtime-installation-plan-catalog", field: "verificationFailedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.verificationFailedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.verificationFailureReason.label", "Verification Failure Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:verificationFailureReason", { value: record?.verificationFailureReason, record, resource: "runtime-installation-plan-catalog", field: "verificationFailureReason", view: "display" }) ?? <CopyableText value={record?.verificationFailureReason} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.agentReadyAt.label", "Agent Ready At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:agentReadyAt", { value: record?.agentReadyAt, record, resource: "runtime-installation-plan-catalog", field: "agentReadyAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.agentReadyAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.agentDeploymentFailedAt.label", "Agent Deployment Failed At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:agentDeploymentFailedAt", { value: record?.agentDeploymentFailedAt, record, resource: "runtime-installation-plan-catalog", field: "agentDeploymentFailedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.agentDeploymentFailedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.agentDeploymentFailureReason.label", "Agent Deployment Failure Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:agentDeploymentFailureReason", { value: record?.agentDeploymentFailureReason, record, resource: "runtime-installation-plan-catalog", field: "agentDeploymentFailureReason", view: "display" }) ?? <CopyableText value={record?.agentDeploymentFailureReason} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.agentDeploymentRetryFailedAt.label", "Agent Deployment Retry Failed At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:agentDeploymentRetryFailedAt", { value: record?.agentDeploymentRetryFailedAt, record, resource: "runtime-installation-plan-catalog", field: "agentDeploymentRetryFailedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.agentDeploymentRetryFailedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.agentDeploymentRetryFailureReason.label", "Agent Deployment Retry Failure Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:agentDeploymentRetryFailureReason", { value: record?.agentDeploymentRetryFailureReason, record, resource: "runtime-installation-plan-catalog", field: "agentDeploymentRetryFailureReason", view: "display" }) ?? <CopyableText value={record?.agentDeploymentRetryFailureReason} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_plan_catalog.fields.lastConnectedAt.label", "Last Connected At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-plan-catalog:display:lastConnectedAt", { value: record?.lastConnectedAt, record, resource: "runtime-installation-plan-catalog", field: "lastConnectedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.lastConnectedAt, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
