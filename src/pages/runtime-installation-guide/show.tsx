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

export const RuntimeInstallationGuideShow = () => {
  const t = useTranslate();
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
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInstallationPlanId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.organizationId.label", "Organization Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructureId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeInfrastructurePackageName.label", "Runtime Infrastructure Package Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeInfrastructurePackageVersion.label", "Runtime Infrastructure Package Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.organizationName.label", "Organization Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeName.label", "Runtime Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.bootstrapCommand.label", "Bootstrap Command")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.bootstrapCommand, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.nodeLabelCommand.label", "Node Label Command")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.nodeLabelCommand, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.nodeTaintCommand.label", "Node Taint Command")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.nodeTaintCommand, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeAgentNodeSelectorYaml.label", "Runtime Agent Node Selector Yaml")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentNodeSelectorYaml, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeAgentTolerationsYaml.label", "Runtime Agent Tolerations Yaml")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentTolerationsYaml, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.bootstrapConfigYaml.label", "Bootstrap Config Yaml")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.bootstrapConfigYaml, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.runtimeEnvironmentType.label", "Runtime Environment Type")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEnvironmentType, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.agentInstallMode.label", "Agent Install Mode")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.agentInstallMode, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_installation_guide.fields.expectedNodeCount.label", "Expected Node Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.expectedNodeCount, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
