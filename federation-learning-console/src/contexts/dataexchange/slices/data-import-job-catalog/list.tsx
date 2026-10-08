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
import { CopyableText } from "@/components/refine-ui/fields/copyable-text";

type DataImportJobCatalogRecord = {
  dataImportJobId: string;
  commandName: string;
  resourceName: string;
  originalFileName: string;
  filePath: string;
  requestedLocale?: string;
  requestedAt: string;
  requestHash: string;
  status: string;
  totalRows?: number;
  successRows?: number;
  failedRows?: number;
  failureIndexPath?: string;
  errorMessage?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: DataImportJobCatalogRecord,
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

export const DataImportJobCatalogList = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<DataImportJobCatalogRecord>();
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
      columnHelper.accessor("dataImportJobId", {
        id: "dataImportJobId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_import_job_catalog.fields.dataImportJobId.label", "Data Import Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_import_job_catalog.fields.dataImportJobId.label", "Data Import Job Id"),
          placeholder: "Enter Data Import Job Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataImportJobCatalogRecord>(
            frontendComposition,
            "field:data-import-job-catalog:display:dataImportJobId",
            {
              value: getValue(),
              record: row.original,
              resource: "data-import-job-catalog",
              field: "dataImportJobId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("commandName", {
        id: "commandName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_import_job_catalog.fields.commandName.label", "Command Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_import_job_catalog.fields.commandName.label", "Command Name"),
          placeholder: "Enter Command Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataImportJobCatalogRecord>(
            frontendComposition,
            "field:data-import-job-catalog:display:commandName",
            {
              value: getValue(),
              record: row.original,
              resource: "data-import-job-catalog",
              field: "commandName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("resourceName", {
        id: "resourceName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_import_job_catalog.fields.resourceName.label", "Resource Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_import_job_catalog.fields.resourceName.label", "Resource Name"),
          placeholder: "Enter Resource Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataImportJobCatalogRecord>(
            frontendComposition,
            "field:data-import-job-catalog:display:resourceName",
            {
              value: getValue(),
              record: row.original,
              resource: "data-import-job-catalog",
              field: "resourceName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("originalFileName", {
        id: "originalFileName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_import_job_catalog.fields.originalFileName.label", "Original File Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_import_job_catalog.fields.originalFileName.label", "Original File Name"),
          placeholder: "Enter Original File Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataImportJobCatalogRecord>(
            frontendComposition,
            "field:data-import-job-catalog:display:originalFileName",
            {
              value: getValue(),
              record: row.original,
              resource: "data-import-job-catalog",
              field: "originalFileName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("filePath", {
        id: "filePath",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_import_job_catalog.fields.filePath.label", "File Path")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_import_job_catalog.fields.filePath.label", "File Path"),
          placeholder: "Enter File Path",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataImportJobCatalogRecord>(
            frontendComposition,
            "field:data-import-job-catalog:display:filePath",
            {
              value: getValue(),
              record: row.original,
              resource: "data-import-job-catalog",
              field: "filePath",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("requestedLocale", {
        id: "requestedLocale",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_import_job_catalog.fields.requestedLocale.label", "Requested Locale")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_import_job_catalog.fields.requestedLocale.label", "Requested Locale"),
          placeholder: "Enter Requested Locale",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataImportJobCatalogRecord>(
            frontendComposition,
            "field:data-import-job-catalog:display:requestedLocale",
            {
              value: getValue(),
              record: row.original,
              resource: "data-import-job-catalog",
              field: "requestedLocale",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("requestedAt", {
        id: "requestedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_import_job_catalog.fields.requestedAt.label", "Requested At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_import_job_catalog.fields.requestedAt.label", "Requested At"),
          placeholder: "Enter Requested At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataImportJobCatalogRecord>(
            frontendComposition,
            "field:data-import-job-catalog:display:requestedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "data-import-job-catalog",
              field: "requestedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("requestHash", {
        id: "requestHash",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_import_job_catalog.fields.requestHash.label", "Request Hash")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_import_job_catalog.fields.requestHash.label", "Request Hash"),
          placeholder: "Enter Request Hash",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataImportJobCatalogRecord>(
            frontendComposition,
            "field:data-import-job-catalog:display:requestHash",
            {
              value: getValue(),
              record: row.original,
              resource: "data-import-job-catalog",
              field: "requestHash",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("status", {
        id: "status",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_import_job_catalog.fields.status.label", "Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_import_job_catalog.fields.status.label", "Status"),
          placeholder: "Enter Status",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataImportJobCatalogRecord>(
            frontendComposition,
            "field:data-import-job-catalog:display:status",
            {
              value: getValue(),
              record: row.original,
              resource: "data-import-job-catalog",
              field: "status",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("totalRows", {
        id: "totalRows",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_import_job_catalog.fields.totalRows.label", "Total Rows")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_import_job_catalog.fields.totalRows.label", "Total Rows"),
          placeholder: "Enter Total Rows",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataImportJobCatalogRecord>(
            frontendComposition,
            "field:data-import-job-catalog:display:totalRows",
            {
              value: getValue(),
              record: row.original,
              resource: "data-import-job-catalog",
              field: "totalRows",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("successRows", {
        id: "successRows",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_import_job_catalog.fields.successRows.label", "Success Rows")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_import_job_catalog.fields.successRows.label", "Success Rows"),
          placeholder: "Enter Success Rows",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataImportJobCatalogRecord>(
            frontendComposition,
            "field:data-import-job-catalog:display:successRows",
            {
              value: getValue(),
              record: row.original,
              resource: "data-import-job-catalog",
              field: "successRows",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("failedRows", {
        id: "failedRows",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_import_job_catalog.fields.failedRows.label", "Failed Rows")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_import_job_catalog.fields.failedRows.label", "Failed Rows"),
          placeholder: "Enter Failed Rows",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataImportJobCatalogRecord>(
            frontendComposition,
            "field:data-import-job-catalog:display:failedRows",
            {
              value: getValue(),
              record: row.original,
              resource: "data-import-job-catalog",
              field: "failedRows",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("failureIndexPath", {
        id: "failureIndexPath",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_import_job_catalog.fields.failureIndexPath.label", "Failure Index Path")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_import_job_catalog.fields.failureIndexPath.label", "Failure Index Path"),
          placeholder: "Enter Failure Index Path",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataImportJobCatalogRecord>(
            frontendComposition,
            "field:data-import-job-catalog:display:failureIndexPath",
            {
              value: getValue(),
              record: row.original,
              resource: "data-import-job-catalog",
              field: "failureIndexPath",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("errorMessage", {
        id: "errorMessage",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_import_job_catalog.fields.errorMessage.label", "Error Message")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_import_job_catalog.fields.errorMessage.label", "Error Message"),
          placeholder: "Enter Error Message",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataImportJobCatalogRecord>(
            frontendComposition,
            "field:data-import-job-catalog:display:errorMessage",
            {
              value: getValue(),
              record: row.original,
              resource: "data-import-job-catalog",
              field: "errorMessage",
              view: "display",
              compact: true,
            },
          ) ?? <CopyableText value={getValue()} compact />,
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
              {renderSlotExtensions<DataImportJobCatalogRecord>(
                frontendComposition,
                "row-actions:data-import-job-catalog:list",
                "rowActions.before",
                { resource: "data-import-job-catalog", record: row.original },
              )}
              <ShowButton variant="ghost" recordItemId={row.original.dataImportJobId} size="sm" />
              {renderSlotExtensions<DataImportJobCatalogRecord>(
                frontendComposition,
                "row-actions:data-import-job-catalog:list",
                "rowActions.after",
                { resource: "data-import-job-catalog", record: row.original },
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
    getRowId: (row) => String(row.dataImportJobId),
    refineCoreProps: {
      dataProviderName: "federation-learning-support",
      syncWithLocation: false,
      meta: {
        tableName: "data_import_job_catalog_read_model_entity",
        idField: "dataImportJobId",
        idFields: ["dataImportJobId"],
        queryFields: ["dataImportJobId","commandName","resourceName","originalFileName","filePath","requestedLocale","requestedAt","requestHash","status","totalRows","successRows","failedRows","failureIndexPath","errorMessage"],
        label: t("resources.data_import_job_catalog.label", "Data Import Job Catalog"),
        aggregateRoute: "dataimportjob",
        queryRoute: "dataimportjobcatalog",
        dataProviderName: "federation-learning-support",
      },
    },
  });


  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:data-import-job-catalog:list", "toolbar.before", { resource: "data-import-job-catalog", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:data-import-job-catalog:list", "toolbar.actions", { resource: "data-import-job-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        >
        </ListToolbar>
        {renderSlotExtensions(frontendComposition, "toolbar:data-import-job-catalog:list", "toolbar.after", { resource: "data-import-job-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
