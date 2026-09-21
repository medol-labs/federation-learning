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
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:runtimeInfrastructureId", { value: record?.runtimeInfrastructureId, record, resource: "runtime-infrastructure-access-view", field: "runtimeInfrastructureId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructureId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.organizationId.label", "Organization Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:organizationId", { value: record?.organizationId, record, resource: "runtime-infrastructure-access-view", field: "organizationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeInstallationPlanId.label", "Runtime Installation Plan Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:runtimeInstallationPlanId", { value: record?.runtimeInstallationPlanId, record, resource: "runtime-infrastructure-access-view", field: "runtimeInstallationPlanId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInstallationPlanId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:runtimeInfrastructurePackageId", { value: record?.runtimeInfrastructurePackageId, record, resource: "runtime-infrastructure-access-view", field: "runtimeInfrastructurePackageId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeInfrastructurePackageName.label", "Runtime Infrastructure Package Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:runtimeInfrastructurePackageName", { value: record?.runtimeInfrastructurePackageName, record, resource: "runtime-infrastructure-access-view", field: "runtimeInfrastructurePackageName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeInfrastructurePackageVersion.label", "Runtime Infrastructure Package Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:runtimeInfrastructurePackageVersion", { value: record?.runtimeInfrastructurePackageVersion, record, resource: "runtime-infrastructure-access-view", field: "runtimeInfrastructurePackageVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.organizationName.label", "Organization Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:organizationName", { value: record?.organizationName, record, resource: "runtime-infrastructure-access-view", field: "organizationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeName.label", "Runtime Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:runtimeName", { value: record?.runtimeName, record, resource: "runtime-infrastructure-access-view", field: "runtimeName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeEnvironmentType.label", "Runtime Environment Type")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:runtimeEnvironmentType", { value: record?.runtimeEnvironmentType, record, resource: "runtime-infrastructure-access-view", field: "runtimeEnvironmentType", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEnvironmentType, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.agentInstallMode.label", "Agent Install Mode")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:agentInstallMode", { value: record?.agentInstallMode, record, resource: "runtime-infrastructure-access-view", field: "agentInstallMode", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.agentInstallMode, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.expectedNodeCount.label", "Expected Node Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:expectedNodeCount", { value: record?.expectedNodeCount, record, resource: "runtime-infrastructure-access-view", field: "expectedNodeCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.expectedNodeCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:runtimeAgentId", { value: record?.runtimeAgentId, record, resource: "runtime-infrastructure-access-view", field: "runtimeAgentId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeAgentVersion.label", "Runtime Agent Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:runtimeAgentVersion", { value: record?.runtimeAgentVersion, record, resource: "runtime-infrastructure-access-view", field: "runtimeAgentVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.infrastructurePreparedAt.label", "Infrastructure Prepared At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:infrastructurePreparedAt", { value: record?.infrastructurePreparedAt, record, resource: "runtime-infrastructure-access-view", field: "infrastructurePreparedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.infrastructurePreparedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.preparedNodeCount.label", "Prepared Node Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:preparedNodeCount", { value: record?.preparedNodeCount, record, resource: "runtime-infrastructure-access-view", field: "preparedNodeCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.preparedNodeCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.infrastructureVerifiedAt.label", "Infrastructure Verified At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:infrastructureVerifiedAt", { value: record?.infrastructureVerifiedAt, record, resource: "runtime-infrastructure-access-view", field: "infrastructureVerifiedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.infrastructureVerifiedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.infrastructureVerificationFailedAt.label", "Infrastructure Verification Failed At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:infrastructureVerificationFailedAt", { value: record?.infrastructureVerificationFailedAt, record, resource: "runtime-infrastructure-access-view", field: "infrastructureVerificationFailedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.infrastructureVerificationFailedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.infrastructureVerificationFailureReason.label", "Infrastructure Verification Failure Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:infrastructureVerificationFailureReason", { value: record?.infrastructureVerificationFailureReason, record, resource: "runtime-infrastructure-access-view", field: "infrastructureVerificationFailureReason", view: "display" }) ?? <CopyableText value={record?.infrastructureVerificationFailureReason} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.agentReadyAt.label", "Agent Ready At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:agentReadyAt", { value: record?.agentReadyAt, record, resource: "runtime-infrastructure-access-view", field: "agentReadyAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.agentReadyAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.agentDeploymentFailedAt.label", "Agent Deployment Failed At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:agentDeploymentFailedAt", { value: record?.agentDeploymentFailedAt, record, resource: "runtime-infrastructure-access-view", field: "agentDeploymentFailedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.agentDeploymentFailedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.agentDeploymentFailureReason.label", "Agent Deployment Failure Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:agentDeploymentFailureReason", { value: record?.agentDeploymentFailureReason, record, resource: "runtime-infrastructure-access-view", field: "agentDeploymentFailureReason", view: "display" }) ?? <CopyableText value={record?.agentDeploymentFailureReason} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.agentDeploymentRetryFailedAt.label", "Agent Deployment Retry Failed At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:agentDeploymentRetryFailedAt", { value: record?.agentDeploymentRetryFailedAt, record, resource: "runtime-infrastructure-access-view", field: "agentDeploymentRetryFailedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.agentDeploymentRetryFailedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.agentDeploymentRetryFailureReason.label", "Agent Deployment Retry Failure Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:agentDeploymentRetryFailureReason", { value: record?.agentDeploymentRetryFailureReason, record, resource: "runtime-infrastructure-access-view", field: "agentDeploymentRetryFailureReason", view: "display" }) ?? <CopyableText value={record?.agentDeploymentRetryFailureReason} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.connectedAt.label", "Connected At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:connectedAt", { value: record?.connectedAt, record, resource: "runtime-infrastructure-access-view", field: "connectedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.connectedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:state", { value: record?.state, record, resource: "runtime-infrastructure-access-view", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
