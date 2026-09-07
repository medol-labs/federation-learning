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
import type { OrganizationType } from "@/domain/value-types";

type OrganizationDirectoryRecord = {
  organizationId: string;
  organizationName: string;
  organizationType: OrganizationType;
  state: "REGISTERED" | "ACTIVE" | "DEACTIVATED";
  approvedDatasetCount: number;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: OrganizationDirectoryRecord,
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

export const OrganizationDirectoryList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<OrganizationDirectoryRecord>();
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
          <DataTableColumnHeader column={column} label={t("resources.organization_directory.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.organization_directory.fields.organizationId.label", "Organization Id"),
          placeholder: "Enter Organization Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.organization_directory.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.organization_directory.fields.organizationName.label", "Organization Name"),
          placeholder: "Enter Organization Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationType", {
        id: "organizationType",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.organization_directory.fields.organizationType.label", "Organization Type")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.organization_directory.fields.organizationType.label", "Organization Type"),
          placeholder: "Select Organization Type",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: "Hospital", value: "HOSPITAL" },
            { label: "Research Institute", value: "RESEARCH_INSTITUTE" },
            { label: "Public Health Agency", value: "PUBLIC_HEALTH_AGENCY" },
            { label: "Laboratory", value: "LABORATORY" },
            { label: "Rehabilitation Center", value: "REHABILITATION_CENTER" },
          ],
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.organization_directory.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.organization_directory.fields.state.label", "State"),
          placeholder: "Select State",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: "Registered", value: "REGISTERED" },
            { label: "Active", value: "ACTIVE" },
            { label: "Deactivated", value: "DEACTIVATED" },
          ],
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("approvedDatasetCount", {
        id: "approvedDatasetCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.organization_directory.fields.approvedDatasetCount.label", "Approved Dataset Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.organization_directory.fields.approvedDatasetCount.label", "Approved Dataset Count"),
          placeholder: "Enter Approved Dataset Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
                {isCommandVisible(row.original, "", "state", ["Registered"]) && (
                  <CommandButton
                    variant="ghost"
                    command="activateOrganization"
                    recordItemId={row.original.organizationId}
                    size="sm"
                  />
                )}
                {isCommandVisible(row.original, "", "state", ["Active"]) && (
                  <CommandButton
                    variant="ghost"
                    command="deactivateOrganization"
                    recordItemId={row.original.organizationId}
                    size="sm"
                  />
                )}
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="createRuntimeInstallationPlan"
                    recordItemId={row.original.organizationId}
                    size="sm"
                    query={{
                      organizationId: row.original.organizationId,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "state", ["Deactivated"]) && (
                  <CommandButton
                    variant="ghost"
                    command="reactivateOrganization"
                    recordItemId={row.original.organizationId}
                    size="sm"
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.organizationId} size="sm" />
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
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "organization_directory_read_model_entity",
        idField: "organizationId",
        idFields: ["organizationId"],
        queryFields: ["organizationId","organizationName","organizationType","state","approvedDatasetCount"],
        label: t("resources.organization_directory.label", "Organization Directory"),
        aggregateRoute: "organization",
        queryRoute: "organizationdirectory",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        <CommandButton variant="default" command="registerOrganization" />
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
