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

type AgentOrganizationDirectoryRecord = {
  organizationId: string;
  organizationName: string;
  organizationType: string;
  state: string;
  syncedAt: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: AgentOrganizationDirectoryRecord,
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

export const AgentOrganizationDirectoryList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<AgentOrganizationDirectoryRecord>();
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
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_organization_directory.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_organization_directory.fields.organizationId.label", "Organization Id"),
          placeholder: "Enter Organization Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentOrganizationDirectoryRecord>(
            frontendComposition,
            "field:agent-organization-directory:display:organizationId",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-organization-directory",
              field: "organizationId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_organization_directory.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_organization_directory.fields.organizationName.label", "Organization Name"),
          placeholder: "Enter Organization Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentOrganizationDirectoryRecord>(
            frontendComposition,
            "field:agent-organization-directory:display:organizationName",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-organization-directory",
              field: "organizationName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationType", {
        id: "organizationType",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_organization_directory.fields.organizationType.label", "Organization Type")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_organization_directory.fields.organizationType.label", "Organization Type"),
          placeholder: "Enter Organization Type",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentOrganizationDirectoryRecord>(
            frontendComposition,
            "field:agent-organization-directory:display:organizationType",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-organization-directory",
              field: "organizationType",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_organization_directory.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_organization_directory.fields.state.label", "State"),
          placeholder: "Enter State",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentOrganizationDirectoryRecord>(
            frontendComposition,
            "field:agent-organization-directory:display:state",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-organization-directory",
              field: "state",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("syncedAt", {
        id: "syncedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_organization_directory.fields.syncedAt.label", "Synced At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_organization_directory.fields.syncedAt.label", "Synced At"),
          placeholder: "Enter Synced At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentOrganizationDirectoryRecord>(
            frontendComposition,
            "field:agent-organization-directory:display:syncedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-organization-directory",
              field: "syncedAt",
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
              {renderSlotExtensions<AgentOrganizationDirectoryRecord>(
                frontendComposition,
                "row-actions:agent-organization-directory:list",
                "rowActions.before",
                { resource: "agent-organization-directory", record: row.original },
              )}
              <ShowButton variant="ghost" recordItemId={row.original.organizationId} size="sm" />
              {renderSlotExtensions<AgentOrganizationDirectoryRecord>(
                frontendComposition,
                "row-actions:agent-organization-directory:list",
                "rowActions.after",
                { resource: "agent-organization-directory", record: row.original },
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
    getRowId: (row) => String(row.organizationId),
    refineCoreProps: {
      dataProviderName: "federation-learning-runtime-agent",
      syncWithLocation: false,
      meta: {
        tableName: "agent_organization_directory_read_model_entity",
        idField: "organizationId",
        idFields: ["organizationId"],
        queryFields: ["organizationId","organizationName","organizationType","state","syncedAt"],
        label: t("resources.agent_organization_directory.label", "Agent Organization Directory"),
        aggregateRoute: "agentorganizationdirectory",
        queryRoute: "agentorganizationdirectory",
        dataProviderName: "federation-learning-runtime-agent",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:agent-organization-directory:list", "toolbar.before", { resource: "agent-organization-directory", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:agent-organization-directory:list", "toolbar.actions", { resource: "agent-organization-directory", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:agent-organization-directory:list", "toolbar.after", { resource: "agent-organization-directory", table })}
      </RefineDataTable>
    </ListView>
  );
};
