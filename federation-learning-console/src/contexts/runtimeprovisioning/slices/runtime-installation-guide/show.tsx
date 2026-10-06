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

export const RuntimeInstallationGuideShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_installation_guide_read_model_entity",
      idField: "runtimeInstallationPlanId",
      label: t("resources.runtime_installation_guide.label", "Runtime Installation Guide"),
      aggregateRoute: "runtimeinstallationplan",
      queryRoute: "runtimeinstallationguide",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.runtimeInstallationPlanId ?? t("resources.runtime_installation_guide.label", "Runtime Installation Guide")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeInstallationPlanId.label", "Runtime Installation Plan Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:runtimeInstallationPlanId", { value: record?.runtimeInstallationPlanId, record, resource: "runtime-installation-guide", field: "runtimeInstallationPlanId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInstallationPlanId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.organizationId.label", "Organization Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:organizationId", { value: record?.organizationId, record, resource: "runtime-installation-guide", field: "organizationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:runtimeInfrastructureId", { value: record?.runtimeInfrastructureId, record, resource: "runtime-installation-guide", field: "runtimeInfrastructureId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructureId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:runtimeAgentId", { value: record?.runtimeAgentId, record, resource: "runtime-installation-guide", field: "runtimeAgentId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeInfrastructureState.label", "Runtime Infrastructure State")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:runtimeInfrastructureState", { value: record?.runtimeInfrastructureState, record, resource: "runtime-installation-guide", field: "runtimeInfrastructureState", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructureState, t, dictionaryLabel, [
                { label: t("resources.runtime_installation_guide.fields.runtimeInfrastructureState.options.Planned", "Planned"), value: "Planned" },
                { label: t("resources.runtime_installation_guide.fields.runtimeInfrastructureState.options.Registered", "Registered"), value: "Registered" },
                { label: t("resources.runtime_installation_guide.fields.runtimeInfrastructureState.options.Prepared", "Prepared"), value: "Prepared" },
                { label: t("resources.runtime_installation_guide.fields.runtimeInfrastructureState.options.Verified", "Verified"), value: "Verified" },
                { label: t("resources.runtime_installation_guide.fields.runtimeInfrastructureState.options.VerificationFailed", "Verification Failed"), value: "VerificationFailed" },
                { label: t("resources.runtime_installation_guide.fields.runtimeInfrastructureState.options.AgentReady", "Agent Ready"), value: "AgentReady" },
                { label: t("resources.runtime_installation_guide.fields.runtimeInfrastructureState.options.RuntimeAgentFailed", "Runtime Agent Failed"), value: "RuntimeAgentFailed" },
                { label: t("resources.runtime_installation_guide.fields.runtimeInfrastructureState.options.Offline", "Offline"), value: "Offline" },
                { label: t("resources.runtime_installation_guide.fields.runtimeInfrastructureState.options.Connected", "Connected"), value: "Connected" },
              ])}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:runtimeInfrastructurePackageId", { value: record?.runtimeInfrastructurePackageId, record, resource: "runtime-installation-guide", field: "runtimeInfrastructurePackageId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeInfrastructurePackageName.label", "Runtime Infrastructure Package Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:runtimeInfrastructurePackageName", { value: record?.runtimeInfrastructurePackageName, record, resource: "runtime-installation-guide", field: "runtimeInfrastructurePackageName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeInfrastructurePackageVersion.label", "Runtime Infrastructure Package Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:runtimeInfrastructurePackageVersion", { value: record?.runtimeInfrastructurePackageVersion, record, resource: "runtime-installation-guide", field: "runtimeInfrastructurePackageVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageVersion, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.organizationName.label", "Organization Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:organizationName", { value: record?.organizationName, record, resource: "runtime-installation-guide", field: "organizationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeName.label", "Runtime Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:runtimeName", { value: record?.runtimeName, record, resource: "runtime-installation-guide", field: "runtimeName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.bootstrapCommand.label", "Bootstrap Command")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:bootstrapCommand", { value: record?.bootstrapCommand, record, resource: "runtime-installation-guide", field: "bootstrapCommand", view: "display" }) ?? <CopyableText value={record?.bootstrapCommand} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.nodeLabelCommand.label", "Node Label Command")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:nodeLabelCommand", { value: record?.nodeLabelCommand, record, resource: "runtime-installation-guide", field: "nodeLabelCommand", view: "display" }) ?? <CopyableText value={record?.nodeLabelCommand} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.nodeTaintCommand.label", "Node Taint Command")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:nodeTaintCommand", { value: record?.nodeTaintCommand, record, resource: "runtime-installation-guide", field: "nodeTaintCommand", view: "display" }) ?? <CopyableText value={record?.nodeTaintCommand} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeAgentNodeSelectorYaml.label", "Runtime Agent Node Selector Yaml")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:runtimeAgentNodeSelectorYaml", { value: record?.runtimeAgentNodeSelectorYaml, record, resource: "runtime-installation-guide", field: "runtimeAgentNodeSelectorYaml", view: "display" }) ?? <CopyableText value={record?.runtimeAgentNodeSelectorYaml} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeAgentTolerationsYaml.label", "Runtime Agent Tolerations Yaml")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:runtimeAgentTolerationsYaml", { value: record?.runtimeAgentTolerationsYaml, record, resource: "runtime-installation-guide", field: "runtimeAgentTolerationsYaml", view: "display" }) ?? <CopyableText value={record?.runtimeAgentTolerationsYaml} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.bootstrapConfigYaml.label", "Bootstrap Config Yaml")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:bootstrapConfigYaml", { value: record?.bootstrapConfigYaml, record, resource: "runtime-installation-guide", field: "bootstrapConfigYaml", view: "display" }) ?? <CopyableText value={record?.bootstrapConfigYaml} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeEnvironmentType.label", "Runtime Environment Type")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:runtimeEnvironmentType", { value: record?.runtimeEnvironmentType, record, resource: "runtime-installation-guide", field: "runtimeEnvironmentType", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEnvironmentType, t, dictionaryLabel, undefined, "RUNTIME_ENVIRONMENT_TYPE")}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.agentInstallMode.label", "Agent Install Mode")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:agentInstallMode", { value: record?.agentInstallMode, record, resource: "runtime-installation-guide", field: "agentInstallMode", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.agentInstallMode, t, dictionaryLabel, undefined, "RUNTIME_AGENT_INSTALL_MODE")}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.expectedNodeCount.label", "Expected Node Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-installation-guide:display:expectedNodeCount", { value: record?.expectedNodeCount, record, resource: "runtime-installation-guide", field: "expectedNodeCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.expectedNodeCount, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
