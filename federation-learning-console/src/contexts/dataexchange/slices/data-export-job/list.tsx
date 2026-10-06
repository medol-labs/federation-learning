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

type DataExportJobCatalogRecord = {
  dataExportJobId: string;
  resourceName: string;
  criteriaJson: string;
  sortJson: string;
  columnsJson: string;
  requestedLocale?: string;
  requestedAt: string;
  snapshotUpperBound: string;
  requestHash: string;
  status: string;
  fileName?: string;
  filePath?: string;
  rowCount?: number;
  errorMessage?: string;
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
      columnHelper.accessor("criteriaJson", {
        id: "criteriaJson",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_export_job_catalog.fields.criteriaJson.label", "Criteria Json")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_export_job_catalog.fields.criteriaJson.label", "Criteria Json"),
          placeholder: "Enter Criteria Json",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataExportJobCatalogRecord>(
            frontendComposition,
            "field:data-export-job-catalog:display:criteriaJson",
            {
              value: getValue(),
              record: row.original,
              resource: "data-export-job-catalog",
              field: "criteriaJson",
              view: "display",
              compact: true,
            },
          ) ?? <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("sortJson", {
        id: "sortJson",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_export_job_catalog.fields.sortJson.label", "Sort Json")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_export_job_catalog.fields.sortJson.label", "Sort Json"),
          placeholder: "Enter Sort Json",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataExportJobCatalogRecord>(
            frontendComposition,
            "field:data-export-job-catalog:display:sortJson",
            {
              value: getValue(),
              record: row.original,
              resource: "data-export-job-catalog",
              field: "sortJson",
              view: "display",
              compact: true,
            },
          ) ?? <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("columnsJson", {
        id: "columnsJson",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_export_job_catalog.fields.columnsJson.label", "Columns Json")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_export_job_catalog.fields.columnsJson.label", "Columns Json"),
          placeholder: "Enter Columns Json",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataExportJobCatalogRecord>(
            frontendComposition,
            "field:data-export-job-catalog:display:columnsJson",
            {
              value: getValue(),
              record: row.original,
              resource: "data-export-job-catalog",
              field: "columnsJson",
              view: "display",
              compact: true,
            },
          ) ?? <CopyableText value={getValue()} compact />,
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
      columnHelper.accessor("requestedAt", {
        id: "requestedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_export_job_catalog.fields.requestedAt.label", "Requested At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_export_job_catalog.fields.requestedAt.label", "Requested At"),
          placeholder: "Enter Requested At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataExportJobCatalogRecord>(
            frontendComposition,
            "field:data-export-job-catalog:display:requestedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "data-export-job-catalog",
              field: "requestedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("snapshotUpperBound", {
        id: "snapshotUpperBound",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_export_job_catalog.fields.snapshotUpperBound.label", "Snapshot Upper Bound")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_export_job_catalog.fields.snapshotUpperBound.label", "Snapshot Upper Bound"),
          placeholder: "Enter Snapshot Upper Bound",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataExportJobCatalogRecord>(
            frontendComposition,
            "field:data-export-job-catalog:display:snapshotUpperBound",
            {
              value: getValue(),
              record: row.original,
              resource: "data-export-job-catalog",
              field: "snapshotUpperBound",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("requestHash", {
        id: "requestHash",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_export_job_catalog.fields.requestHash.label", "Request Hash")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_export_job_catalog.fields.requestHash.label", "Request Hash"),
          placeholder: "Enter Request Hash",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataExportJobCatalogRecord>(
            frontendComposition,
            "field:data-export-job-catalog:display:requestHash",
            {
              value: getValue(),
              record: row.original,
              resource: "data-export-job-catalog",
              field: "requestHash",
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
      columnHelper.accessor("fileName", {
        id: "fileName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_export_job_catalog.fields.fileName.label", "File Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_export_job_catalog.fields.fileName.label", "File Name"),
          placeholder: "Enter File Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataExportJobCatalogRecord>(
            frontendComposition,
            "field:data-export-job-catalog:display:fileName",
            {
              value: getValue(),
              record: row.original,
              resource: "data-export-job-catalog",
              field: "fileName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("filePath", {
        id: "filePath",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_export_job_catalog.fields.filePath.label", "File Path")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_export_job_catalog.fields.filePath.label", "File Path"),
          placeholder: "Enter File Path",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataExportJobCatalogRecord>(
            frontendComposition,
            "field:data-export-job-catalog:display:filePath",
            {
              value: getValue(),
              record: row.original,
              resource: "data-export-job-catalog",
              field: "filePath",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("rowCount", {
        id: "rowCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_export_job_catalog.fields.rowCount.label", "Row Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_export_job_catalog.fields.rowCount.label", "Row Count"),
          placeholder: "Enter Row Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataExportJobCatalogRecord>(
            frontendComposition,
            "field:data-export-job-catalog:display:rowCount",
            {
              value: getValue(),
              record: row.original,
              resource: "data-export-job-catalog",
              field: "rowCount",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("errorMessage", {
        id: "errorMessage",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.data_export_job_catalog.fields.errorMessage.label", "Error Message")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.data_export_job_catalog.fields.errorMessage.label", "Error Message"),
          placeholder: "Enter Error Message",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DataExportJobCatalogRecord>(
            frontendComposition,
            "field:data-export-job-catalog:display:errorMessage",
            {
              value: getValue(),
              record: row.original,
              resource: "data-export-job-catalog",
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
        queryFields: ["dataExportJobId","resourceName","criteriaJson","sortJson","columnsJson","requestedLocale","requestedAt","snapshotUpperBound","requestHash","status","fileName","filePath","rowCount","errorMessage"],
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
