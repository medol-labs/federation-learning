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
import { CopyableText } from "@/components/refine-ui/fields/copyable-text";
import type { DictionaryCode } from "@/contexts/domain/value-types";

type DictionaryCatalogRecord = {
  dictionaryId: string;
  dictionaryCode: DictionaryCode;
  dictionaryName: string;
  description?: string;
  state: "Registered" | "Archived";
  registeredAt: string;
  updatedAt?: string;
  archivedAt?: string;
  archiveReason?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: DictionaryCatalogRecord,
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

export const DictionaryCatalogList = () => {
  const t = useTranslate();
  const { open } = useNotification();
  const [isExporting, setIsExporting] = React.useState(false);
  const { dictionaryLabel } = useDictionaryTranslation();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<DictionaryCatalogRecord>();
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
      columnHelper.accessor("dictionaryId", {
        id: "dictionaryId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_catalog.fields.dictionaryId.label", "Dictionary Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_catalog.fields.dictionaryId.label", "Dictionary Id"),
          placeholder: "Enter Dictionary Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryCatalogRecord>(
            frontendComposition,
            "field:dictionary-catalog:display:dictionaryId",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-catalog",
              field: "dictionaryId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("dictionaryCode", {
        id: "dictionaryCode",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_catalog.fields.dictionaryCode.label", "Dictionary Code")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_catalog.fields.dictionaryCode.label", "Dictionary Code"),
          placeholder: "Enter Dictionary Code",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryCatalogRecord>(
            frontendComposition,
            "field:dictionary-catalog:display:dictionaryCode",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-catalog",
              field: "dictionaryCode",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("dictionaryName", {
        id: "dictionaryName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_catalog.fields.dictionaryName.label", "Dictionary Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_catalog.fields.dictionaryName.label", "Dictionary Name"),
          placeholder: "Enter Dictionary Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryCatalogRecord>(
            frontendComposition,
            "field:dictionary-catalog:display:dictionaryName",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-catalog",
              field: "dictionaryName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("description", {
        id: "description",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_catalog.fields.description.label", "Description")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_catalog.fields.description.label", "Description"),
          placeholder: "Enter Description",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryCatalogRecord>(
            frontendComposition,
            "field:dictionary-catalog:display:description",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-catalog",
              field: "description",
              view: "display",
              compact: true,
            },
          ) ?? <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_catalog.fields.state.label", "State"),
          placeholder: "Select State",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: t("resources.dictionary_catalog.fields.state.options.Registered", "Registered"), value: "Registered" },
            { label: t("resources.dictionary_catalog.fields.state.options.Archived", "Archived"), value: "Archived" },
          ],
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryCatalogRecord>(
            frontendComposition,
            "field:dictionary-catalog:display:state",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-catalog",
              field: "state",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel, [
            { label: t("resources.dictionary_catalog.fields.state.options.Registered", "Registered"), value: "Registered" },
            { label: t("resources.dictionary_catalog.fields.state.options.Archived", "Archived"), value: "Archived" },
          ]),
      }),
      columnHelper.accessor("registeredAt", {
        id: "registeredAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_catalog.fields.registeredAt.label", "Registered At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_catalog.fields.registeredAt.label", "Registered At"),
          placeholder: "Enter Registered At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryCatalogRecord>(
            frontendComposition,
            "field:dictionary-catalog:display:registeredAt",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-catalog",
              field: "registeredAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("updatedAt", {
        id: "updatedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_catalog.fields.updatedAt.label", "Updated At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_catalog.fields.updatedAt.label", "Updated At"),
          placeholder: "Enter Updated At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryCatalogRecord>(
            frontendComposition,
            "field:dictionary-catalog:display:updatedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-catalog",
              field: "updatedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("archivedAt", {
        id: "archivedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_catalog.fields.archivedAt.label", "Archived At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_catalog.fields.archivedAt.label", "Archived At"),
          placeholder: "Enter Archived At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryCatalogRecord>(
            frontendComposition,
            "field:dictionary-catalog:display:archivedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-catalog",
              field: "archivedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("archiveReason", {
        id: "archiveReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_catalog.fields.archiveReason.label", "Archive Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_catalog.fields.archiveReason.label", "Archive Reason"),
          placeholder: "Enter Archive Reason",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryCatalogRecord>(
            frontendComposition,
            "field:dictionary-catalog:display:archiveReason",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-catalog",
              field: "archiveReason",
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
            {isCommandVisible(row.original, "", "state", ["Registered"]) && (
            <CommandButton
              variant="outline"
              command="archiveDictionary"
              recordItemId={row.original.dictionaryId}
              size="sm"
              query={{
                archiveReason: row.original.archiveReason,
                dictionaryCode: row.original.dictionaryCode,
              }}
            />
            )}
            <RowActionMenu>
              {renderSlotExtensions<DictionaryCatalogRecord>(
                frontendComposition,
                "row-actions:dictionary-catalog:list",
                "rowActions.before",
                { resource: "dictionary-catalog", record: row.original },
              )}
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="updateDictionary"
                    recordItemId={row.original.dictionaryId}
                    size="sm"
                    query={{
                      dictionaryName: row.original.dictionaryName,
                      description: row.original.description,
                      dictionaryCode: row.original.dictionaryCode,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="addDictionaryValue"
                    recordItemId={row.original.dictionaryId}
                    size="sm"
                    query={{
                      dictionaryId: row.original.dictionaryId,
                      dictionaryCode: row.original.dictionaryCode,
                      description: row.original.description,
                    }}
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.dictionaryId} size="sm" />
              {renderSlotExtensions<DictionaryCatalogRecord>(
                frontendComposition,
                "row-actions:dictionary-catalog:list",
                "rowActions.after",
                { resource: "dictionary-catalog", record: row.original },
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
    getRowId: (row) => String(row.dictionaryId),
    refineCoreProps: {
      dataProviderName: "federation-learning-support",
      syncWithLocation: false,
      meta: {
        tableName: "dictionary_catalog_read_model_entity",
        idField: "dictionaryId",
        idFields: ["dictionaryId"],
        queryFields: ["dictionaryId","dictionaryCode","dictionaryName","description","state","registeredAt","updatedAt","archivedAt","archiveReason"],
        label: t("resources.dictionary_catalog.label", "Dictionary Catalog"),
        aggregateRoute: "dictionary",
        queryRoute: "dictionarycatalog",
        dataProviderName: "federation-learning-support",
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
        ? [...filters, { field: "dictionaryId", operator: "in", value: selectedIds } as CrudFilter]
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
        aggregateRoute: "dictionary",
        queryRoute: "dictionarycatalog",
        dataProviderName: "federation-learning-support",
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
        {renderSlotExtensions(frontendComposition, "toolbar:dictionary-catalog:list", "toolbar.before", { resource: "dictionary-catalog", table })}
        <CommandButton variant="default" command="registerDictionary" />
        {renderSlotExtensions(frontendComposition, "toolbar:dictionary-catalog:list", "toolbar.actions", { resource: "dictionary-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        <CommandButton variant="destructive" command="archiveDictionary" size="sm" />
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
        {renderSlotExtensions(frontendComposition, "toolbar:dictionary-catalog:list", "toolbar.after", { resource: "dictionary-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
