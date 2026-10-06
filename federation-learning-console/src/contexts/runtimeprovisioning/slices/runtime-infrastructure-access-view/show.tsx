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
import { useDictionaryTranslation } from "@/lib/dictionary-i18n";
import { Separator } from "@/components/ui/separator";
import { renderFieldOverride } from "@/platform/composition";

const formatValue = (
  value: unknown,
  t: ReturnType<typeof useTranslate>,
  dictionaryLabel: ReturnType<typeof useDictionaryTranslation>["dictionaryLabel"],
  options?: Array<{ label: string; value: string }>,
  dictionaryCode?: string,
): string => {
  if (value === null || value === undefined || value === "") return "-";
  if (Array.isArray(value)) {
    const formatted: string[] = value.map((item) => formatValue(item, t, dictionaryLabel, options, dictionaryCode)).filter((item) => item !== "-");
    return formatted.length > 0 ? formatted.join(", ") : "-";
  }
  if (typeof value === "boolean") return value ? t("values.boolean.true", "True") : t("values.boolean.false", "False");
  const stringValue = String(value);
  if (dictionaryCode) return dictionaryLabel(dictionaryCode, stringValue, t(`dictionaries.${dictionaryCode}.${stringValue}`, stringValue));
  return options?.find((option) => option.value === stringValue)?.label ?? stringValue;
};

export const RuntimeInfrastructureAccessViewShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
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
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:runtimeInfrastructureId", { value: record?.runtimeInfrastructureId, record, resource: "runtime-infrastructure-access-view", field: "runtimeInfrastructureId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructureId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.organizationId.label", "Organization Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:organizationId", { value: record?.organizationId, record, resource: "runtime-infrastructure-access-view", field: "organizationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeInstallationPlanId.label", "Runtime Installation Plan Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:runtimeInstallationPlanId", { value: record?.runtimeInstallationPlanId, record, resource: "runtime-infrastructure-access-view", field: "runtimeInstallationPlanId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInstallationPlanId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:runtimeInfrastructurePackageId", { value: record?.runtimeInfrastructurePackageId, record, resource: "runtime-infrastructure-access-view", field: "runtimeInfrastructurePackageId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeInfrastructurePackageName.label", "Runtime Infrastructure Package Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:runtimeInfrastructurePackageName", { value: record?.runtimeInfrastructurePackageName, record, resource: "runtime-infrastructure-access-view", field: "runtimeInfrastructurePackageName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeInfrastructurePackageVersion.label", "Runtime Infrastructure Package Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:runtimeInfrastructurePackageVersion", { value: record?.runtimeInfrastructurePackageVersion, record, resource: "runtime-infrastructure-access-view", field: "runtimeInfrastructurePackageVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageVersion, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.organizationName.label", "Organization Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:organizationName", { value: record?.organizationName, record, resource: "runtime-infrastructure-access-view", field: "organizationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeName.label", "Runtime Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:runtimeName", { value: record?.runtimeName, record, resource: "runtime-infrastructure-access-view", field: "runtimeName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeEnvironmentType.label", "Runtime Environment Type")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:runtimeEnvironmentType", { value: record?.runtimeEnvironmentType, record, resource: "runtime-infrastructure-access-view", field: "runtimeEnvironmentType", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEnvironmentType, t, dictionaryLabel, undefined, "RUNTIME_ENVIRONMENT_TYPE")}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.agentInstallMode.label", "Agent Install Mode")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:agentInstallMode", { value: record?.agentInstallMode, record, resource: "runtime-infrastructure-access-view", field: "agentInstallMode", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.agentInstallMode, t, dictionaryLabel, undefined, "RUNTIME_AGENT_INSTALL_MODE")}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.expectedNodeCount.label", "Expected Node Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:expectedNodeCount", { value: record?.expectedNodeCount, record, resource: "runtime-infrastructure-access-view", field: "expectedNodeCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.expectedNodeCount, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:runtimeAgentId", { value: record?.runtimeAgentId, record, resource: "runtime-infrastructure-access-view", field: "runtimeAgentId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.runtimeAgentVersion.label", "Runtime Agent Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:runtimeAgentVersion", { value: record?.runtimeAgentVersion, record, resource: "runtime-infrastructure-access-view", field: "runtimeAgentVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentVersion, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.infrastructurePreparedAt.label", "Infrastructure Prepared At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:infrastructurePreparedAt", { value: record?.infrastructurePreparedAt, record, resource: "runtime-infrastructure-access-view", field: "infrastructurePreparedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.infrastructurePreparedAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.preparedNodeCount.label", "Prepared Node Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:preparedNodeCount", { value: record?.preparedNodeCount, record, resource: "runtime-infrastructure-access-view", field: "preparedNodeCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.preparedNodeCount, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.infrastructureVerifiedAt.label", "Infrastructure Verified At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:infrastructureVerifiedAt", { value: record?.infrastructureVerifiedAt, record, resource: "runtime-infrastructure-access-view", field: "infrastructureVerifiedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.infrastructureVerifiedAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.infrastructureVerificationFailedAt.label", "Infrastructure Verification Failed At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:infrastructureVerificationFailedAt", { value: record?.infrastructureVerificationFailedAt, record, resource: "runtime-infrastructure-access-view", field: "infrastructureVerificationFailedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.infrastructureVerificationFailedAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.infrastructureVerificationFailureReason.label", "Infrastructure Verification Failure Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:infrastructureVerificationFailureReason", { value: record?.infrastructureVerificationFailureReason, record, resource: "runtime-infrastructure-access-view", field: "infrastructureVerificationFailureReason", view: "display" }) ?? <CopyableText value={record?.infrastructureVerificationFailureReason} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.agentReadyAt.label", "Agent Ready At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:agentReadyAt", { value: record?.agentReadyAt, record, resource: "runtime-infrastructure-access-view", field: "agentReadyAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.agentReadyAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.agentDeploymentFailedAt.label", "Agent Deployment Failed At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:agentDeploymentFailedAt", { value: record?.agentDeploymentFailedAt, record, resource: "runtime-infrastructure-access-view", field: "agentDeploymentFailedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.agentDeploymentFailedAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.agentDeploymentFailureReason.label", "Agent Deployment Failure Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:agentDeploymentFailureReason", { value: record?.agentDeploymentFailureReason, record, resource: "runtime-infrastructure-access-view", field: "agentDeploymentFailureReason", view: "display" }) ?? <CopyableText value={record?.agentDeploymentFailureReason} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.agentDeploymentRetryFailedAt.label", "Agent Deployment Retry Failed At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:agentDeploymentRetryFailedAt", { value: record?.agentDeploymentRetryFailedAt, record, resource: "runtime-infrastructure-access-view", field: "agentDeploymentRetryFailedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.agentDeploymentRetryFailedAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.agentDeploymentRetryFailureReason.label", "Agent Deployment Retry Failure Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:agentDeploymentRetryFailureReason", { value: record?.agentDeploymentRetryFailureReason, record, resource: "runtime-infrastructure-access-view", field: "agentDeploymentRetryFailureReason", view: "display" }) ?? <CopyableText value={record?.agentDeploymentRetryFailureReason} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.connectedAt.label", "Connected At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:connectedAt", { value: record?.connectedAt, record, resource: "runtime-infrastructure-access-view", field: "connectedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.connectedAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_access_view.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-access-view:display:state", { value: record?.state, record, resource: "runtime-infrastructure-access-view", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t, dictionaryLabel, [
                { label: t("resources.runtime_infrastructure_access_view.fields.state.options.Planned", "Planned"), value: "Planned" },
                { label: t("resources.runtime_infrastructure_access_view.fields.state.options.Registered", "Registered"), value: "Registered" },
                { label: t("resources.runtime_infrastructure_access_view.fields.state.options.Prepared", "Prepared"), value: "Prepared" },
                { label: t("resources.runtime_infrastructure_access_view.fields.state.options.Verified", "Verified"), value: "Verified" },
                { label: t("resources.runtime_infrastructure_access_view.fields.state.options.VerificationFailed", "Verification Failed"), value: "VerificationFailed" },
                { label: t("resources.runtime_infrastructure_access_view.fields.state.options.AgentReady", "Agent Ready"), value: "AgentReady" },
                { label: t("resources.runtime_infrastructure_access_view.fields.state.options.RuntimeAgentFailed", "Runtime Agent Failed"), value: "RuntimeAgentFailed" },
                { label: t("resources.runtime_infrastructure_access_view.fields.state.options.Offline", "Offline"), value: "Offline" },
                { label: t("resources.runtime_infrastructure_access_view.fields.state.options.Connected", "Connected"), value: "Connected" },
              ])}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
