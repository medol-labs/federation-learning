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

type RolePermissionGrantCatalogRecord = {
  roleId: string;
  roleCode: string;
  roleName?: string;
  permissionCode: string;
  permissionName?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: RolePermissionGrantCatalogRecord,
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

export const RolePermissionGrantCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<RolePermissionGrantCatalogRecord>();
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
      columnHelper.accessor("roleId", {
        id: "roleId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.role_permission_grant_catalog.fields.roleId.label", "Role Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.role_permission_grant_catalog.fields.roleId.label", "Role Id"),
          placeholder: "Enter Role Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RolePermissionGrantCatalogRecord>(
            frontendComposition,
            "field:role-permission-grant-catalog:display:roleId",
            {
              value: getValue(),
              record: row.original,
              resource: "role-permission-grant-catalog",
              field: "roleId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roleCode", {
        id: "roleCode",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.role_permission_grant_catalog.fields.roleCode.label", "Role Code")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.role_permission_grant_catalog.fields.roleCode.label", "Role Code"),
          placeholder: "Enter Role Code",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RolePermissionGrantCatalogRecord>(
            frontendComposition,
            "field:role-permission-grant-catalog:display:roleCode",
            {
              value: getValue(),
              record: row.original,
              resource: "role-permission-grant-catalog",
              field: "roleCode",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roleName", {
        id: "roleName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.role_permission_grant_catalog.fields.roleName.label", "Role Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.role_permission_grant_catalog.fields.roleName.label", "Role Name"),
          placeholder: "Enter Role Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RolePermissionGrantCatalogRecord>(
            frontendComposition,
            "field:role-permission-grant-catalog:display:roleName",
            {
              value: getValue(),
              record: row.original,
              resource: "role-permission-grant-catalog",
              field: "roleName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("permissionCode", {
        id: "permissionCode",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.role_permission_grant_catalog.fields.permissionCode.label", "Permission Code")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.role_permission_grant_catalog.fields.permissionCode.label", "Permission Code"),
          placeholder: "Enter Permission Code",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RolePermissionGrantCatalogRecord>(
            frontendComposition,
            "field:role-permission-grant-catalog:display:permissionCode",
            {
              value: getValue(),
              record: row.original,
              resource: "role-permission-grant-catalog",
              field: "permissionCode",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("permissionName", {
        id: "permissionName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.role_permission_grant_catalog.fields.permissionName.label", "Permission Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.role_permission_grant_catalog.fields.permissionName.label", "Permission Name"),
          placeholder: "Enter Permission Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RolePermissionGrantCatalogRecord>(
            frontendComposition,
            "field:role-permission-grant-catalog:display:permissionName",
            {
              value: getValue(),
              record: row.original,
              resource: "role-permission-grant-catalog",
              field: "permissionName",
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
              {renderSlotExtensions<RolePermissionGrantCatalogRecord>(
                frontendComposition,
                "row-actions:role-permission-grant-catalog:list",
                "rowActions.before",
                { resource: "role-permission-grant-catalog", record: row.original },
              )}
              <ShowButton variant="ghost" recordItemId={row.original.roleCode} size="sm" />
              {renderSlotExtensions<RolePermissionGrantCatalogRecord>(
                frontendComposition,
                "row-actions:role-permission-grant-catalog:list",
                "rowActions.after",
                { resource: "role-permission-grant-catalog", record: row.original },
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
    getRowId: (row) => String(row.roleCode) + ":" + String(row.permissionCode),
    refineCoreProps: {
      dataProviderName: "federation-learning-support",
      syncWithLocation: false,
      meta: {
        tableName: "role_permission_grant_catalog_read_model_entity",
        idField: "roleCode",
        idFields: ["roleCode","permissionCode"],
        queryFields: ["roleId","roleCode","roleName","permissionCode","permissionName"],
        label: t("resources.role_permission_grant_catalog.label", "Role Permission Grant Catalog"),
        aggregateRoute: "rolepermissiongrant",
        queryRoute: "rolepermissiongrantcatalog",
        dataProviderName: "federation-learning-support",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:role-permission-grant-catalog:list", "toolbar.before", { resource: "role-permission-grant-catalog", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:role-permission-grant-catalog:list", "toolbar.actions", { resource: "role-permission-grant-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:role-permission-grant-catalog:list", "toolbar.after", { resource: "role-permission-grant-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
