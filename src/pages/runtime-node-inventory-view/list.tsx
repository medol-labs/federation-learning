// Generated from config.json by the refine generator.
import { useTable } from "@refinedev/react-table";
import { useTranslate } from "@refinedev/core";
import { createColumnHelper } from "@tanstack/react-table";
import React from "react";

import { DataTableColumnHeader } from "@/components/data-table/data-table-column-header";
import { CommandButton } from "@/components/refine-ui/buttons/command";
import { EditButton } from "@/components/refine-ui/buttons/edit";
import { ShowButton } from "@/components/refine-ui/buttons/show";
import { RefineDataTable } from "@/components/refine-ui/data-table/refine-data-table";
import {
  ListToolbar,
  ListView,
  ListViewHeader
} from "@/components/refine-ui/views/list-view";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from "@/components/ui/dropdown-menu";
import { MoreHorizontal } from "lucide-react";

type RuntimeNodeInventoryViewRecord = {
  nodeId: string;
  runtimeNodeInventoryReportId: string;
  organizationId: string;
  runtimeInfrastructureId: string;
  runtimeAgentId: string;
  organizationName?: string;
  runtimeName?: string;
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
  recordedAt: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: RuntimeNodeInventoryViewRecord,
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

export const RuntimeNodeInventoryViewList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<RuntimeNodeInventoryViewRecord>();
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
      columnHelper.accessor("nodeId", {
        id: "nodeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.nodeId.label", "Node Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeNodeInventoryReportId", {
        id: "runtimeNodeInventoryReportId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.runtimeNodeInventoryReportId.label", "Runtime Node Inventory Report Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructureId", {
        id: "runtimeInfrastructureId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeAgentId", {
        id: "runtimeAgentId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.runtimeAgentId.label", "Runtime Agent Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeName", {
        id: "runtimeName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.runtimeName.label", "Runtime Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeNodeName", {
        id: "runtimeNodeName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.runtimeNodeName.label", "Runtime Node Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("infrastructureNodeId", {
        id: "infrastructureNodeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.infrastructureNodeId.label", "Infrastructure Node Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeNodeRole", {
        id: "runtimeNodeRole",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.runtimeNodeRole.label", "Runtime Node Role")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("nodeReady", {
        id: "nodeReady",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.nodeReady.label", "Node Ready")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeEngineVersion", {
        id: "runtimeEngineVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.runtimeEngineVersion.label", "Runtime Engine Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("containerEngineVersion", {
        id: "containerEngineVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.containerEngineVersion.label", "Container Engine Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("operatingSystem", {
        id: "operatingSystem",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.operatingSystem.label", "Operating System")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("architecture", {
        id: "architecture",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.architecture.label", "Architecture")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("inventoryHash", {
        id: "inventoryHash",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.inventoryHash.label", "Inventory Hash")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("discoveredAt", {
        id: "discoveredAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.discoveredAt.label", "Discovered At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("recordedAt", {
        id: "recordedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.recordedAt.label", "Recorded At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="ghost" size="icon">
                  <MoreHorizontal className="h-4 w-4" />
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end">
                {isCommandVisible(row.original, "", "", []) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="registerRuntimeInfrastructure"
                    recordItemId={row.original.nodeId}
                    size="sm"
                    query={{
                      runtimeInfrastructureId: row.original.runtimeInfrastructureId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                <DropdownMenuItem>
                  <ShowButton variant="ghost" recordItemId={row.original.nodeId} size="sm" />
                </DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
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
    getRowId: (row) => String(row.nodeId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: true,
      meta: {
        tableName: "runtime_node_inventory_view_read_model_entity",
        idField: "nodeId",
        idFields: ["nodeId"],
        label: t("resources.runtime_node_inventory_view.label", "Runtime Node Inventory View"),
        aggregateRoute: "runtimenodeinventory",
        queryRoute: "runtimenodeinventoryview",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar table={table.reactTable} />
      </RefineDataTable>
    </ListView>
  );
};
