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

export const RuntimeNodeInventoryViewShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_node_inventory_view_read_model_entity",
      idField: "nodeId",
      label: t("resources.runtime_node_inventory_view.label", "Runtime Node Inventory View"),
      aggregateRoute: "runtimenodeinventory",
      queryRoute: "runtimenodeinventoryview",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.nodeId ?? t("resources.runtime_node_inventory_view.label", "Runtime Node Inventory View")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.nodeId.label", "Node Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-node-inventory-view:display:nodeId", { value: record?.nodeId, record, resource: "runtime-node-inventory-view", field: "nodeId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.nodeId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.runtimeNodeInventoryReportId.label", "Runtime Node Inventory Report Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-node-inventory-view:display:runtimeNodeInventoryReportId", { value: record?.runtimeNodeInventoryReportId, record, resource: "runtime-node-inventory-view", field: "runtimeNodeInventoryReportId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeNodeInventoryReportId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.organizationId.label", "Organization Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-node-inventory-view:display:organizationId", { value: record?.organizationId, record, resource: "runtime-node-inventory-view", field: "organizationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-node-inventory-view:display:runtimeInfrastructureId", { value: record?.runtimeInfrastructureId, record, resource: "runtime-node-inventory-view", field: "runtimeInfrastructureId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructureId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-node-inventory-view:display:runtimeAgentId", { value: record?.runtimeAgentId, record, resource: "runtime-node-inventory-view", field: "runtimeAgentId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.organizationName.label", "Organization Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-node-inventory-view:display:organizationName", { value: record?.organizationName, record, resource: "runtime-node-inventory-view", field: "organizationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.runtimeName.label", "Runtime Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-node-inventory-view:display:runtimeName", { value: record?.runtimeName, record, resource: "runtime-node-inventory-view", field: "runtimeName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.runtimeNodeName.label", "Runtime Node Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-node-inventory-view:display:runtimeNodeName", { value: record?.runtimeNodeName, record, resource: "runtime-node-inventory-view", field: "runtimeNodeName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeNodeName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.infrastructureNodeId.label", "Infrastructure Node Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-node-inventory-view:display:infrastructureNodeId", { value: record?.infrastructureNodeId, record, resource: "runtime-node-inventory-view", field: "infrastructureNodeId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.infrastructureNodeId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.runtimeNodeRole.label", "Runtime Node Role")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-node-inventory-view:display:runtimeNodeRole", { value: record?.runtimeNodeRole, record, resource: "runtime-node-inventory-view", field: "runtimeNodeRole", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeNodeRole, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.nodeReady.label", "Node Ready")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-node-inventory-view:display:nodeReady", { value: record?.nodeReady, record, resource: "runtime-node-inventory-view", field: "nodeReady", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.nodeReady, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.runtimeEngineVersion.label", "Runtime Engine Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-node-inventory-view:display:runtimeEngineVersion", { value: record?.runtimeEngineVersion, record, resource: "runtime-node-inventory-view", field: "runtimeEngineVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.containerEngineVersion.label", "Container Engine Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-node-inventory-view:display:containerEngineVersion", { value: record?.containerEngineVersion, record, resource: "runtime-node-inventory-view", field: "containerEngineVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.containerEngineVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.operatingSystem.label", "Operating System")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-node-inventory-view:display:operatingSystem", { value: record?.operatingSystem, record, resource: "runtime-node-inventory-view", field: "operatingSystem", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.operatingSystem, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.architecture.label", "Architecture")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-node-inventory-view:display:architecture", { value: record?.architecture, record, resource: "runtime-node-inventory-view", field: "architecture", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.architecture, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.inventoryHash.label", "Inventory Hash")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-node-inventory-view:display:inventoryHash", { value: record?.inventoryHash, record, resource: "runtime-node-inventory-view", field: "inventoryHash", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.inventoryHash, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.discoveredAt.label", "Discovered At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-node-inventory-view:display:discoveredAt", { value: record?.discoveredAt, record, resource: "runtime-node-inventory-view", field: "discoveredAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.discoveredAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.recordedAt.label", "Recorded At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-node-inventory-view:display:recordedAt", { value: record?.recordedAt, record, resource: "runtime-node-inventory-view", field: "recordedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.recordedAt, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
