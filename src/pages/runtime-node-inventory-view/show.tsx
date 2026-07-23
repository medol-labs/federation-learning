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

export const RuntimeNodeInventoryViewShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "flplatform-backend",
    meta: {
      tableName: "runtime_node_inventory_view_read_model_entity",
      idField: "nodeId",
      label: t("resources.runtime_node_inventory_view.label", "Runtime Node Inventory View"),
      aggregateRoute: "runtimenodeinventory",
      queryRoute: "runtimenodeinventoryview",
      dataProviderName: "flplatform-backend",
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
              <p className="text-sm text-muted-foreground">{formatValue(record?.nodeId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.runtimeNodeInventoryReportId.label", "Runtime Node Inventory Report Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeNodeInventoryReportId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.organizationId.label", "Organization Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructureId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.organizationName.label", "Organization Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.runtimeName.label", "Runtime Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.runtimeNodeName.label", "Runtime Node Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeNodeName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.infrastructureNodeId.label", "Infrastructure Node Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.infrastructureNodeId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.runtimeNodeRole.label", "Runtime Node Role")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeNodeRole, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.nodeReady.label", "Node Ready")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.nodeReady, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.runtimeEngineVersion.label", "Runtime Engine Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.containerEngineVersion.label", "Container Engine Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.containerEngineVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.operatingSystem.label", "Operating System")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.operatingSystem, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.architecture.label", "Architecture")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.architecture, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.inventoryHash.label", "Inventory Hash")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.inventoryHash, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.discoveredAt.label", "Discovered At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.discoveredAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_node_inventory_view.fields.recordedAt.label", "Recorded At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.recordedAt, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
