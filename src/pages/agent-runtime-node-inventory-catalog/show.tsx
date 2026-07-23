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

export const AgentRuntimeNodeInventoryCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "flruntime-agent",
    meta: {
      tableName: "agent_runtime_node_inventory_catalog_read_model_entity",
      idField: "runtimeNodeInventoryReportId",
      label: t("resources.agent_runtime_node_inventory_catalog.label", "Agent Runtime Node Inventory Catalog"),
      aggregateRoute: "agentruntimenodeinventory",
      queryRoute: "agentruntimenodeinventorycatalog",
      dataProviderName: "flruntime-agent",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.runtimeNodeInventoryReportId ?? t("resources.agent_runtime_node_inventory_catalog.label", "Agent Runtime Node Inventory Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.runtimeNodeInventoryReportId.label", "Runtime Node Inventory Report Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeNodeInventoryReportId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.organizationId.label", "Organization Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructureId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.organizationName.label", "Organization Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.runtimeNodeName.label", "Runtime Node Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeNodeName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.infrastructureNodeId.label", "Infrastructure Node Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.infrastructureNodeId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.runtimeNodeRole.label", "Runtime Node Role")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeNodeRole, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.nodeReady.label", "Node Ready")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.nodeReady, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.runtimeEngineVersion.label", "Runtime Engine Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.containerEngineVersion.label", "Container Engine Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.containerEngineVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.operatingSystem.label", "Operating System")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.operatingSystem, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.architecture.label", "Architecture")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.architecture, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.inventoryHash.label", "Inventory Hash")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.inventoryHash, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.discoveredAt.label", "Discovered At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.discoveredAt, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
