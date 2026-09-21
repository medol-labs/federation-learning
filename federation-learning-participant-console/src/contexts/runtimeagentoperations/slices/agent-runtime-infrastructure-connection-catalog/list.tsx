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

type AgentRuntimeInfrastructureConnectionCatalogRecord = {
  runtimeInfrastructureId: string;
  runtimeAgentId: string;
  runtimePlatformConnectionReady: boolean;
  platformApiReachable: boolean;
  agentAuthenticationSucceeded: boolean;
  controlChannelEstablished: boolean;
  heartbeatAccepted: boolean;
  connectedAt: string;
  connectionReportFailedAt?: string;
  connectionReportFailureReason?: string;
  connectionReportRetryable?: boolean;
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
        meta: {
          label: t("resources.agent_runtime_infrastructure_connection_catalog.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id"),
          placeholder: "Enter Runtime Infrastructure Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeInfrastructureConnectionCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-infrastructure-connection-catalog:display:runtimeInfrastructureId",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-infrastructure-connection-catalog",
              field: "runtimeInfrastructureId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeAgentId", {
        id: "runtimeAgentId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_infrastructure_connection_catalog.fields.runtimeAgentId.label", "Runtime Agent Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_infrastructure_connection_catalog.fields.runtimeAgentId.label", "Runtime Agent Id"),
          placeholder: "Enter Runtime Agent Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeInfrastructureConnectionCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-infrastructure-connection-catalog:display:runtimeAgentId",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-infrastructure-connection-catalog",
              field: "runtimeAgentId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimePlatformConnectionReady", {
        id: "runtimePlatformConnectionReady",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_infrastructure_connection_catalog.fields.runtimePlatformConnectionReady.label", "Runtime Platform Connection Ready")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_infrastructure_connection_catalog.fields.runtimePlatformConnectionReady.label", "Runtime Platform Connection Ready"),
          placeholder: "Enter Runtime Platform Connection Ready",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeInfrastructureConnectionCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-infrastructure-connection-catalog:display:runtimePlatformConnectionReady",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-infrastructure-connection-catalog",
              field: "runtimePlatformConnectionReady",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("platformApiReachable", {
        id: "platformApiReachable",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_infrastructure_connection_catalog.fields.platformApiReachable.label", "Platform Api Reachable")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_infrastructure_connection_catalog.fields.platformApiReachable.label", "Platform Api Reachable"),
          placeholder: "Enter Platform Api Reachable",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeInfrastructureConnectionCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-infrastructure-connection-catalog:display:platformApiReachable",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-infrastructure-connection-catalog",
              field: "platformApiReachable",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("agentAuthenticationSucceeded", {
        id: "agentAuthenticationSucceeded",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_infrastructure_connection_catalog.fields.agentAuthenticationSucceeded.label", "Agent Authentication Succeeded")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_infrastructure_connection_catalog.fields.agentAuthenticationSucceeded.label", "Agent Authentication Succeeded"),
          placeholder: "Enter Agent Authentication Succeeded",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeInfrastructureConnectionCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-infrastructure-connection-catalog:display:agentAuthenticationSucceeded",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-infrastructure-connection-catalog",
              field: "agentAuthenticationSucceeded",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("controlChannelEstablished", {
        id: "controlChannelEstablished",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_infrastructure_connection_catalog.fields.controlChannelEstablished.label", "Control Channel Established")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_infrastructure_connection_catalog.fields.controlChannelEstablished.label", "Control Channel Established"),
          placeholder: "Enter Control Channel Established",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeInfrastructureConnectionCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-infrastructure-connection-catalog:display:controlChannelEstablished",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-infrastructure-connection-catalog",
              field: "controlChannelEstablished",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("heartbeatAccepted", {
        id: "heartbeatAccepted",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_infrastructure_connection_catalog.fields.heartbeatAccepted.label", "Heartbeat Accepted")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_infrastructure_connection_catalog.fields.heartbeatAccepted.label", "Heartbeat Accepted"),
          placeholder: "Enter Heartbeat Accepted",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeInfrastructureConnectionCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-infrastructure-connection-catalog:display:heartbeatAccepted",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-infrastructure-connection-catalog",
              field: "heartbeatAccepted",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("connectedAt", {
        id: "connectedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_infrastructure_connection_catalog.fields.connectedAt.label", "Connected At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_infrastructure_connection_catalog.fields.connectedAt.label", "Connected At"),
          placeholder: "Enter Connected At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeInfrastructureConnectionCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-infrastructure-connection-catalog:display:connectedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-infrastructure-connection-catalog",
              field: "connectedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("connectionReportFailedAt", {
        id: "connectionReportFailedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_infrastructure_connection_catalog.fields.connectionReportFailedAt.label", "Connection Report Failed At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_infrastructure_connection_catalog.fields.connectionReportFailedAt.label", "Connection Report Failed At"),
          placeholder: "Enter Connection Report Failed At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeInfrastructureConnectionCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-infrastructure-connection-catalog:display:connectionReportFailedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-infrastructure-connection-catalog",
              field: "connectionReportFailedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("connectionReportFailureReason", {
        id: "connectionReportFailureReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_infrastructure_connection_catalog.fields.connectionReportFailureReason.label", "Connection Report Failure Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_infrastructure_connection_catalog.fields.connectionReportFailureReason.label", "Connection Report Failure Reason"),
          placeholder: "Enter Connection Report Failure Reason",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeInfrastructureConnectionCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-infrastructure-connection-catalog:display:connectionReportFailureReason",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-infrastructure-connection-catalog",
              field: "connectionReportFailureReason",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("connectionReportRetryable", {
        id: "connectionReportRetryable",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_runtime_infrastructure_connection_catalog.fields.connectionReportRetryable.label", "Connection Report Retryable")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_runtime_infrastructure_connection_catalog.fields.connectionReportRetryable.label", "Connection Report Retryable"),
          placeholder: "Enter Connection Report Retryable",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentRuntimeInfrastructureConnectionCatalogRecord>(
            frontendComposition,
            "field:agent-runtime-infrastructure-connection-catalog:display:connectionReportRetryable",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-runtime-infrastructure-connection-catalog",
              field: "connectionReportRetryable",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
              {renderSlotExtensions<AgentRuntimeInfrastructureConnectionCatalogRecord>(
                frontendComposition,
                "row-actions:agent-runtime-infrastructure-connection-catalog:list",
                "rowActions.before",
                { resource: "agent-runtime-infrastructure-connection-catalog", record: row.original },
              )}
              <ShowButton variant="ghost" recordItemId={row.original.runtimeInfrastructureId} size="sm" />
              {renderSlotExtensions<AgentRuntimeInfrastructureConnectionCatalogRecord>(
                frontendComposition,
                "row-actions:agent-runtime-infrastructure-connection-catalog:list",
                "rowActions.after",
                { resource: "agent-runtime-infrastructure-connection-catalog", record: row.original },
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
    getRowId: (row) => String(row.runtimeInfrastructureId),
    refineCoreProps: {
      dataProviderName: "federation-learning-runtime-agent",
      syncWithLocation: false,
      meta: {
        tableName: "agent_runtime_infrastructure_connection_catalog_read_model_entity",
        idField: "runtimeInfrastructureId",
        idFields: ["runtimeInfrastructureId"],
        queryFields: ["runtimeInfrastructureId","runtimeAgentId","runtimePlatformConnectionReady","platformApiReachable","agentAuthenticationSucceeded","controlChannelEstablished","heartbeatAccepted","connectedAt","connectionReportFailedAt","connectionReportFailureReason","connectionReportRetryable"],
        label: t("resources.agent_runtime_infrastructure_connection_catalog.label", "Agent Runtime Infrastructure Connection Catalog"),
        aggregateRoute: "agentruntimeinfrastructureconnection",
        queryRoute: "agentruntimeinfrastructureconnectioncatalog",
        dataProviderName: "federation-learning-runtime-agent",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:agent-runtime-infrastructure-connection-catalog:list", "toolbar.before", { resource: "agent-runtime-infrastructure-connection-catalog", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:agent-runtime-infrastructure-connection-catalog:list", "toolbar.actions", { resource: "agent-runtime-infrastructure-connection-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:agent-runtime-infrastructure-connection-catalog:list", "toolbar.after", { resource: "agent-runtime-infrastructure-connection-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
