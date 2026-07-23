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

type AgentRuntimeInfrastructureConnectionCatalogRecord = {
  runtimeInfrastructureId: string;
  runtimeAgentId: string;
  runtimePlatformConnectionReady: boolean;
  platformApiReachable: boolean;
  agentAuthenticationSucceeded: boolean;
  controlChannelEstablished: boolean;
  heartbeatAccepted: boolean;
  connectedAt: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: AgentRuntimeInfrastructureConnectionCatalogRecord,
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

export const AgentRuntimeInfrastructureConnectionCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<AgentRuntimeInfrastructureConnectionCatalogRecord>();
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
      columnHelper.accessor("runtimeInfrastructureId", {
        id: "runtimeInfrastructureId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_infrastructure_connection_catalog.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeAgentId", {
        id: "runtimeAgentId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_infrastructure_connection_catalog.fields.runtimeAgentId.label", "Runtime Agent Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimePlatformConnectionReady", {
        id: "runtimePlatformConnectionReady",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_infrastructure_connection_catalog.fields.runtimePlatformConnectionReady.label", "Runtime Platform Connection Ready")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("platformApiReachable", {
        id: "platformApiReachable",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_infrastructure_connection_catalog.fields.platformApiReachable.label", "Platform Api Reachable")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("agentAuthenticationSucceeded", {
        id: "agentAuthenticationSucceeded",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_infrastructure_connection_catalog.fields.agentAuthenticationSucceeded.label", "Agent Authentication Succeeded")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("controlChannelEstablished", {
        id: "controlChannelEstablished",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_infrastructure_connection_catalog.fields.controlChannelEstablished.label", "Control Channel Established")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("heartbeatAccepted", {
        id: "heartbeatAccepted",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_infrastructure_connection_catalog.fields.heartbeatAccepted.label", "Heartbeat Accepted")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("connectedAt", {
        id: "connectedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_infrastructure_connection_catalog.fields.connectedAt.label", "Connected At")} />
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
                    command="recordRuntimeConnectionEstablished"
                    recordItemId={row.original.runtimeInfrastructureId}
                    size="sm"
                    query={{
                      runtimeAgentId: row.original.runtimeAgentId,
                      runtimeInfrastructureId: row.original.runtimeInfrastructureId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                <DropdownMenuItem>
                  <ShowButton variant="ghost" recordItemId={row.original.runtimeInfrastructureId} size="sm" />
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
    getRowId: (row) => String(row.runtimeInfrastructureId),
    refineCoreProps: {
      dataProviderName: "federation-learning-console",
      syncWithLocation: true,
      meta: {
        tableName: "agent_runtime_infrastructure_connection_catalog_read_model_entity",
        idField: "runtimeInfrastructureId",
        idFields: ["runtimeInfrastructureId"],
        label: t("resources.agent_runtime_infrastructure_connection_catalog.label", "Agent Runtime Infrastructure Connection Catalog"),
        aggregateRoute: "agentruntimeinfrastructureconnection",
        queryRoute: "agentruntimeinfrastructureconnectioncatalog",
        dataProviderName: "federation-learning-console",
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
