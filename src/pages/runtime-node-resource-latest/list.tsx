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

type RuntimeNodeResourceLatestRecord = {
  nodeId: string;
  runtimeAgentId: string;
  runtimeInfrastructureId?: string;
  runtimeNodeName?: string;
  nodeReady: boolean;
  allocatableCpuCores: number;
  allocatableMemoryGb: number;
  allocatableGpuCount: number;
  allocatedCpuCores: number;
  allocatedMemoryGb: number;
  allocatedGpuCount: number;
  availableCpuCores: number;
  availableMemoryGb: number;
  availableGpuCount: number;
  runningWorkloadCount: number;
  workloadCapacity: number;
  observedAt: string;
  allocatableCapacityChanged: boolean;
  telemetryRetentionPolicy: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: RuntimeNodeResourceLatestRecord,
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

export const RuntimeNodeResourceLatestList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<RuntimeNodeResourceLatestRecord>();
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
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.nodeId.label", "Node Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.nodeId.label", "Node Id"),
          placeholder: "Enter Node Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeAgentId", {
        id: "runtimeAgentId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.runtimeAgentId.label", "Runtime Agent Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.runtimeAgentId.label", "Runtime Agent Id"),
          placeholder: "Enter Runtime Agent Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructureId", {
        id: "runtimeInfrastructureId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id"),
          placeholder: "Enter Runtime Infrastructure Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeNodeName", {
        id: "runtimeNodeName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.runtimeNodeName.label", "Runtime Node Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.runtimeNodeName.label", "Runtime Node Name"),
          placeholder: "Enter Runtime Node Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("nodeReady", {
        id: "nodeReady",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.nodeReady.label", "Node Ready")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.nodeReady.label", "Node Ready"),
          placeholder: "Enter Node Ready",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("allocatableCpuCores", {
        id: "allocatableCpuCores",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.allocatableCpuCores.label", "Allocatable Cpu Cores")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.allocatableCpuCores.label", "Allocatable Cpu Cores"),
          placeholder: "Enter Allocatable Cpu Cores",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("allocatableMemoryGb", {
        id: "allocatableMemoryGb",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.allocatableMemoryGb.label", "Allocatable Memory Gb")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.allocatableMemoryGb.label", "Allocatable Memory Gb"),
          placeholder: "Enter Allocatable Memory Gb",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("allocatableGpuCount", {
        id: "allocatableGpuCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.allocatableGpuCount.label", "Allocatable Gpu Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.allocatableGpuCount.label", "Allocatable Gpu Count"),
          placeholder: "Enter Allocatable Gpu Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("allocatedCpuCores", {
        id: "allocatedCpuCores",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.allocatedCpuCores.label", "Allocated Cpu Cores")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.allocatedCpuCores.label", "Allocated Cpu Cores"),
          placeholder: "Enter Allocated Cpu Cores",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("allocatedMemoryGb", {
        id: "allocatedMemoryGb",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.allocatedMemoryGb.label", "Allocated Memory Gb")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.allocatedMemoryGb.label", "Allocated Memory Gb"),
          placeholder: "Enter Allocated Memory Gb",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("allocatedGpuCount", {
        id: "allocatedGpuCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.allocatedGpuCount.label", "Allocated Gpu Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.allocatedGpuCount.label", "Allocated Gpu Count"),
          placeholder: "Enter Allocated Gpu Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("availableCpuCores", {
        id: "availableCpuCores",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.availableCpuCores.label", "Available Cpu Cores")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.availableCpuCores.label", "Available Cpu Cores"),
          placeholder: "Enter Available Cpu Cores",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("availableMemoryGb", {
        id: "availableMemoryGb",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.availableMemoryGb.label", "Available Memory Gb")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.availableMemoryGb.label", "Available Memory Gb"),
          placeholder: "Enter Available Memory Gb",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("availableGpuCount", {
        id: "availableGpuCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.availableGpuCount.label", "Available Gpu Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.availableGpuCount.label", "Available Gpu Count"),
          placeholder: "Enter Available Gpu Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runningWorkloadCount", {
        id: "runningWorkloadCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.runningWorkloadCount.label", "Running Workload Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.runningWorkloadCount.label", "Running Workload Count"),
          placeholder: "Enter Running Workload Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("workloadCapacity", {
        id: "workloadCapacity",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.workloadCapacity.label", "Workload Capacity")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.workloadCapacity.label", "Workload Capacity"),
          placeholder: "Enter Workload Capacity",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("observedAt", {
        id: "observedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.observedAt.label", "Observed At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.observedAt.label", "Observed At"),
          placeholder: "Enter Observed At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("allocatableCapacityChanged", {
        id: "allocatableCapacityChanged",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.allocatableCapacityChanged.label", "Allocatable Capacity Changed")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.allocatableCapacityChanged.label", "Allocatable Capacity Changed"),
          placeholder: "Enter Allocatable Capacity Changed",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("telemetryRetentionPolicy", {
        id: "telemetryRetentionPolicy",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_resource_latest.fields.telemetryRetentionPolicy.label", "Telemetry Retention Policy")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_resource_latest.fields.telemetryRetentionPolicy.label", "Telemetry Retention Policy"),
          placeholder: "Enter Telemetry Retention Policy",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
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
      syncWithLocation: false,
      meta: {
        tableName: "runtime_node_resource_latest_read_model_entity",
        idField: "nodeId",
        idFields: ["nodeId"],
        queryFields: ["nodeId","runtimeAgentId","runtimeInfrastructureId","runtimeNodeName","nodeReady","allocatableCpuCores","allocatableMemoryGb","allocatableGpuCount","allocatedCpuCores","allocatedMemoryGb","allocatedGpuCount","availableCpuCores","availableMemoryGb","availableGpuCount","runningWorkloadCount","workloadCapacity","observedAt","allocatableCapacityChanged","telemetryRetentionPolicy"],
        label: t("resources.runtime_node_resource_latest.label", "Runtime Node Resource Latest"),
        aggregateRoute: "runtimenoderesourcetelemetry",
        queryRoute: "runtimenoderesourcelatest",
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
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
      </RefineDataTable>
    </ListView>
  );
};
