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

type PermissionCatalogRecord = {
  permissionId: string;
  permissionCode: string;
  permissionName: string;
  description?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: PermissionCatalogRecord,
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

export const PermissionCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<PermissionCatalogRecord>();
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
      columnHelper.accessor("permissionId", {
        id: "permissionId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.permission_catalog.fields.permissionId.label", "Permission Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.permission_catalog.fields.permissionId.label", "Permission Id"),
          placeholder: "Enter Permission Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PermissionCatalogRecord>(
            frontendComposition,
            "field:permission-catalog:display:permissionId",
            {
              value: getValue(),
              record: row.original,
              resource: "permission-catalog",
              field: "permissionId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("permissionCode", {
        id: "permissionCode",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.permission_catalog.fields.permissionCode.label", "Permission Code")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.permission_catalog.fields.permissionCode.label", "Permission Code"),
          placeholder: "Enter Permission Code",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PermissionCatalogRecord>(
            frontendComposition,
            "field:permission-catalog:display:permissionCode",
            {
              value: getValue(),
              record: row.original,
              resource: "permission-catalog",
              field: "permissionCode",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("permissionName", {
        id: "permissionName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.permission_catalog.fields.permissionName.label", "Permission Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.permission_catalog.fields.permissionName.label", "Permission Name"),
          placeholder: "Enter Permission Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PermissionCatalogRecord>(
            frontendComposition,
            "field:permission-catalog:display:permissionName",
            {
              value: getValue(),
              record: row.original,
              resource: "permission-catalog",
              field: "permissionName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("description", {
        id: "description",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.permission_catalog.fields.description.label", "Description")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.permission_catalog.fields.description.label", "Description"),
          placeholder: "Enter Description",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PermissionCatalogRecord>(
            frontendComposition,
            "field:permission-catalog:display:description",
            {
              value: getValue(),
              record: row.original,
              resource: "permission-catalog",
              field: "description",
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
              {renderSlotExtensions<PermissionCatalogRecord>(
                frontendComposition,
                "row-actions:permission-catalog:list",
                "rowActions.before",
                { resource: "permission-catalog", record: row.original },
              )}
              <ShowButton variant="ghost" recordItemId={row.original.permissionId} size="sm" />
              {renderSlotExtensions<PermissionCatalogRecord>(
                frontendComposition,
                "row-actions:permission-catalog:list",
                "rowActions.after",
                { resource: "permission-catalog", record: row.original },
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
    getRowId: (row) => String(row.permissionId),
    refineCoreProps: {
      dataProviderName: "federation-learning-support",
      syncWithLocation: false,
      meta: {
        tableName: "permission_catalog_read_model_entity",
        idField: "permissionId",
        idFields: ["permissionId"],
        queryFields: ["permissionId","permissionCode","permissionName","description"],
        label: t("resources.permission_catalog.label", "Permission Catalog"),
        aggregateRoute: "permission",
        queryRoute: "permissioncatalog",
        dataProviderName: "federation-learning-support",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:permission-catalog:list", "toolbar.before", { resource: "permission-catalog", table })}
        <CommandButton variant="default" command="registerPermission" />
        {renderSlotExtensions(frontendComposition, "toolbar:permission-catalog:list", "toolbar.actions", { resource: "permission-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:permission-catalog:list", "toolbar.after", { resource: "permission-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
