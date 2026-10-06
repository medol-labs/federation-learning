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
import { useDictionaryTranslation } from "@/lib/dictionary-i18n";
import { renderFieldOverride, renderSlotExtensions } from "@/platform/composition";
import { CopyableText } from "@/components/refine-ui/fields/copyable-text";
import type { DictionaryCode, DictionaryValueCode, DisplayOrder } from "@/contexts/domain/value-types";

type DictionaryValueCatalogRecord = {
  dictionaryValueId: string;
  dictionaryId: string;
  dictionaryCode: DictionaryCode;
  valueCode: DictionaryValueCode;
  defaultDisplayName: string;
  displayOrder?: DisplayOrder;
  description?: string;
  active: boolean;
  state: "Active" | "Disabled";
  addedAt: string;
  updatedAt?: string;
  disabledAt?: string;
  disabledReason?: string;
  enabledAt?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: DictionaryValueCatalogRecord,
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

export const DictionaryValueCatalogList = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<DictionaryValueCatalogRecord>();
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
      columnHelper.accessor("dictionaryValueId", {
        id: "dictionaryValueId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_catalog.fields.dictionaryValueId.label", "Dictionary Value Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_catalog.fields.dictionaryValueId.label", "Dictionary Value Id"),
          placeholder: "Enter Dictionary Value Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-catalog:display:dictionaryValueId",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-catalog",
              field: "dictionaryValueId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("dictionaryId", {
        id: "dictionaryId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_catalog.fields.dictionaryId.label", "Dictionary Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_catalog.fields.dictionaryId.label", "Dictionary Id"),
          placeholder: "Enter Dictionary Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-catalog:display:dictionaryId",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-catalog",
              field: "dictionaryId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("dictionaryCode", {
        id: "dictionaryCode",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_catalog.fields.dictionaryCode.label", "Dictionary Code")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_catalog.fields.dictionaryCode.label", "Dictionary Code"),
          placeholder: "Enter Dictionary Code",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-catalog:display:dictionaryCode",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-catalog",
              field: "dictionaryCode",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("valueCode", {
        id: "valueCode",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_catalog.fields.valueCode.label", "Value Code")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_catalog.fields.valueCode.label", "Value Code"),
          placeholder: "Enter Value Code",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-catalog:display:valueCode",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-catalog",
              field: "valueCode",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("defaultDisplayName", {
        id: "defaultDisplayName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_catalog.fields.defaultDisplayName.label", "Default Display Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_catalog.fields.defaultDisplayName.label", "Default Display Name"),
          placeholder: "Enter Default Display Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-catalog:display:defaultDisplayName",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-catalog",
              field: "defaultDisplayName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("displayOrder", {
        id: "displayOrder",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_catalog.fields.displayOrder.label", "Display Order")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_catalog.fields.displayOrder.label", "Display Order"),
          placeholder: "Enter Display Order",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-catalog:display:displayOrder",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-catalog",
              field: "displayOrder",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("description", {
        id: "description",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_catalog.fields.description.label", "Description")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_catalog.fields.description.label", "Description"),
          placeholder: "Enter Description",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-catalog:display:description",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-catalog",
              field: "description",
              view: "display",
              compact: true,
            },
          ) ?? <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("active", {
        id: "active",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_catalog.fields.active.label", "Active")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_catalog.fields.active.label", "Active"),
          placeholder: "Enter Active",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-catalog:display:active",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-catalog",
              field: "active",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_catalog.fields.state.label", "State"),
          placeholder: "Select State",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: t("resources.dictionary_value_catalog.fields.state.options.Active", "Active"), value: "Active" },
            { label: t("resources.dictionary_value_catalog.fields.state.options.Disabled", "Disabled"), value: "Disabled" },
          ],
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-catalog:display:state",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-catalog",
              field: "state",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel, [
            { label: t("resources.dictionary_value_catalog.fields.state.options.Active", "Active"), value: "Active" },
            { label: t("resources.dictionary_value_catalog.fields.state.options.Disabled", "Disabled"), value: "Disabled" },
          ]),
      }),
      columnHelper.accessor("addedAt", {
        id: "addedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_catalog.fields.addedAt.label", "Added At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_catalog.fields.addedAt.label", "Added At"),
          placeholder: "Enter Added At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-catalog:display:addedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-catalog",
              field: "addedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("updatedAt", {
        id: "updatedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_catalog.fields.updatedAt.label", "Updated At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_catalog.fields.updatedAt.label", "Updated At"),
          placeholder: "Enter Updated At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-catalog:display:updatedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-catalog",
              field: "updatedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("disabledAt", {
        id: "disabledAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_catalog.fields.disabledAt.label", "Disabled At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_catalog.fields.disabledAt.label", "Disabled At"),
          placeholder: "Enter Disabled At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-catalog:display:disabledAt",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-catalog",
              field: "disabledAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("disabledReason", {
        id: "disabledReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_catalog.fields.disabledReason.label", "Disabled Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_catalog.fields.disabledReason.label", "Disabled Reason"),
          placeholder: "Enter Disabled Reason",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-catalog:display:disabledReason",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-catalog",
              field: "disabledReason",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("enabledAt", {
        id: "enabledAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_value_catalog.fields.enabledAt.label", "Enabled At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_value_catalog.fields.enabledAt.label", "Enabled At"),
          placeholder: "Enter Enabled At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryValueCatalogRecord>(
            frontendComposition,
            "field:dictionary-value-catalog:display:enabledAt",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-value-catalog",
              field: "enabledAt",
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
              {renderSlotExtensions<DictionaryValueCatalogRecord>(
                frontendComposition,
                "row-actions:dictionary-value-catalog:list",
                "rowActions.before",
                { resource: "dictionary-value-catalog", record: row.original },
              )}
                {isCommandVisible(row.original, "", "", []) && (
                  <EditButton variant="ghost" recordItemId={row.original.dictionaryValueId} size="sm" />
                )}
                {isCommandVisible(row.original, "", "state", ["Active"]) && (
                  <CommandButton
                    variant="ghost"
                    command="disableDictionaryValue"
                    recordItemId={row.original.dictionaryValueId}
                    size="sm"
                    query={{
                      disabledReason: row.original.disabledReason,
                      dictionaryCode: row.original.dictionaryCode,
                      valueCode: row.original.valueCode,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "state", ["Disabled"]) && (
                  <CommandButton
                    variant="ghost"
                    command="enableDictionaryValue"
                    recordItemId={row.original.dictionaryValueId}
                    size="sm"
                    query={{
                      dictionaryCode: row.original.dictionaryCode,
                      valueCode: row.original.valueCode,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="setDictionaryValueTranslation"
                    recordItemId={row.original.dictionaryValueId}
                    size="sm"
                    query={{
                      dictionaryValueId: row.original.dictionaryValueId,
                      dictionaryCode: row.original.dictionaryCode,
                      valueCode: row.original.valueCode,
                      description: row.original.description,
                    }}
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.dictionaryValueId} size="sm" />
              {renderSlotExtensions<DictionaryValueCatalogRecord>(
                frontendComposition,
                "row-actions:dictionary-value-catalog:list",
                "rowActions.after",
                { resource: "dictionary-value-catalog", record: row.original },
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
    getRowId: (row) => String(row.dictionaryValueId),
    refineCoreProps: {
      dataProviderName: "federation-learning-support",
      syncWithLocation: false,
      meta: {
        tableName: "dictionary_value_catalog_read_model_entity",
        idField: "dictionaryValueId",
        idFields: ["dictionaryValueId"],
        queryFields: ["dictionaryValueId","dictionaryId","dictionaryCode","valueCode","defaultDisplayName","displayOrder","description","active","state","addedAt","updatedAt","disabledAt","disabledReason","enabledAt"],
        label: t("resources.dictionary_value_catalog.label", "Dictionary Value Catalog"),
        aggregateRoute: "dictionaryvalue",
        queryRoute: "dictionaryvaluecatalog",
        dataProviderName: "federation-learning-support",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:dictionary-value-catalog:list", "toolbar.before", { resource: "dictionary-value-catalog", table })}
        <CommandButton variant="default" command="addDictionaryValue" />
        {renderSlotExtensions(frontendComposition, "toolbar:dictionary-value-catalog:list", "toolbar.actions", { resource: "dictionary-value-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:dictionary-value-catalog:list", "toolbar.after", { resource: "dictionary-value-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
