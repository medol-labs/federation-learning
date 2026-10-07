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
import type { DictionaryCode, DictionaryValueCode, LocaleCode } from "@/contexts/domain/value-types";

type DictionaryValueTranslationCatalogRecord = {
  dictionaryValueTranslationId: string;
  dictionaryValueId: string;
  dictionaryCode: DictionaryCode;
  valueCode: DictionaryValueCode;
  locale: LocaleCode;
  displayName: string;
  description?: string;
  updatedAt?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: DictionaryValueTranslationCatalogRecord,
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

export const DictionaryValueTranslationCatalogList = () => {
  const t = useTranslate();
  const { open } = useNotification();
  const [isExporting, setIsExporting] = React.useState(false);
  const { dictionaryLabel } = useDictionaryTranslation();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<DictionaryValueTranslationCatalogRecord>();
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
      columnHelper.accessor("dictionaryValueTranslationId", {
        id: "dictionaryValueTranslationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_translation_catalog.fields.dictionaryValueTranslationId.label", "Dictionary Value Translation Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_translation_catalog.fields.dictionaryValueTranslationId.label", "Dictionary Value Translation Id"),
          placeholder: "Enter Dictionary Value Translation Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueTranslationCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-translation-catalog:display:dictionaryValueTranslationId",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-translation-catalog",
              field: "dictionaryValueTranslationId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("dictionaryValueId", {
        id: "dictionaryValueId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_translation_catalog.fields.dictionaryValueId.label", "Dictionary Value Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_translation_catalog.fields.dictionaryValueId.label", "Dictionary Value Id"),
          placeholder: "Enter Dictionary Value Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueTranslationCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-translation-catalog:display:dictionaryValueId",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-translation-catalog",
              field: "dictionaryValueId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("dictionaryCode", {
        id: "dictionaryCode",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_translation_catalog.fields.dictionaryCode.label", "Dictionary Code")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_translation_catalog.fields.dictionaryCode.label", "Dictionary Code"),
          placeholder: "Enter Dictionary Code",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueTranslationCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-translation-catalog:display:dictionaryCode",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-translation-catalog",
              field: "dictionaryCode",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("valueCode", {
        id: "valueCode",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_translation_catalog.fields.valueCode.label", "Value Code")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_translation_catalog.fields.valueCode.label", "Value Code"),
          placeholder: "Enter Value Code",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueTranslationCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-translation-catalog:display:valueCode",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-translation-catalog",
              field: "valueCode",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("locale", {
        id: "locale",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_translation_catalog.fields.locale.label", "Locale")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_translation_catalog.fields.locale.label", "Locale"),
          placeholder: "Enter Locale",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueTranslationCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-translation-catalog:display:locale",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-translation-catalog",
              field: "locale",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("displayName", {
        id: "displayName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_translation_catalog.fields.displayName.label", "Display Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_translation_catalog.fields.displayName.label", "Display Name"),
          placeholder: "Enter Display Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueTranslationCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-translation-catalog:display:displayName",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-translation-catalog",
              field: "displayName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("description", {
        id: "description",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_translation_catalog.fields.description.label", "Description")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_translation_catalog.fields.description.label", "Description"),
          placeholder: "Enter Description",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueTranslationCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-translation-catalog:display:description",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-translation-catalog",
              field: "description",
              view: "display",
              compact: true,
            },
          ) ?? <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("updatedAt", {
        id: "updatedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_translation_catalog.fields.updatedAt.label", "Updated At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_translation_catalog.fields.updatedAt.label", "Updated At"),
          placeholder: "Enter Updated At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueTranslationCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-translation-catalog:display:updatedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-translation-catalog",
              field: "updatedAt",
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
              {renderSlotExtensions<DictionaryValueTranslationCatalogRecord>(
                frontendComposition,
                "row-actions:dictionary-value-translation-catalog:list",
                "rowActions.before",
                { resource: "dictionary-value-translation-catalog", record: row.original },
              )}
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="updateDictionaryValueTranslation"
                    recordItemId={row.original.dictionaryValueTranslationId}
                    size="sm"
                    query={{
                      dictionaryValueId: row.original.dictionaryValueId,
                      dictionaryCode: row.original.dictionaryCode,
                      valueCode: row.original.valueCode,
                      locale: row.original.locale,
                      displayName: row.original.displayName,
                      description: row.original.description,
                    }}
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.dictionaryValueTranslationId} size="sm" />
              {renderSlotExtensions<DictionaryValueTranslationCatalogRecord>(
                frontendComposition,
                "row-actions:dictionary-value-translation-catalog:list",
                "rowActions.after",
                { resource: "dictionary-value-translation-catalog", record: row.original },
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
    getRowId: (row) => String(row.dictionaryValueTranslationId),
    refineCoreProps: {
      dataProviderName: "federation-learning-support",
      syncWithLocation: false,
      meta: {
        tableName: "dictionary_value_translation_catalog_read_model_entity",
        idField: "dictionaryValueTranslationId",
        idFields: ["dictionaryValueTranslationId"],
        queryFields: ["dictionaryValueTranslationId","dictionaryValueId","dictionaryCode","valueCode","locale","displayName","description","updatedAt"],
        label: t("resources.dictionary_value_translation_catalog.label", "Dictionary Value Translation Catalog"),
        aggregateRoute: "dictionaryvaluetranslation",
        queryRoute: "dictionaryvaluetranslationcatalog",
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
        ? [...filters, { field: "dictionaryValueTranslationId", operator: "in", value: selectedIds } as CrudFilter]
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
        aggregateRoute: "dictionaryvaluetranslation",
        queryRoute: "dictionaryvaluetranslationcatalog",
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
        {renderSlotExtensions(frontendComposition, "toolbar:dictionary-value-translation-catalog:list", "toolbar.before", { resource: "dictionary-value-translation-catalog", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:dictionary-value-translation-catalog:list", "toolbar.actions", { resource: "dictionary-value-translation-catalog", table })}
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
        {renderSlotExtensions(frontendComposition, "toolbar:dictionary-value-translation-catalog:list", "toolbar.after", { resource: "dictionary-value-translation-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
