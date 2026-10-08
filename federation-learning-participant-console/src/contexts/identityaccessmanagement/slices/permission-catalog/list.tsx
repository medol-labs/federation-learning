// Generated from config.json by the refine generator.
import { useTable } from "@refinedev/react-table";
import { useNotification, useTranslate, type CrudFilter, type CrudSorting } from "@refinedev/core";
import { createColumnHelper } from "@tanstack/react-table";
import { Download } from "lucide-react";
import React from "react";

import { frontendComposition } from "@/app/composition/composition.resolved";
import { DataTableColumnHeader } from "@/components/data-table/data-table-column-header";
import { CommandButton } from "@/components/refine-ui/buttons/command";
import { ShowButton } from "@/components/refine-ui/buttons/show";
import { RefineDataTable } from "@/components/refine-ui/data-table/refine-data-table";
import { RowActionMenu } from "@/components/refine-ui/row-action-menu";
import {
  ListToolbar,
  ListView,
  ListViewHeader
} from "@/components/refine-ui/views/list-view";
import { Checkbox } from "@/components/ui/checkbox";
import { Button } from "@/components/ui/button";
import { requestDataExport, type DataExportColumn } from "@/lib/data-export";
import { useDictionaryTranslation } from "@/lib/dictionary-i18n";
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

const formatValue = (
  value: unknown,
  t: ReturnType<typeof useTranslate>,
  dictionaryLabel: ReturnType<typeof useDictionaryTranslation>["dictionaryLabel"],
  options?: Array<{ label: string; value: string }>,
  dictionaryCode?: string,
): string => {
  if (value === null || value === undefined || value === "") return "-";
  if (Array.isArray(value)) {
    const formatted: string[] = value.map((item) => formatValue(item, t, dictionaryLabel, options, dictionaryCode)).filter((item) => item !== "-");
    return formatted.length > 0 ? formatted.join(", ") : "-";
  }
  if (typeof value === "boolean") return value ? t("values.boolean.true", "True") : t("values.boolean.false", "False");
  const stringValue = String(value);
  if (dictionaryCode) return dictionaryLabel(dictionaryCode, stringValue, t(`dictionaries.${dictionaryCode}.${stringValue}`, stringValue));
  return options?.find((option) => option.value === stringValue)?.label ?? stringValue;
};

export const PermissionCatalogList = () => {
  const t = useTranslate();
  const { open } = useNotification();
  const [isExporting, setIsExporting] = React.useState(false);
  const { dictionaryLabel } = useDictionaryTranslation();
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
  }, [dictionaryLabel, t]);

  const table = useTable({
    columns,
    initialState: {
      columnPinning: { right: ["actions"], left: ["select"] },
    },
    getRowId: (row) => String(row.permissionId),
    refineCoreProps: {
      dataProviderName: "federation-learning-runtime-agent",
      syncWithLocation: false,
      meta: {
        tableName: "permission_catalog_read_model_entity",
        idField: "permissionId",
        idFields: ["permissionId"],
        queryFields: ["permissionId","permissionCode","permissionName","description"],
        label: t("resources.permission_catalog.label", "Permission Catalog"),
        aggregateRoute: "permission",
        queryRoute: "permissioncatalog",
        dataProviderName: "federation-learning-runtime-agent",
      },
    },
  });

  const handleExport = React.useCallback(async () => {
    setIsExporting(true);
    try {
      const tableState = table.reactTable.getState();
      const filters: CrudFilter[] = tableState.columnFilters.flatMap((filter) => {
        const currentFilter = filter as { id: string; operator?: string; value?: unknown };
        if (!currentFilter.operator) {
          return [];
        }
        return [{
          field: currentFilter.id,
          operator: currentFilter.operator,
          value: currentFilter.value,
        } as CrudFilter];
      });
      const sorters: CrudSorting = tableState.sorting.map((sort) => ({
        field: sort.id,
        order: sort.desc ? "desc" : "asc",
      }));
      const selectedIds = Object.entries(tableState.rowSelection)
        .filter(([, selected]) => selected)
        .map(([id]) => id);
      const exportFilters: CrudFilter[] = selectedIds.length > 0
        ? [...filters, { field: "permissionId", operator: "in", value: selectedIds } as CrudFilter]
        : filters;
      const columns: DataExportColumn[] = table.reactTable
        .getAllLeafColumns()
        .filter((column) => column.getIsVisible())
        .filter((column) => !["select", "actions"].includes(column.id))
        .map((column) => ({
          field: column.id,
          label: String(column.columnDef.meta?.label ?? column.id),
          dictionaryCode: typeof column.columnDef.meta?.dictionaryCode === "string"
            ? column.columnDef.meta.dictionaryCode
            : undefined,
        }));
      const result = await requestDataExport({
        aggregateRoute: "permission",
        queryRoute: "permissioncatalog",
        dataProviderName: "federation-learning-runtime-agent",
        filters: exportFilters,
        sorters,
        columns,
      });
      open?.({
        type: "success",
        message: result.kind === "job"
          ? t("dataExport.jobCreated", "Export job created")
          : t("dataExport.downloadStarted", "Export download started"),
        description: result.kind === "job" ? result.jobId : result.filename,
      });
    } catch (error) {
      open?.({
        type: "error",
        message: t("dataExport.failed", "Export failed"),
        description: error instanceof Error ? error.message : undefined,
      });
    } finally {
      setIsExporting(false);
    }
  }, [open, table, t]);


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
        >
          <Button type="button" variant="outline" size="sm" onClick={handleExport} disabled={isExporting}>
            <Download className="size-4" />
            {isExporting
              ? t("dataExport.exporting", "Exporting")
              : Object.values(table.reactTable.getState().rowSelection).some(Boolean)
                ? t("dataExport.exportSelected", "Export selected")
                : t("dataExport.export", "Export")}
          </Button>
        </ListToolbar>
        {renderSlotExtensions(frontendComposition, "toolbar:permission-catalog:list", "toolbar.after", { resource: "permission-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
