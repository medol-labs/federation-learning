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

export const RuntimeAgentLifecycleCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "runtime_agent_lifecycle_catalog_read_model_entity",
      idField: "runtimeAgentId",
      label: t("resources.runtime_agent_lifecycle_catalog.label", "Runtime Agent Lifecycle Catalog"),
      aggregateRoute: "runtimeagentlifecycle",
      queryRoute: "runtimeagentlifecyclecatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.runtimeAgentId ?? t("resources.runtime_agent_lifecycle_catalog.label", "Runtime Agent Lifecycle Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_lifecycle_catalog.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_lifecycle_catalog.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructureId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_lifecycle_catalog.fields.agentVersion.label", "Agent Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.agentVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_lifecycle_catalog.fields.lifecycleStatus.label", "Lifecycle Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.lifecycleStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_lifecycle_catalog.fields.bootstrapConfigurationLoaded.label", "Bootstrap Configuration Loaded")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.bootstrapConfigurationLoaded, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_lifecycle_catalog.fields.bootstrapFailureReason.label", "Bootstrap Failure Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.bootstrapFailureReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_lifecycle_catalog.fields.runtimeAgentSelfCheckPassed.label", "Runtime Agent Self Check Passed")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentSelfCheckPassed, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_lifecycle_catalog.fields.configurationLoaded.label", "Configuration Loaded")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.configurationLoaded, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_lifecycle_catalog.fields.secretStoreAccessible.label", "Secret Store Accessible")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.secretStoreAccessible, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_lifecycle_catalog.fields.runtimeEngineAdapterReady.label", "Runtime Engine Adapter Ready")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineAdapterReady, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_lifecycle_catalog.fields.modelRepositoryClientReady.label", "Model Repository Client Ready")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelRepositoryClientReady, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_lifecycle_catalog.fields.localDatasetBindingStoreReady.label", "Local Dataset Binding Store Ready")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.localDatasetBindingStoreReady, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_lifecycle_catalog.fields.workingDirectoryWritable.label", "Working Directory Writable")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.workingDirectoryWritable, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_lifecycle_catalog.fields.bootstrappedAt.label", "Bootstrapped At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.bootstrappedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_lifecycle_catalog.fields.bootstrapFailedAt.label", "Bootstrap Failed At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.bootstrapFailedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_lifecycle_catalog.fields.startedAt.label", "Started At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.startedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_lifecycle_catalog.fields.readyAt.label", "Ready At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.readyAt, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
