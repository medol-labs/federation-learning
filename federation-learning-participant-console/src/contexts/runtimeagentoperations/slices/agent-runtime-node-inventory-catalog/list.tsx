// Generated from config.json by the refine generator.
import { useTable } from "@refinedev/react-table";
import { useTranslate } from "@refinedev/core";
import { createColumnHelper } from "@tanstack/react-table";
import React from "react";

import { frontendComposition } from "@/app/composition/composition.resolved";
import { DataTableColumnHeader } from "@/components/data-table/data-table-column-header";
import { CommandButton } from "@/components/refine-ui/buttons/command";
import { EditButton } from "@/components/refine-ui/buttons/edit";
import { ShowButton } from "@/components/refine-ui/buttons/show";
import { RefineDataTable } from "@/components/refine-ui/data-table/refine-data-table";
import { RowActionMenu } from "@/components/refine-ui/row-action-menu";
import {
  ListToolbar,
  ListView,
  ListViewHeader
} from "@/components/refine-ui/views/list-view";
import { Checkbox } from "@/components/ui/checkbox";
import { renderFieldOverride, renderSlotExtensions } from "@/platform/composition";

type AgentRuntimeNodeInventoryCatalogRecord = {
  runtimeNodeInventoryReportId: string;
  organizationId: string;
  runtimeInfrastructureId: string;
  runtimeAgentId: string;
  organizationName?: string;
  runtimeNodeName: string;
  infrastructureNodeId?: string;
  runtimeNodeRole: string;
  nodeReady: boolean;
  runtimeEngineVersion?: string;
  containerEngineVersion?: string;
  operatingSystem?: string;
  architecture: string;
  inventoryHash: string;
  discoveredAt: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: AgentRuntimeNodeInventoryCatalogRecord,
  enabledField?: string,
  stateField?: string,
  allowedStates: string[] = [],
) => {
  const row = record as Record<string, unknown>;
  if (enabledField && row[enabledField] === false) return false;
  if (allowedStates.length === 0) return true;
  if (!stateField) return false;
  const currentState = normalizeWorkflowState(row[stateField]);
  return allowedStates.map(normalizeWorkflowState).includes(currentState);
};

export const AgentRuntimeNodeInventoryCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<AgentRuntimeNodeInventoryCatalogRecord>();
    return [
      columnHelper.display({
        id: "select",
        header: ({ table }) => (
          <Checkbox
            checked={table.getIsAllPageRowsSelected() || (table.getIsSomePageRowsSelected() && "indeterminate")}
            onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
            aria-label={t("table.selectAll", "Select all")}
          />
        ),
        cell: ({ row }) => (
          <Checkbox
            checked={row.getIsSelected()}
            onCheckedChange={(value) => row.toggleSelected(!!value)}
            aria-label={t("table.selectRow", "Select row")}
          />
        ),
        size: 32,
        enableSorting: false,
        enableHiding: false,
      }),
      columnHelper.accessor("runtimeNodeInventoryReportId", {
        id: "runtimeNodeInventoryReportId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_node_inventory_catalog.fields.runtimeNodeInventoryReportId.label", "Runtime Node Inventory Report Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_node_inventory_catalog.fields.runtimeNodeInventoryReportId.label", "Runtime Node Inventory Report Id"),
          placeholder: "Enter Runtime Node Inventory Report Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeNodeInventoryCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-node-inventory-catalog:display:runtimeNodeInventoryReportId",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-node-inventory-catalog",
              field: "runtimeNodeInventoryReportId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_node_inventory_catalog.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_node_inventory_catalog.fields.organizationId.label", "Organization Id"),
          placeholder: "Enter Organization Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeNodeInventoryCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-node-inventory-catalog:display:organizationId",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-node-inventory-catalog",
              field: "organizationId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructureId", {
        id: "runtimeInfrastructureId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_node_inventory_catalog.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_node_inventory_catalog.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id"),
          placeholder: "Enter Runtime Infrastructure Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeNodeInventoryCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-node-inventory-catalog:display:runtimeInfrastructureId",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-node-inventory-catalog",
              field: "runtimeInfrastructureId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeAgentId", {
        id: "runtimeAgentId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_node_inventory_catalog.fields.runtimeAgentId.label", "Runtime Agent Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_node_inventory_catalog.fields.runtimeAgentId.label", "Runtime Agent Id"),
          placeholder: "Enter Runtime Agent Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeNodeInventoryCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-node-inventory-catalog:display:runtimeAgentId",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-node-inventory-catalog",
              field: "runtimeAgentId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_node_inventory_catalog.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_node_inventory_catalog.fields.organizationName.label", "Organization Name"),
          placeholder: "Enter Organization Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeNodeInventoryCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-node-inventory-catalog:display:organizationName",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-node-inventory-catalog",
              field: "organizationName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeNodeName", {
        id: "runtimeNodeName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_node_inventory_catalog.fields.runtimeNodeName.label", "Runtime Node Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_node_inventory_catalog.fields.runtimeNodeName.label", "Runtime Node Name"),
          placeholder: "Enter Runtime Node Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeNodeInventoryCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-node-inventory-catalog:display:runtimeNodeName",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-node-inventory-catalog",
              field: "runtimeNodeName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("infrastructureNodeId", {
        id: "infrastructureNodeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_node_inventory_catalog.fields.infrastructureNodeId.label", "Infrastructure Node Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_node_inventory_catalog.fields.infrastructureNodeId.label", "Infrastructure Node Id"),
          placeholder: "Enter Infrastructure Node Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeNodeInventoryCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-node-inventory-catalog:display:infrastructureNodeId",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-node-inventory-catalog",
              field: "infrastructureNodeId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeNodeRole", {
        id: "runtimeNodeRole",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_node_inventory_catalog.fields.runtimeNodeRole.label", "Runtime Node Role")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_node_inventory_catalog.fields.runtimeNodeRole.label", "Runtime Node Role"),
          placeholder: "Enter Runtime Node Role",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeNodeInventoryCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-node-inventory-catalog:display:runtimeNodeRole",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-node-inventory-catalog",
              field: "runtimeNodeRole",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("nodeReady", {
        id: "nodeReady",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_node_inventory_catalog.fields.nodeReady.label", "Node Ready")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_node_inventory_catalog.fields.nodeReady.label", "Node Ready"),
          placeholder: "Enter Node Ready",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeNodeInventoryCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-node-inventory-catalog:display:nodeReady",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-node-inventory-catalog",
              field: "nodeReady",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeEngineVersion", {
        id: "runtimeEngineVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_node_inventory_catalog.fields.runtimeEngineVersion.label", "Runtime Engine Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_node_inventory_catalog.fields.runtimeEngineVersion.label", "Runtime Engine Version"),
          placeholder: "Enter Runtime Engine Version",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeNodeInventoryCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-node-inventory-catalog:display:runtimeEngineVersion",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-node-inventory-catalog",
              field: "runtimeEngineVersion",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("containerEngineVersion", {
        id: "containerEngineVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_node_inventory_catalog.fields.containerEngineVersion.label", "Container Engine Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_node_inventory_catalog.fields.containerEngineVersion.label", "Container Engine Version"),
          placeholder: "Enter Container Engine Version",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeNodeInventoryCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-node-inventory-catalog:display:containerEngineVersion",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-node-inventory-catalog",
              field: "containerEngineVersion",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("operatingSystem", {
        id: "operatingSystem",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_node_inventory_catalog.fields.operatingSystem.label", "Operating System")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_node_inventory_catalog.fields.operatingSystem.label", "Operating System"),
          placeholder: "Enter Operating System",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeNodeInventoryCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-node-inventory-catalog:display:operatingSystem",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-node-inventory-catalog",
              field: "operatingSystem",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("architecture", {
        id: "architecture",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_node_inventory_catalog.fields.architecture.label", "Architecture")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_node_inventory_catalog.fields.architecture.label", "Architecture"),
          placeholder: "Enter Architecture",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeNodeInventoryCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-node-inventory-catalog:display:architecture",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-node-inventory-catalog",
              field: "architecture",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("inventoryHash", {
        id: "inventoryHash",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_node_inventory_catalog.fields.inventoryHash.label", "Inventory Hash")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_node_inventory_catalog.fields.inventoryHash.label", "Inventory Hash"),
          placeholder: "Enter Inventory Hash",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeNodeInventoryCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-node-inventory-catalog:display:inventoryHash",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-node-inventory-catalog",
              field: "inventoryHash",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("discoveredAt", {
        id: "discoveredAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_node_inventory_catalog.fields.discoveredAt.label", "Discovered At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_node_inventory_catalog.fields.discoveredAt.label", "Discovered At"),
          placeholder: "Enter Discovered At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeNodeInventoryCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-node-inventory-catalog:display:discoveredAt",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-node-inventory-catalog",
              field: "discoveredAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
              {renderSlotExtensions<AgentRuntimeNodeInventoryCatalogRecord>(
                frontendComposition,
                "row-actions:agent-runtime-node-inventory-catalog:list",
                "rowActions.before",
                { resource: "agent-runtime-node-inventory-catalog", record: row.original },
              )}
              <ShowButton variant="ghost" recordItemId={row.original.runtimeNodeInventoryReportId} size="sm" />
              {renderSlotExtensions<AgentRuntimeNodeInventoryCatalogRecord>(
                frontendComposition,
                "row-actions:agent-runtime-node-inventory-catalog:list",
                "rowActions.after",
                { resource: "agent-runtime-node-inventory-catalog", record: row.original },
              )}
            </RowActionMenu>
          </div>
        ),
        enableSorting: false,
        size: 32,
      }),
    ];
  }, [t]);

  const table = useTable({
    columns,
    initialState: {
      columnPinning: { right: ["actions"], left: ["select"] },
    },
    getRowId: (row) => String(row.runtimeNodeInventoryReportId),
    refineCoreProps: {
      dataProviderName: "federation-learning-runtime-agent",
      syncWithLocation: false,
      meta: {
        tableName: "agent_runtime_node_inventory_catalog_read_model_entity",
        idField: "runtimeNodeInventoryReportId",
        idFields: ["runtimeNodeInventoryReportId"],
        queryFields: ["runtimeNodeInventoryReportId","organizationId","runtimeInfrastructureId","runtimeAgentId","organizationName","runtimeNodeName","infrastructureNodeId","runtimeNodeRole","nodeReady","runtimeEngineVersion","containerEngineVersion","operatingSystem","architecture","inventoryHash","discoveredAt"],
        label: t("resources.agent_runtime_node_inventory_catalog.label", "Agent Runtime Node Inventory Catalog"),
        aggregateRoute: "agentruntimenodeinventory",
        queryRoute: "agentruntimenodeinventorycatalog",
        dataProviderName: "federation-learning-runtime-agent",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:agent-runtime-node-inventory-catalog:list", "toolbar.before", { resource: "agent-runtime-node-inventory-catalog", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:agent-runtime-node-inventory-catalog:list", "toolbar.actions", { resource: "agent-runtime-node-inventory-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:agent-runtime-node-inventory-catalog:list", "toolbar.after", { resource: "agent-runtime-node-inventory-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
