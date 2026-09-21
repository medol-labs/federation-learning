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

type UserRoleAssignmentCatalogRecord = {
  userAccountId: string;
  username?: string;
  roleCode: string;
  roleName?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: UserRoleAssignmentCatalogRecord,
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

export const UserRoleAssignmentCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<UserRoleAssignmentCatalogRecord>();
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
      columnHelper.accessor("userAccountId", {
        id: "userAccountId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.user_role_assignment_catalog.fields.userAccountId.label", "User Account Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.user_role_assignment_catalog.fields.userAccountId.label", "User Account Id"),
          placeholder: "Enter User Account Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UserRoleAssignmentCatalogRecord>(
            frontendComposition,
            "field:user-role-assignment-catalog:display:userAccountId",
            {
              value: getValue(),
              record: row.original,
              resource: "user-role-assignment-catalog",
              field: "userAccountId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("username", {
        id: "username",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.user_role_assignment_catalog.fields.username.label", "Username")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.user_role_assignment_catalog.fields.username.label", "Username"),
          placeholder: "Enter Username",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UserRoleAssignmentCatalogRecord>(
            frontendComposition,
            "field:user-role-assignment-catalog:display:username",
            {
              value: getValue(),
              record: row.original,
              resource: "user-role-assignment-catalog",
              field: "username",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roleCode", {
        id: "roleCode",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.user_role_assignment_catalog.fields.roleCode.label", "Role Code")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.user_role_assignment_catalog.fields.roleCode.label", "Role Code"),
          placeholder: "Enter Role Code",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UserRoleAssignmentCatalogRecord>(
            frontendComposition,
            "field:user-role-assignment-catalog:display:roleCode",
            {
              value: getValue(),
              record: row.original,
              resource: "user-role-assignment-catalog",
              field: "roleCode",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roleName", {
        id: "roleName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.user_role_assignment_catalog.fields.roleName.label", "Role Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.user_role_assignment_catalog.fields.roleName.label", "Role Name"),
          placeholder: "Enter Role Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UserRoleAssignmentCatalogRecord>(
            frontendComposition,
            "field:user-role-assignment-catalog:display:roleName",
            {
              value: getValue(),
              record: row.original,
              resource: "user-role-assignment-catalog",
              field: "roleName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
              {renderSlotExtensions<UserRoleAssignmentCatalogRecord>(
                frontendComposition,
                "row-actions:user-role-assignment-catalog:list",
                "rowActions.before",
                { resource: "user-role-assignment-catalog", record: row.original },
              )}
              <ShowButton variant="ghost" recordItemId={row.original.userAccountId} size="sm" />
              {renderSlotExtensions<UserRoleAssignmentCatalogRecord>(
                frontendComposition,
                "row-actions:user-role-assignment-catalog:list",
                "rowActions.after",
                { resource: "user-role-assignment-catalog", record: row.original },
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
    getRowId: (row) => String(row.userAccountId) + ":" + String(row.roleCode),
    refineCoreProps: {
      dataProviderName: "federation-learning-runtime-agent",
      syncWithLocation: false,
      meta: {
        tableName: "user_role_assignment_catalog_read_model_entity",
        idField: "userAccountId",
        idFields: ["userAccountId","roleCode"],
        queryFields: ["userAccountId","username","roleCode","roleName"],
        label: t("resources.user_role_assignment_catalog.label", "User Role Assignment Catalog"),
        aggregateRoute: "userroleassignment",
        queryRoute: "userroleassignmentcatalog",
        dataProviderName: "federation-learning-runtime-agent",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:user-role-assignment-catalog:list", "toolbar.before", { resource: "user-role-assignment-catalog", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:user-role-assignment-catalog:list", "toolbar.actions", { resource: "user-role-assignment-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:user-role-assignment-catalog:list", "toolbar.after", { resource: "user-role-assignment-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
