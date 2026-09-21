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

export const AgentRuntimeNodeInventoryCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "agent_runtime_node_inventory_catalog_read_model_entity",
      idField: "runtimeNodeInventoryReportId",
      label: t("resources.agent_runtime_node_inventory_catalog.label", "Agent Runtime Node Inventory Catalog"),
      aggregateRoute: "agentruntimenodeinventory",
      queryRoute: "agentruntimenodeinventorycatalog",
      dataProviderName: "federation-learning-runtime-agent",
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
              {renderFieldOverride(frontendComposition, "field:agent-runtime-node-inventory-catalog:display:runtimeNodeInventoryReportId", { value: record?.runtimeNodeInventoryReportId, record, resource: "agent-runtime-node-inventory-catalog", field: "runtimeNodeInventoryReportId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeNodeInventoryReportId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.organizationId.label", "Organization Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-node-inventory-catalog:display:organizationId", { value: record?.organizationId, record, resource: "agent-runtime-node-inventory-catalog", field: "organizationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-node-inventory-catalog:display:runtimeInfrastructureId", { value: record?.runtimeInfrastructureId, record, resource: "agent-runtime-node-inventory-catalog", field: "runtimeInfrastructureId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructureId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-node-inventory-catalog:display:runtimeAgentId", { value: record?.runtimeAgentId, record, resource: "agent-runtime-node-inventory-catalog", field: "runtimeAgentId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.organizationName.label", "Organization Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-node-inventory-catalog:display:organizationName", { value: record?.organizationName, record, resource: "agent-runtime-node-inventory-catalog", field: "organizationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.runtimeNodeName.label", "Runtime Node Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-node-inventory-catalog:display:runtimeNodeName", { value: record?.runtimeNodeName, record, resource: "agent-runtime-node-inventory-catalog", field: "runtimeNodeName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeNodeName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.infrastructureNodeId.label", "Infrastructure Node Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-node-inventory-catalog:display:infrastructureNodeId", { value: record?.infrastructureNodeId, record, resource: "agent-runtime-node-inventory-catalog", field: "infrastructureNodeId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.infrastructureNodeId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.runtimeNodeRole.label", "Runtime Node Role")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-node-inventory-catalog:display:runtimeNodeRole", { value: record?.runtimeNodeRole, record, resource: "agent-runtime-node-inventory-catalog", field: "runtimeNodeRole", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeNodeRole, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.nodeReady.label", "Node Ready")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-node-inventory-catalog:display:nodeReady", { value: record?.nodeReady, record, resource: "agent-runtime-node-inventory-catalog", field: "nodeReady", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.nodeReady, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.runtimeEngineVersion.label", "Runtime Engine Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-node-inventory-catalog:display:runtimeEngineVersion", { value: record?.runtimeEngineVersion, record, resource: "agent-runtime-node-inventory-catalog", field: "runtimeEngineVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.containerEngineVersion.label", "Container Engine Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-node-inventory-catalog:display:containerEngineVersion", { value: record?.containerEngineVersion, record, resource: "agent-runtime-node-inventory-catalog", field: "containerEngineVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.containerEngineVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.operatingSystem.label", "Operating System")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-node-inventory-catalog:display:operatingSystem", { value: record?.operatingSystem, record, resource: "agent-runtime-node-inventory-catalog", field: "operatingSystem", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.operatingSystem, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.architecture.label", "Architecture")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-node-inventory-catalog:display:architecture", { value: record?.architecture, record, resource: "agent-runtime-node-inventory-catalog", field: "architecture", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.architecture, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.inventoryHash.label", "Inventory Hash")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-node-inventory-catalog:display:inventoryHash", { value: record?.inventoryHash, record, resource: "agent-runtime-node-inventory-catalog", field: "inventoryHash", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.inventoryHash, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_inventory_catalog.fields.discoveredAt.label", "Discovered At")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-node-inventory-catalog:display:discoveredAt", { value: record?.discoveredAt, record, resource: "agent-runtime-node-inventory-catalog", field: "discoveredAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.discoveredAt, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
