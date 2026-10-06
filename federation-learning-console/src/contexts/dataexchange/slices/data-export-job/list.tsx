// Generated from config.json by the refine generator.
import { useTable } from "@refinedev/react-table";
import { useTranslate } from "@refinedev/core";
import { createColumnHelper } from "@tanstack/react-table";
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
import { useDictionaryTranslation } from "@/lib/dictionary-i18n";
import { renderFieldOverride, renderSlotExtensions } from "@/platform/composition";

type DataExportJobCatalogRecord = {
  dataExportJobId: string;
  resourceName: string;
  requestedLocale?: string;
  status: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: DataExportJobCatalogRecord,
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

export const DataExportJobCatalogList = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<DataExportJobCatalogRecord>();
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
      columnHelper.accessor("dataExportJobId", {
        id: "dataExportJobId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_export_job_catalog.fields.dataExportJobId.label", "Data Export Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_export_job_catalog.fields.dataExportJobId.label", "Data Export Job Id"),
          placeholder: "Enter Data Export Job Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataExportJobCatalogRecord>(
            frontendComposition,
            "field:data-export-job-catalog:display:dataExportJobId",
            {
              value: getValue(),
              record: row.original,
              resource: "data-export-job-catalog",
              field: "dataExportJobId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("resourceName", {
        id: "resourceName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_export_job_catalog.fields.resourceName.label", "Resource Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_export_job_catalog.fields.resourceName.label", "Resource Name"),
          placeholder: "Enter Resource Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataExportJobCatalogRecord>(
            frontendComposition,
            "field:data-export-job-catalog:display:resourceName",
            {
              value: getValue(),
              record: row.original,
              resource: "data-export-job-catalog",
              field: "resourceName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("requestedLocale", {
        id: "requestedLocale",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_export_job_catalog.fields.requestedLocale.label", "Requested Locale")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_export_job_catalog.fields.requestedLocale.label", "Requested Locale"),
          placeholder: "Enter Requested Locale",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataExportJobCatalogRecord>(
            frontendComposition,
            "field:data-export-job-catalog:display:requestedLocale",
            {
              value: getValue(),
              record: row.original,
              resource: "data-export-job-catalog",
              field: "requestedLocale",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("status", {
        id: "status",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_export_job_catalog.fields.status.label", "Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_export_job_catalog.fields.status.label", "Status"),
          placeholder: "Enter Status",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataExportJobCatalogRecord>(
            frontendComposition,
            "field:data-export-job-catalog:display:status",
            {
              value: getValue(),
              record: row.original,
              resource: "data-export-job-catalog",
              field: "status",
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
              {renderSlotExtensions<DataExportJobCatalogRecord>(
                frontendComposition,
                "row-actions:data-export-job-catalog:list",
                "rowActions.before",
                { resource: "data-export-job-catalog", record: row.original },
              )}
              <ShowButton variant="ghost" recordItemId={row.original.dataExportJobId} size="sm" />
              {renderSlotExtensions<DataExportJobCatalogRecord>(
                frontendComposition,
                "row-actions:data-export-job-catalog:list",
                "rowActions.after",
                { resource: "data-export-job-catalog", record: row.original },
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
    getRowId: (row) => String(row.dataExportJobId),
    refineCoreProps: {
      dataProviderName: "federation-learning-support",
      syncWithLocation: false,
      meta: {
        tableName: "data_export_job_catalog_read_model_entity",
        idField: "dataExportJobId",
        idFields: ["dataExportJobId"],
        queryFields: ["dataExportJobId","resourceName","requestedLocale","status"],
        label: t("resources.data_export_job_catalog.label", "Data Export Job Catalog"),
        aggregateRoute: "dataexportjob",
        queryRoute: "dataexportjobcatalog",
        dataProviderName: "federation-learning-support",
      },
    },
  });


  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:data-export-job-catalog:list", "toolbar.before", { resource: "data-export-job-catalog", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:data-export-job-catalog:list", "toolbar.actions", { resource: "data-export-job-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:data-export-job-catalog:list", "toolbar.after", { resource: "data-export-job-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
