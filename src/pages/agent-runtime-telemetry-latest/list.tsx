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
import { RowActionMenu } from "@/components/refine-ui/row-action-menu";
import {
  ListToolbar,
  ListView,
  ListViewHeader
} from "@/components/refine-ui/views/list-view";
import { Checkbox } from "@/components/ui/checkbox";

type AgentRuntimeTelemetryLatestRecord = {
  nodeId: string;
  runtimeAgentId: string;
  federationId?: string;
  trainingJobId?: string;
  roundExecutionId?: string;
  cpuLoad?: string;
  gpuLoad?: string;
  memoryLoad?: string;
  lastHeartbeatAt?: string;
  telemetryRetentionPolicy: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: AgentRuntimeTelemetryLatestRecord,
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

export const AgentRuntimeTelemetryLatestList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<AgentRuntimeTelemetryLatestRecord>();
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
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_telemetry_latest.fields.nodeId.label", "Node Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_telemetry_latest.fields.nodeId.label", "Node Id"),
          placeholder: "Enter Node Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeAgentId", {
        id: "runtimeAgentId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_telemetry_latest.fields.runtimeAgentId.label", "Runtime Agent Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_telemetry_latest.fields.runtimeAgentId.label", "Runtime Agent Id"),
          placeholder: "Enter Runtime Agent Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("federationId", {
        id: "federationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_telemetry_latest.fields.federationId.label", "Federation Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_telemetry_latest.fields.federationId.label", "Federation Id"),
          placeholder: "Enter Federation Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingJobId", {
        id: "trainingJobId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_telemetry_latest.fields.trainingJobId.label", "Training Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_telemetry_latest.fields.trainingJobId.label", "Training Job Id"),
          placeholder: "Enter Training Job Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundExecutionId", {
        id: "roundExecutionId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_telemetry_latest.fields.roundExecutionId.label", "Round Execution Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_telemetry_latest.fields.roundExecutionId.label", "Round Execution Id"),
          placeholder: "Enter Round Execution Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("cpuLoad", {
        id: "cpuLoad",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_telemetry_latest.fields.cpuLoad.label", "Cpu Load")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_telemetry_latest.fields.cpuLoad.label", "Cpu Load"),
          placeholder: "Enter Cpu Load",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("gpuLoad", {
        id: "gpuLoad",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_telemetry_latest.fields.gpuLoad.label", "Gpu Load")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_telemetry_latest.fields.gpuLoad.label", "Gpu Load"),
          placeholder: "Enter Gpu Load",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("memoryLoad", {
        id: "memoryLoad",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_telemetry_latest.fields.memoryLoad.label", "Memory Load")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_telemetry_latest.fields.memoryLoad.label", "Memory Load"),
          placeholder: "Enter Memory Load",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("lastHeartbeatAt", {
        id: "lastHeartbeatAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_telemetry_latest.fields.lastHeartbeatAt.label", "Last Heartbeat At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_telemetry_latest.fields.lastHeartbeatAt.label", "Last Heartbeat At"),
          placeholder: "Enter Last Heartbeat At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("telemetryRetentionPolicy", {
        id: "telemetryRetentionPolicy",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_telemetry_latest.fields.telemetryRetentionPolicy.label", "Telemetry Retention Policy")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_telemetry_latest.fields.telemetryRetentionPolicy.label", "Telemetry Retention Policy"),
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
            <RowActionMenu>
              <ShowButton variant="ghost" recordItemId={row.original.nodeId} size="sm" />
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
    getRowId: (row) => String(row.nodeId),
    refineCoreProps: {
      dataProviderName: "federation-learning-runtime-agent",
      syncWithLocation: false,
      meta: {
        tableName: "agent_runtime_telemetry_latest_read_model_entity",
        idField: "nodeId",
        idFields: ["nodeId"],
        queryFields: ["nodeId","runtimeAgentId","federationId","trainingJobId","roundExecutionId","cpuLoad","gpuLoad","memoryLoad","lastHeartbeatAt","telemetryRetentionPolicy"],
        label: t("resources.agent_runtime_telemetry_latest.label", "Agent Runtime Telemetry Latest"),
        aggregateRoute: "agentruntimetelemetry",
        queryRoute: "agentruntimetelemetrylatest",
        dataProviderName: "federation-learning-runtime-agent",
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
